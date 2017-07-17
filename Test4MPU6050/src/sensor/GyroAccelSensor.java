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
	
	private double Xaccl, Yaccl, Zaccl;
	private double Xgyro, Ygyro, Zgyro;
	
	//레지스터의 주소를 담고 있는 바이트배열
	private byte[] addr={59, 61, 63, 67, 69, 71};
	public double getXaccl() throws IOException {
		Xaccl = readWord2C(addr[0])/16384.0;
		return Xaccl;
	}

	public double getYaccl() throws IOException {
		Yaccl = readWord2C(addr[1])/16384.0;
		return Yaccl;
	}

	public double getZaccl() throws IOException {
		Zaccl = readWord2C(addr[2])/16384.0;
		return Zaccl;
	}

	public double getXgyro() throws IOException {
		Xgyro = readWord2C(addr[3])/131.0;
		return Xgyro;
	}

	public double getYgyro() throws IOException {
		Ygyro = readWord2C(addr[4])/131.0;
		return Ygyro;
	}

	public double getZgyro() throws IOException {
		Zgyro = readWord2C(addr[5])/131.0;
		return Zgyro;
	}
	

	
	public GyroAccelSensor() throws I2CFactory.UnsupportedBusNumberException, IOException {
		
		bus = I2CFactory.getInstance(I2CBus.BUS_1);
		mpu6050 = bus.getDevice(0x68);
		System.out.println("Create GyroAccelSensor");
		
		//디바이스 깨우기
		mpu6050.write(Mpu6050Registers.MPU6050_RA_PWR_MGMT_1, Mpu6050RegisterValues.MPU6050_RA_PWR_MGMT_1);
		//글로벌 세팅
		//mpu6050
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
		return radians*(180.0/Math.PI);
	}
	
	public double y_rotation(double x, double y, double z){
		double radians = Math.atan2(y, dist(z,x));
		return -radians*(180.0/Math.PI);
	}
	
	public double z_rotation(double x, double y, double z){
		double radians = Math.atan2(z, dist(x,y));
		return radians*(180.0/Math.PI);
	}
	
	
		
	public static void main(String[] args) {
		
		try {
			
			GyroAccelSensor test = new GyroAccelSensor();
			
			while(true){
				
			
//				int x = test.AddrRead(13);
//				int y = test.AddrRead(14);
//				int z = test.AddrRead(15);

				double acclx = test.getXaccl();
				double accly = test.getYaccl();
				double acclz = test.getZaccl();
				double gyrox = test.getXgyro();
				double gyroy = test.getYgyro();
				double gyroz = test.getZgyro();

				System.out.println("acclx : "+acclx);
				System.out.println("accly : "+accly);
				System.out.println("acclz : "+acclz);
				System.out.println("|");
				
				System.out.println("gyrox : "+gyrox);
				System.out.println("gyroy : "+gyroy);
				System.out.println("gyroz : "+gyroz);
				System.out.println("|");
				System.out.println("|");
				
				System.out.println("x rotation : "+ test.x_rotation(acclx, accly, acclz));
				System.out.println("y rotation : "+ test.y_rotation(acclx, accly, acclz));
				System.out.println("z rotation : "+ test.z_rotation(acclx, accly, acclz));
				System.out.println("|");
				System.out.println("|");
				System.out.println("| End");
				
				try {Thread.sleep(1000);	} catch (InterruptedException ex) {	}
			}
			
		} catch (I2CFactory.UnsupportedBusNumberException ex) {
			Logger.getLogger(GyroAccelSensor.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GyroAccelSensor.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
}
