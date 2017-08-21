package Motion.server;

import Motion.mqtt.Distributor;
import static Motion.server.GyroscopeResource.currPitchAngle;
import static Motion.server.GyroscopeResource.currRollAngle;
import static Motion.server.GyroscopeResource.currYawAngle;
import static Motion.server.IRResource.irDistance;
import static Motion.server.UltrasonicResource.ultraDistance;
import java.util.logging.Level;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Hwasung Seo
 */

public class MQTTResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(MQTTResource.class);

	private static MQTTResource instance;
		
	private Distributor mqtt;
	private String mqttId;
	
	public MQTTResource() throws Exception {
		super("mqtt");
		instance = this;

		//여기에 이름을 세팅
		mqttId = "Hwasung Seo";
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						mqtt  = new Distributor(mqttId, "gyro");
						JSONObject responseJsonObject = new JSONObject();
						responseJsonObject.put("yawAngle", String.valueOf(Math.round(currYawAngle*100)/100.));
						responseJsonObject.put("pitchAngle", String.valueOf(Math.round(currPitchAngle*100)/100.));
						responseJsonObject.put("rollAngle", String.valueOf(Math.round(currRollAngle*100)/100.));

						String responseJson = responseJsonObject.toString();
						try {
							mqtt.publish(responseJson);
						} catch (MqttException ex) {
							java.util.logging.Logger.getLogger(MQTTResource.class.getName()).log(Level.SEVERE, null, ex);
						}
						mqtt.close();
						
						mqtt = new Distributor(mqttId, "ultrasonic");
						responseJsonObject = new JSONObject();
						responseJsonObject.put("distance", String.valueOf(ultraDistance) );
						responseJson = responseJsonObject.toString();
						try {
							mqtt.publish(responseJson);
						} catch (MqttException ex) {
							java.util.logging.Logger.getLogger(GyroscopeResource.class.getName()).log(Level.SEVERE, null, ex);
						}
						mqtt.close();
								
						mqtt = new Distributor(mqttId, "ifraredray");
						responseJsonObject = new JSONObject();
						responseJsonObject.put("distance", String.valueOf(irDistance) );
						responseJson = responseJsonObject.toString();
						try {
							mqtt.publish(responseJson);
						} catch (MqttException ex) {
							java.util.logging.Logger.getLogger(GyroscopeResource.class.getName()).log(Level.SEVERE, null, ex);
						}
						mqtt.close();

						Thread.sleep(500);
						
					} catch (Exception e) {
						LOGGER.info(e.toString());
					}
				}
			}

		};
		thread.start();

	}

	public static MQTTResource getInstance() {

		return instance;
	}

	@Override
	public void handleGET(CoapExchange exchange) {

		
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		

		String requestJson = exchange.getRequestText();
			
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		
		if (sensor.equals("status")) {
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "success");
			responseJsonObject.put("yawAngle", String.valueOf(currYawAngle));
			responseJsonObject.put("pitchAngle", String.valueOf(currPitchAngle));
			responseJsonObject.put("rollAngle", String.valueOf(currRollAngle));
			responseJsonObject.put("ultrasonic", String.valueOf(ultraDistance));
			responseJsonObject.put("ifraredray", String.valueOf(irDistance));
			String responseJson = responseJsonObject.toString();
			exchange.respond(responseJson);
		}
			
	}
	

}
