package hardware.test;

import hardware.sensor.GyroDirection;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;

public class MouseControlTest {

    private String ipAddress = "192.168.3.133";
    private CoapClient coapClient;
    private JSONObject jsonObject;
    private String json;

    private void mouse(int acclX, int acclY, int acclZ) {
        jsonObject = new JSONObject();
        jsonObject.put("command", "change");
        jsonObject.put("acclX", String.valueOf(acclX));
        jsonObject.put("acclY", String.valueOf(acclY));
        jsonObject.put("acclZ", String.valueOf(acclZ));
        json = jsonObject.toString();

        coapClient = new CoapClient();
        coapClient.setURI("coap://" + ipAddress + "/mouse");
//        System.out.println("acclX " + acclX + "acclY " + acclY + "acclZ " + acclZ);
        coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
        coapClient.shutdown();
    }

    // Method
    public static void main(String[] args) throws Exception {
        MouseControlTest test = new MouseControlTest();
        GyroDirection gyro = new GyroDirection();

        gyro.init();
        int xValue = 0;
        int yValue = 0;
        int zValue = 0;
        while (true) {
            xValue = gyro.getAcclX();
            yValue = gyro.getAcclY();
            zValue = gyro.getAcclZ();

            test.mouse(xValue, yValue, zValue);
//            System.out.println("xValue: " + xValue + "\tyValue: " + yValue + "\tzValue: " + zValue);
//            test.getTest();
        }
    }
}
