package sensor;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GyroAccelSensor {
	
	private I2CBus bus;
	private I2CDevice mpu6050;
	
	private double acclX, acclY, acclZ;
	
	private double gyroX, gyroY, gyroZ;
	private double gyroAngleXcollect=0, gyroAngleYcollect=0, gyroAngleZcollect=0;
		
	private double gyroAngularSpeedOffsetX ,gyroAngularSpeedOffsetY, gyroAngularSpeedOffsetZ;
	
	private double deltaGyroX, deltaGyroY, deltaGyroZ;
	private double splAngleX, splAngleY, splAngleZ;
	
	private double filteredAngleX = 0.;
	private double filteredAngleY = 0.;
	private double filteredAngleZ = 0.;
	
	private Thread updatingThread = null;
	private boolean updatingThreadStopped = true;
	private long lastUpdateTimeX = 0;
	private long lastUpdateTimeY = 0;
	private long lastUpdateTimeZ = 0;
	
	
	public double getAcclX() throws IOException {
		acclX = readWord2C(Mpu6050Registers.MPU6050_RA_ACCEL_XOUT_H)/16384.0;
		return acclX;
	}

	public double getAcclY() throws IOException {
		acclY = readWord2C(Mpu6050Registers.MPU6050_RA_ACCEL_YOUT_H)/16384.0;
		return acclY;
	}

	public double getAcclZ() throws IOException {
		acclZ = readWord2C(Mpu6050Registers.MPU6050_RA_ACCEL_ZOUT_H)/16384.0;
		return acclZ;
	}

	public double getGyroX() throws IOException {
		gyroX = readWord2C(Mpu6050Registers.MPU6050_RA_GYRO_XOUT_H)/131.0;
		return gyroX;
	}

	public double getGyroY() throws IOException {
		gyroY = readWord2C(Mpu6050Registers.MPU6050_RA_GYRO_YOUT_H)/131.0;
		return gyroY;
	}

	public double getGyroZ() throws IOException {
		gyroZ = readWord2C(Mpu6050Registers.MPU6050_RA_GYRO_ZOUT_H)/131.0;
		return gyroZ;
	}
	
	// 각도들이 쌓인 값을 나타내어 누적된 결과를 반환하는 메소드
	public double getGyroAngleXcollect() {
		gyroAngleXcollect+=gyroX;
		return gyroAngleXcollect;
	}

	public double getGyroAngleYcollect() {
		gyroAngleYcollect+=gyroY;
		return gyroAngleYcollect;
	}

	public double getGyroAngleZcollect() {
		gyroAngleZcollect+=gyroZ;
		return gyroAngleZcollect;
	}
	
	
	// 필터된 값을 리턴하는 함수
	public double getFilteredAngleX() {
		double alpha = 1;
		filteredAngleX = alpha * (filteredAngleX + gyroX) + (1. - alpha) * x_rotation(acclX, acclY, acclZ);
		return filteredAngleX;
	}

	public double getFilteredAngleY() {
		double alpha = 1;
		filteredAngleY = alpha * (filteredAngleX + gyroY) + (1. - alpha) * y_rotation(acclX, acclY, acclZ);
		return filteredAngleY;
	}

	public double getFilteredAngleZ() {
		return filteredAngleZ+gyroZ;
	}
	
	// 실시간으로 올라가는 값을 측정하기 위해 각 축의 각속도의 변화랑을 리턴하는 함수
	public double getDeltaAngleX() throws IOException{
		deltaGyroX = readWord2C(Mpu6050Registers.MPU6050_RA_GYRO_XOUT_H)/131.0;
		double dt = Math.abs(System.currentTimeMillis() - lastUpdateTimeX) / 1000.;		
		deltaGyroX -= gyroAngularSpeedOffsetX;
		deltaGyroX = deltaGyroX*dt;
		lastUpdateTimeX = System.currentTimeMillis();
		return deltaGyroX;
	}
	
	public double getDeltaAngleY() throws IOException{
		deltaGyroY = readWord2C(Mpu6050Registers.MPU6050_RA_GYRO_YOUT_H)/131.0;
		double dt = Math.abs(System.currentTimeMillis() - lastUpdateTimeY) / 1000.;		
		deltaGyroY -= gyroAngularSpeedOffsetY;
		deltaGyroY = deltaGyroY*dt;
		lastUpdateTimeY = System.currentTimeMillis();
		return deltaGyroY;
	}
	
	public double getDeltaAngleZ() throws IOException{
		deltaGyroZ = readWord2C(Mpu6050Registers.MPU6050_RA_GYRO_ZOUT_H)/131.0;
		double dt = Math.abs(System.currentTimeMillis() - lastUpdateTimeZ) / 1000.;		
		deltaGyroZ -= gyroAngularSpeedOffsetZ;
		deltaGyroZ = deltaGyroZ*dt;
		lastUpdateTimeZ = System.currentTimeMillis();
		return deltaGyroX;
	}

	public int getSplAngleX() throws IOException {
		splAngleX = 0;
		double deg=Math.atan2(acclY, acclZ)*180/Math.PI;
		splAngleX = (0.95 * (splAngleX + (getGyroX() * 0.001))) + (0.05 * deg) ;
		return (int)((splAngleX+9)*20);
	}

	public int getSplAngleY() throws IOException {
		splAngleY = 0;
		double deg=Math.atan2(acclX, acclZ)*180/Math.PI;
		splAngleY = (0.95 * (splAngleY + (getGyroY() * 0.001))) + (0.05 * deg) ;
		return (int)((splAngleY+9)*20);
	}

	public int getSplAngleZ() {
		splAngleZ = getGyroAngleZcollect();
		return (int)splAngleZ;
	}
	
	
	// 한번에 모든 값을 업데이트하기 위한 메소드
	public void updateValues() throws IOException{
		
		getAcclX();
		getAcclY();
		getAcclZ();
		
		getGyroX();
		getGyroY();
		getGyroZ();
		
		gyroX -= gyroAngularSpeedOffsetX;
		gyroY -= gyroAngularSpeedOffsetY;
		gyroZ -= gyroAngularSpeedOffsetZ;
		
		double dt = Math.abs(System.currentTimeMillis() - (lastUpdateTimeX+lastUpdateTimeY+lastUpdateTimeZ)/3.0) / 1000.;
		gyroX = gyroX*dt;
		gyroY = gyroY*dt;
		gyroZ = gyroZ*dt;
		lastUpdateTimeX = System.currentTimeMillis();
		lastUpdateTimeY = System.currentTimeMillis();
		lastUpdateTimeZ = System.currentTimeMillis();
		
		getGyroAngleXcollect();
		getGyroAngleYcollect();
		getGyroAngleZcollect();
		
		getFilteredAngleX();
		getFilteredAngleY();
		getFilteredAngleZ();
		
	}
	
	public GyroAccelSensor() throws I2CFactory.UnsupportedBusNumberException, IOException {
				bus = I2CFactory.getInstance(I2CBus.BUS_1);
		mpu6050 = bus.getDevice(0x68);
		System.out.println("Create GyroAccelSensor");
		
		//디바이스 깨우기
		mpu6050.write(Mpu6050Registers.MPU6050_RA_PWR_MGMT_1,
					Mpu6050RegisterValues.MPU6050_RA_PWR_MGMT_1);
		
		mpu6050.write(Mpu6050Registers.MPU6050_RA_SMPLRT_DIV,
					Mpu6050RegisterValues.MPU6050_RA_SMPLRT_DIV);
		
		mpu6050.write(Mpu6050Registers.MPU6050_RA_CONFIG,
					Mpu6050RegisterValues.MPU6050_RA_CONFIG);
		
		mpu6050.write(Mpu6050Registers.MPU6050_RA_ACCEL_CONFIG,
					Mpu6050RegisterValues.MPU6050_RA_ACCEL_CONFIG);
		
		mpu6050.write(Mpu6050Registers.MPU6050_RA_INT_ENABLE,
					Mpu6050RegisterValues.MPU6050_RA_INT_ENABLE);
		
		mpu6050.write(Mpu6050Registers.MPU6050_RA_PWR_MGMT_2,
					Mpu6050RegisterValues.MPU6050_RA_PWR_MGMT_2);
		
		System.out.println("Calibration started (3초간 움직이지 마세요)");
		
		// Gyroscope offsets 오프셋을 구하여 오차를 빼주기 위해서 필요한 부분
		gyroAngularSpeedOffsetX = 0.;
		gyroAngularSpeedOffsetY = 0.;
		gyroAngularSpeedOffsetZ = 0.;
		
		int nbReadings = 300;
		for(int i = 0; i < nbReadings; i++) {
			
			gyroAngularSpeedOffsetX += getGyroX();
			gyroAngularSpeedOffsetY += getGyroY();
			gyroAngularSpeedOffsetZ += getGyroZ();
			
			try {Thread.sleep((long) 10);	} catch (InterruptedException ex) {	}
		}
		
		gyroAngularSpeedOffsetX /= nbReadings;
		gyroAngularSpeedOffsetY /= nbReadings;
		gyroAngularSpeedOffsetZ /= nbReadings;
		
		System.out.println("Calibration이 끝났습니다.");
	}
	
	
	// I2C통신을 이용하여 읽은 데이터를 값으로 변경하는 함수
	public double readWord2C(byte addr) throws IOException{

		double value = readWord(addr);

			if (value >= 0x8000){
				value = (double) -((65535 - value) + 1);
			}
			else{
				value = (double) value;
			}

			return value;
	}
	// I2C로 읽은 데이터의 상하위 8비트를 합쳐서 16비트로 만드는 함수
	public double readWord(byte addr) throws IOException{
		
			int high = readRegister(addr);
			int low = readRegister((byte) (addr + 1));
			double value = (high << 8) + low;

			return value;
	}
	
	// 레지스터의 주소로 데이터를 바이트로 받는 함수 (8비트 단위로 H부분과 L부분이 있음)
	public byte readRegister(byte register) throws IOException {
			int data = mpu6050.read(register);
			return (byte) data;
	}
	
	// 점과 점의 거리를 구하기 위한 코드
	private double dist(double a, double b){
		 
		return Math.sqrt(Math.pow(a, 2.0)+Math.pow(b, 2.0));
	}
	
	// 회전값을 구하기 위한 코드 ( 점사이의 거리와 x, y, z의 좌표값을 이용해 구함)
	public double x_rotation(double x, double y, double z){
		double radians = Math.atan2(x, dist(y,z));
		double delta = 0.;
		if (y >= 0) {
			if (z >= 0) {
				// pass
			} else {
				radians *= -1;
				delta = 180.;
			}
		} else {
			if (z <= 0) {
				radians *= -1;
				delta = 180.;
			} else {
				delta = 360.;
			}
		}
		return radians * (180.0/Math.PI) + delta;
	}
	
	public double y_rotation(double x, double y, double z){
		double tan = -1 * x / dist(y, z);
		double delta = 0.;
		if (x <= 0) {
			if (z >= 0) {
				// q1
				// nothing to do
			} else {
				// q2
				tan *= -1;
				delta = 180.;
			}
		} else {
			if (z <= 0) {
				// q3
				tan *= -1;
				delta = 180.;
			} else {
				// q4
				delta = 360.;
			}
		}

		return Math.atan(tan)*(180.0/Math.PI) + delta;
	}
	
	public double z_rotation(double x, double y, double z){
		double radians = Math.atan2(z, dist(x,y));
		return radians*(180.0/Math.PI);
	}
	
	// 스레드를 돌려 초기 시간값 설정 후 계속해서 값을 업데이트함
	public void startUpdatingThread() {
        if(updatingThread == null || !updatingThread.isAlive()) {
		updatingThreadStopped = false;
		lastUpdateTimeX = System.currentTimeMillis();
		lastUpdateTimeY = System.currentTimeMillis();
		lastUpdateTimeZ = System.currentTimeMillis();
            updatingThread = new Thread(() -> {
                while(!updatingThreadStopped) {
			try {
				updateValues();
			} catch (IOException ex) {}
                }
            });
            updatingThread.start();
        } else {
			System.out.println("Updating thread of the MPU6050 is already started.(이미 시작 됐네요)");
		}
	}
	
	// 스레드를 멈추기위한 메소드
	public void stopUpdatingThread() throws InterruptedException {
        updatingThreadStopped = true;
		try {
			updatingThread.join();
		} catch (InterruptedException e) {
			System.out.println("Exception when joining the updating thread.");
		throw e;
		}
		updatingThread = null;
	}
		
	public static void main(String[] args) {
		
		
		try {
			
			GyroAccelSensor test = new GyroAccelSensor();
			
			test.startUpdatingThread();
			
			while(true){
				
				double acclx = test.getAcclX();
				double accly = test.getAcclY();
				double acclz = test.getAcclZ();
				double gyrox = test.getGyroX();
				double gyroy = test.getGyroY();
				double gyroz = test.getGyroZ();
				
				double preAcclx, preAccly, preAcclz;

				double preGyrox, preGyroy, preGyroz;
		
			
				
								
				preAcclx = acclx;
				preAccly = accly;
				preAcclz = acclz;
				preGyrox = gyrox;
				preGyroy = gyroy;
				preGyroz = gyroz;
								
				try {Thread.sleep((long) 0.1);	} catch (InterruptedException ex) {	}
				
				acclx = test.getAcclX();
				accly = test.getAcclY();
				acclz = test.getAcclZ();
				gyrox = test.getGyroX();
				gyroy = test.getGyroY();
				gyroz = test.getGyroZ();
				
				int deltaX = (int)((acclx-preAcclx)*1);
				int deltaY = (int)((accly-preAccly)*1);
				int deltaZ = (int)((acclz-preAcclz)*1);
				
				double vecX1 = 0 + preAcclx+ (acclx-preAcclx)/2.0;
				double vecY1 = 0 + preAccly+ (accly-preAccly)/2.0;
				double vecZ1 = 0 + preAcclz+ (acclz-preAcclz)/2.0;
				
				double degX1 = 0 + preGyrox +(gyrox-preGyrox)/2.0;
				double degY1 = 0 + preGyroy +(gyroy-preGyroy)/2.0;
				double degZ1 = 0 + preGyroz +(gyroz-preGyroz)/2.0;
				
				preAcclx = acclx;
				preAccly = accly;
				preAcclz = acclz;
				preGyrox = gyrox;
				preGyroy = gyroy;
				preGyroz = gyroz;
				
				try {Thread.sleep((long) 0.1);	} catch (InterruptedException ex) {	}
				
				acclx = test.getAcclX();
				accly = test.getAcclY();
				acclz = test.getAcclZ();
				gyrox = test.getGyroX();
				gyroy = test.getGyroY();
				gyroz = test.getGyroZ();
				
				double vecX2 = vecX1+ preAcclx + (acclx-preAcclx)/2.0;
				double vecY2 = vecY1+ preAccly + (accly-preAccly)/2.0;
				double vecZ2 = vecZ1+ preAcclz + (acclz-preAcclz)/2.0;
				
				double posX1 = 0 +vecX1+ (vecX2-vecX1)/2.0;
				double posY1 = 0 +vecY1+ (vecY2-vecY1)/2.0;
				double posZ1 = 0 +vecZ1+ (vecZ2-vecZ1)/2.0;
				
				double degX2 = 0+ preGyrox +(gyrox-preGyrox)/2.0;
				double degY2 = 0 + preGyroy +(gyroy-preGyroy)/2.0;
				double degZ2 = 0 + preGyroz +(gyroz-preGyroz)/2.0;
				
				preAcclx = acclx;
				preAccly = accly;
				preAcclz = acclz;
				preGyrox = gyrox;
				preGyroy = gyroy;
				preGyroz = gyroz;
				
				try {Thread.sleep((long) 0.1);	} catch (InterruptedException ex) {	}
				
				acclx = test.getAcclX();
				accly = test.getAcclY();
				acclz = test.getAcclZ();
				gyrox = test.getGyroX();
				gyroy = test.getGyroY();
				gyroz = test.getGyroZ();
				
				double vecX3 = vecX2+ preAcclx + (acclx-preAcclx)/2.0;
				double vecY3 = vecY2+ preAccly + (accly-preAccly)/2.0;
				double vecZ3 = vecZ2+ preAcclz + (acclz-preAcclz)/2.0;
				
				double posX2 = posX1 +vecX2+ (vecX3-vecX2)/2.0;
				double posY2 = posY1 +vecY2+ (vecY3-vecY2)/2.0;
				double posZ2 = posZ1 +vecZ2+ (vecZ3-vecZ2)/2.0;
				
				int deltaPosX = (int)(posX2-posX1);
				int deltaPosY = (int)(posY2-posY1);
				int deltaPosZ = (int)(posZ2-posZ1);
				
				double degX3 = (0+ preGyrox +(gyrox-preGyrox)/2.0);
				double degY3 = (0 + preGyroy +(gyroy-preGyroy)/2.0);
				double degZ3 = (0 + preGyroz +(gyroz-preGyroz)/2.0);
				
				double deltaDegX1 = gyrox-preGyrox;
				double deltaDegY1 = gyroy-preGyroy;
				double deltaDegZ1 = (gyroz-preGyroz);
				
				
				
//				System.out.println("acclx : "+acclx);
//				System.out.println("accly : "+accly);
//				System.out.println("acclz : "+acclz);
//				System.out.println("|");				
//								
//				System.out.println("acclx-prevAcclx : "+ deltaX);
//				System.out.println("accly-prevAccly : "+ deltaY);
//				System.out.println("acclz-prevAcclz : "+ deltaZ);
//				System.out.println("|");
//				
//				System.out.println("velocityX1 : "+ vecX1);
//				System.out.println("velocityY1 : "+ vecY1);
//				System.out.println("velocityZ1 : "+ vecZ1);
//				System.out.println("|");
//				
//				System.out.println("velocityX2 : "+ vecX2);
//				System.out.println("velocityY2 : "+ vecY2);
//				System.out.println("velocityZ2 : "+ vecZ2);
//				System.out.println("|");
//				
//				System.out.println("positionX1 : "+ posX1);
//				System.out.println("positionY1 : "+ posY1);
//				System.out.println("positionZ1 : "+ posZ1);
//				System.out.println("|");
//				
//				System.out.println("positionX2 : "+ posX2);
//				System.out.println("positionY2 : "+ posY2);
//				System.out.println("positionZ2 : "+ posZ2);
//				System.out.println("|");
//				
//				System.out.println("deltaPositionX : "+ deltaPosX);
//				System.out.println("deltaPositionY : "+ deltaPosY);
//				System.out.println("deltaPositionZ : "+ deltaPosZ);
//				System.out.println("|");
//				
//				System.out.println("gyrox : "+gyrox);
//				System.out.println("gyroy : "+gyroy);
//				System.out.println("gyroz : "+gyroz);
//				System.out.println("|");
//				
//				System.out.println("x rotation : "+ test.x_rotation(acclx, accly, acclz));
//				System.out.println("y rotation : "+ test.y_rotation(acclx, accly, acclz));
//				System.out.println("z rotation : "+ test.z_rotation(acclx, accly, acclz));
//				System.out.println("|");
//				
//				System.out.println("degX1 : "+degX1);
//				System.out.println("degY1 : "+degY1);
//				System.out.println("degZ1 : "+degZ1);
//				System.out.println("|");
//				
//				System.out.println("degX2 : "+degX2);
//				System.out.println("degY2 : "+degY2);
//				System.out.println("degZ2 : "+degZ2);
//				System.out.println("|");
//				
//				System.out.println("degX3 : "+degX3);
//				System.out.println("degY3 : "+degY3);
//				System.out.println("degZ3 : "+degZ3);
//				System.out.println("|");
//				
//				System.out.println("deltaDegX1 : "+deltaDegX1);
//				System.out.println("deltaDegY1 : "+deltaDegY1);
//				System.out.println("deltaDegZ1 : "+deltaDegZ1);
//				System.out.println("|");				
//				
//				System.out.println("filteredAngleX : "+test.getFilteredAngleX());
//				System.out.println("filteredAngleY : "+test.getFilteredAngleY());
//				System.out.println("filteredAngleZ : "+test.getFilteredAngleZ());
//				System.out.println("|");
//				
//				System.out.println("AngleX : "+test.getGyroAngleXcollect());
//				System.out.println("AngleY : "+test.getGyroAngleYcollect());
//				System.out.println("AngleZ : "+test.getGyroAngleZcollect());
//				System.out.println("|");
//				
				System.out.println("SimpleAngleX : "+test.getSplAngleX());
				System.out.println("SimpleAngleY : "+test.getSplAngleY());
				System.out.println("SimpleAngleZ : "+test.getSplAngleZ());
				System.out.println("|");
				System.out.println("|- End");
				
				
				
				try {Thread.sleep(500);	} catch (InterruptedException ex) {	}
			}
			
		} catch (I2CFactory.UnsupportedBusNumberException ex) {
			Logger.getLogger(GyroAccelSensor.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GyroAccelSensor.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
}
