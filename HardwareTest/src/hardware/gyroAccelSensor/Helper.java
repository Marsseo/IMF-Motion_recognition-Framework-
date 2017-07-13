package gyroAccelSensor;


public class Helper {

    public static void printValue(String name, byte value) {
        System.out.println(name + ":\t" + Integer.toBinaryString((int) value) + " (" + value + ")");
    }

    public static void printValueIfNotZero(String name, byte value) {
        if (value != 0) {
            System.out.println(name + ":\t" + Integer.toBinaryString((int) value) + " (" + value + ")");
        }
    }

    public static String formatBinary(byte b) {
        String binaryString = Integer.toBinaryString(b);
        if (binaryString.length() > 8) {
            binaryString = binaryString.substring(binaryString.length() - 8);
        }
        if (binaryString.length() < 8) {
            byte fillingZeros = (byte) (8 - binaryString.length());
            for (int j = 1; j <= fillingZeros; j++) {
                binaryString = "0" + binaryString;
            }
        }
        return binaryString;
    }
}
