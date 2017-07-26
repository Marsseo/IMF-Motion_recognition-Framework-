package sensor;

import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;


public class GyroTest {
	
	double x, y, z;
	private String ipAddress = "192.168.3.109";
	private JSONObject jsonObject;
	private String json;
	private CoapClient coapClient;
	private GyroAccelSensor test;

	public GyroTest() {		
		
		
		try {
			GyroAccelSensor test = new GyroAccelSensor();
			
			test.startUpdatingThread();
			x = test.getSplAngleX();
			y = test.getSplAngleY();
			z = test.getSplAngleZ();
						
			
		} catch (I2CFactory.UnsupportedBusNumberException ex) {
			Logger.getLogger(GyroTest.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GyroTest.class.getName()).log(Level.SEVERE, null, ex);
		}		
		
		
	}
	
	
	public void send() throws IOException{
		
			x = test.getSplAngleX();
			y = test.getSplAngleY();
			z = test.getSplAngleZ();
			
			jsonObject = new JSONObject();
			jsonObject.put("command", "change");
			jsonObject.put("X", String.valueOf(x));
			jsonObject.put("Y", String.valueOf(y));
			jsonObject.put("Z", String.valueOf(z));
			json = jsonObject.toString();

			coapClient = new CoapClient();
			coapClient.setURI("coap://" + ipAddress + "/gesture");
			CoapResponse a = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
			System.out.println(a);
			coapClient.shutdown();
		}
	
	
	
	public static void main(String[] args) {
		GyroTest test = new GyroTest();
		
		
		while(true){
			try {
				test.send();
			} catch (IOException ex) {
				Logger.getLogger(GyroTest.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	
}
