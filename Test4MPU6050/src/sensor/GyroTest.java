package sensor;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;


public class GyroTest {
	
	private double x, y, z;
	private String ipAddress = "192.168.3.109";
	private JSONObject jsonObject;
	private String json;
	private CoapClient coapClient;
	private GyroAccelSensor test1;
	private TouchSwitch test2;

	public GyroTest() {		
		
		
		try {
			test1 = new GyroAccelSensor();
			test2 = new TouchSwitch(RaspiPin.GPIO_01);
			test1.startUpdatingThread();
			
						
			
		} catch (I2CFactory.UnsupportedBusNumberException ex) {
			Logger.getLogger(GyroTest.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GyroTest.class.getName()).log(Level.SEVERE, null, ex);
		}		
		
		
	}
	
	
	public void send() throws IOException{
		
			x = test1.getFilteredAngleX();
			y = test1.getFilteredAngleY();
			z = (z<0) ?360+test1.getFilteredAngleZ():test1.getFilteredAngleZ();
			
			jsonObject = new JSONObject();
			jsonObject.put("command", "change");
			jsonObject.put("rollAngle", String.valueOf(x));
			jsonObject.put("pitchAngle", String.valueOf(y));
			jsonObject.put("yawAngle", String.valueOf(z));
			json = jsonObject.toString();

			coapClient = new CoapClient();
			coapClient.setURI("coap://" + ipAddress + "/gyroscope");
			CoapResponse a = coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
			//System.out.println(a);
			coapClient.shutdown();
		}
	
	public void sendButton(){
		
		jsonObject = new JSONObject();
		jsonObject.put("sensor", "button");
		jsonObject.put("yawAngle", test2.getStatus());
		json = jsonObject.toString();

		coapClient = new CoapClient();
		coapClient.setURI("coap://" + ipAddress + "/gyroscope");
		coapClient.post(json, MediaTypeRegistry.APPLICATION_JSON);
		coapClient.shutdown();
		
	}
	
	
	
	public static void main(String[] args) {
		
		GyroTest test = new GyroTest();
		
		
		while(true){
			try {
				test.send();
				System.out.println("각도 :"+test.x+"\t"+test.y+"\t"+test.z);
			} catch (IOException ex) {
				Logger.getLogger(GyroTest.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	
}
