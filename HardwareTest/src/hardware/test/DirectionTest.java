package hardware.test;

import hardware.sensor.GyroDirection;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;

public class DirectionTest {

    // Field
    private String ipAddress = "192.168.3.50";
    private CoapClient coapClient;
    private JSONObject jsonObject;
    private String json;

    // Method
    private void frontTire(int angle) {
        jsonObject = new JSONObject();
        jsonObject.put("command", "change");
        jsonObject.put("angle", String.valueOf(angle));
        json = jsonObject.toString();

        coapClient = new CoapClient();
        coapClient.setURI("coap://" + ipAddress + "/fronttire");
        coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
        coapClient.shutdown();
    }

    private void backTire(String direction, int speed) {
        jsonObject = new JSONObject();
        jsonObject.put("command", "change");
        jsonObject.put("direction", direction);
        jsonObject.put("speed", String.valueOf(speed));
        json = jsonObject.toString();

        coapClient = new CoapClient();
        coapClient.setURI("coap://" + ipAddress + "/backtire");
        coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
        coapClient.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        DirectionTest test = new DirectionTest();

        GyroDirection gyro = new GyroDirection();

        gyro.init();

        int xValue;
        int yValue;
        int zValue;

        int angle = 0;
        int speed = 0;
        String direction = "";

        while (true) {

            xValue = gyro.getAcclX();
            yValue = gyro.getAcclY();
            zValue = gyro.getAcclZ();

            if (xValue > 8000) {
                angle = 60;
            } else if (xValue < -8000) {
                angle = 120;
            } else if (xValue > -4000 && xValue < 4000) {
                angle = 90;
            }

            speed = yValue / 4;
            if (yValue > 4000) {
                direction = "forward";
            } else if (yValue < -4000) {
                direction = "backward";
                speed = Math.abs(speed);
            } else if (yValue > -3000 && yValue < 3000) {
                direction = "forward";
                speed = 0;
            }

            test.frontTire(angle);
            test.backTire(direction, speed);
            System.out.println("angle: " + angle + "\tdirection: " + direction + "\tspeed: " + speed);
            System.out.println();
//            Thread.sleep(500);
        }
    }
}
