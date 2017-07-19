package hardware.sensor;

import static com.pi4j.wiringpi.I2C.wiringPiI2CReadReg8;
import static com.pi4j.wiringpi.I2C.wiringPiI2CSetup;
import static com.pi4j.wiringpi.I2C.wiringPiI2CWriteReg8;

public class GyroDirection {

    // Field
    private static int i2cAddress = 0x68;
    private static int linuxFilehandle;

    private int acclX;
    private int acclY;
    private int acclZ;

    // Method
    int readWord2C(int addr) {
        int val;
        val = wiringPiI2CReadReg8(linuxFilehandle, addr);
        val = val << 8;
        val += wiringPiI2CReadReg8(linuxFilehandle, addr + 1);
        if (val >= 0x8000) {
            val = -(65536 - val);
        }
        return val;
    }

    public int getAcclX() {
        acclX = readWord2C(0x3B);
        if (acclX > 16000) {
            acclX = 16000;
        } else if (acclX < -16000) {
            acclX = -16000;
        }
        return acclX;
    }

    public int getAcclY() {
        acclY = readWord2C(0x3D);
        if (acclY > 16000) {
            acclY = 16000;
        } else if (acclY < -16000) {
            acclY = -16000;
        }
        return acclY;
    }

    public int getAcclZ() {
        acclZ = readWord2C(0x3F);
        if (acclZ > 16000) {
            acclZ = 16000;
        } else if (acclZ < -16000) {
            acclZ = -16000;
        }
        return acclZ;
    }

    public void init() {
        linuxFilehandle = wiringPiI2CSetup(i2cAddress);
        wiringPiI2CWriteReg8(linuxFilehandle, 0x6B, 0x00);
    }

    public static void main(String[] args) throws Exception {
        GyroDirection gyro = new GyroDirection();
        gyro.init();

        int directionX;
        int directionY;
        int directionZ;

        while (true) {
            directionX = gyro.getAcclX();
            directionY = gyro.getAcclY();
//            directionZ = gyro.getAcclZ();
            Thread.sleep(200);
            System.out.println("X: " + directionX + "\tY: " + directionY);// + "\tZ: " + directionZ);
        }
    }
}
