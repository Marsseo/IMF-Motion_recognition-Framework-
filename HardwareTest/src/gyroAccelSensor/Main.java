package gyroAccelSensor;


import gyroAccelSensor.Mpu6050.Mpu6050Controller;
import static gyroAccelSensor.Mpu6050.Mpu6050Controller.initialize;
import static gyroAccelSensor.Mpu6050.Mpu6050Controller.readRegister;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.Random;


public class Main {

	  public static void main(String[] args) throws IOException, InterruptedException, I2CFactory.UnsupportedBusNumberException {
			
			  initialize();
        while (true) {
            run();
        }

			
			/*
       try {
             byte[] accelData = new byte[6];
            System.out.println("Creating I2C bus");
            I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
            System.out.println("Creating I2C device");
            I2CDevice device = bus.getDevice(0x68);

            byte[] writeData = new byte[1];
            long waitTimeSent = 1000;
            long waitTimeRead = 1000;
            int count = 0;
						Mpu6050Controller mpu6050=new Mpu6050Controller();
            while (count < 100) {
							
                //negative values don't work
                new Random().nextBytes(writeData);
                System.out.println("Writing " + writeData[0] + " via I2C");
                device.write(writeData[0]);
                System.out.println("Waiting 1 second");
                Thread.sleep(waitTimeSent);
                System.out.println("Reading data via I2C");
                int dataRead = device.read(00);//put the high value register you want to read here
                int dataRead2 = device.read(00);////put the low value register you want to read here
                int output = (dataRead<<8) + dataRead2;
                System.out.println("Read " + output + " mps via I2C");
                System.out.println("Waiting 1 second");
								
                Thread.sleep(waitTimeRead);
						
                count++;
								
								 device.read(0x3B, accelData, 0, 6);   
								 for(int i=0;i<6;i++){
								 System.out.println(accelData[i]);
								 }
								 Thread.sleep(1000);
								
								 acX = (resolAc0 * make16( accelData[0],  accelData[1] ));
                    acY = (resolAc0 * make16( accelData[2] , accelData[3] ));
                    acZ = (resolAc0 * make16( accelData[4] , accelData[5] ));           
								 
								
							//	byte value=Mpu6050Controller.readRegister(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_XOUT_H);
							//	System.out.println(value);
								
								//device.write(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_XOUT_H);
								//int gyroX=device.read(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_XOUT_H);
								//System.out.println(gyroX);
								//readWord2C(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_XOUT_H);
							//	device.write(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_YOUT_H);
							//	int gyroY=device.read(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_YOUT_H);
							//	System.out.println(gyroY);
							//	device.write(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_ZOUT_H);
							//	int gyroZ=device.read(Mpu6050.Mpu6050Registers.MPU6050_RA_GYRO_ZOUT_H);
							//	System.out.println(gyroZ);
								
							
            }
        } catch (IOException ex) {
            ex.printStackTrace();
      //  } catch (InterruptedException ex) {
     //       ex.printStackTrace();
        }
				
    }

    private static void run() {

    }
		
		    public double readWord(byte addr) throws IOException{
        int high = readRegister(addr);
        int low = readRegister((byte) (addr + 1));
        double value = (high << 8) + low;
        
        return value;
    }
    
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
		    private int make16( int high, int low ){
        //Este método convierte dos variables de 8 bits, en una sola variable de de 16 bits
        return ( (high << 8) | low );
    }

    private static void initialize() throws IOException, InterruptedException, I2CFactory.UnsupportedBusNumberException {
       System.out.println("Initializing Mpu6050");
        Mpu6050Controller.initialize();
        System.out.println("Mpu6050 initialized!");
				int value = Mpu6050Controller.readRegister();
				    for(int i=0;i<100;i++){
							System.out.println(value);
						}

		
    }
		*/
		
}
		 private static void run() {

    }
}
