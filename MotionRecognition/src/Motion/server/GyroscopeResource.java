package Motion.server;

import Motion.GyroMotions;
import Motion.MotionCheck;
import Motion.mqtt.Distributor;
import java.util.logging.Level;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GyroscopeResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(GyroscopeResource.class);

	private static GyroscopeResource instance;
	public static double currYawAngle;
	public static double currRollAngle;
	public static double currPitchAngle;
	
	public GyroscopeResource() throws Exception {
		super("gyroscope");
		instance = this;

		setObservable(true);
		getAttributes().setObservable();
		setObserveType(CoAP.Type.NON);

		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {					
						
						changed();
						Thread.sleep(500);
						
					} catch (Exception e) {
						LOGGER.info(e.toString());
					}
				}
			}

		};
		//thread.start();

	}

	public static GyroscopeResource getInstance() {

		return instance;
	}

	@Override
	public void handleGET(CoapExchange exchange) {

		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("yawAngle", String.valueOf(Math.round(currYawAngle*100)/100.));
		responseJsonObject.put("pitchAngle", String.valueOf(Math.round(currPitchAngle*100)/100.));
		responseJsonObject.put("rollAngle", String.valueOf(Math.round(currRollAngle*100)/100.));

		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//System.out.println("post 방식");
		//{"sensor":"gyroscope","yawAngle":"100","pitchAngle":"100","rollAngle":"100"} 이런식으로
		//{"sensor":"status"} 이런식으로 요청
		// coap://192.168.3.133:5683/gyroscope?sensor=gyroscope&yawAngle=yaw&pitchAngle=pitch&rollAngle=roll 이런식으로 요청

		try {
			String requestJson = exchange.getRequestText();
			if (requestJson.equals("")) {
				String sensor1 = exchange.getRequestOptions().getUriQuery().get(0).split("=")[1];
				String yawAngle1="";
				String pitchAngle1="";
				String rollAngle1="";
				//System.out.println("개이득    :"+exchange.getRequestOptions().getURIQueryCount()); //삭제각
				
				if(exchange.getRequestOptions().getURIQueryCount()>1){
					yawAngle1 = exchange.getRequestOptions().getUriQuery().get(1).split("=")[1];
				pitchAngle1 = exchange.getRequestOptions().getUriQuery().get(2).split("=")[1];
				 rollAngle1 = exchange.getRequestOptions().getUriQuery().get(3).split("=")[1];
				}
				
				//System.out.println("key1 :" + key1);
				//System.out.println("key2 :" + key2);

				if (sensor1.equals("gyroscope")) {
					currYawAngle = Double.parseDouble(yawAngle1);
					currPitchAngle = Double.parseDouble(pitchAngle1);
					currRollAngle = Double.parseDouble(rollAngle1);
					GyroMotions.gyroAddData(currYawAngle, currPitchAngle, currRollAngle);
					
					exchange.respond(String.valueOf(MotionCheck.finalMotion));
				} else if (sensor1.equals("status")) {
					
					//exchange.respond(String.valueOf(Action.motionResult));

				} else {
					exchange.respond("fail");
				}
			} else {
				JSONObject requestJsonObject = new JSONObject(requestJson);
				String sensor = requestJsonObject.getString("sensor");
				if (sensor.equals("gyroscope")) {
					double yawAngle = Double.parseDouble(requestJsonObject.getString("yawAngle"));
					double pitchAngle = Double.parseDouble(requestJsonObject.getString("pitchAngle"));
					double rollAngle = Double.parseDouble(requestJsonObject.getString("rollAngle"));
					currYawAngle = yawAngle;
					currPitchAngle = pitchAngle;
					currRollAngle = rollAngle;
					GyroMotions.gyroAddData(currYawAngle, currPitchAngle, currRollAngle);
				} else if (sensor.equals("status")) {

				}
				JSONObject responseJsonObject = new JSONObject();
				responseJsonObject.put("result", "success");
				responseJsonObject.put("yawAngle", String.valueOf(currYawAngle));
				responseJsonObject.put("pitchAngle", String.valueOf(currPitchAngle));
				responseJsonObject.put("rollAngle", String.valueOf(currRollAngle));
				String responseJson = responseJsonObject.toString();
				exchange.respond(responseJson);
			}

		} catch (Exception e) {
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "fail");
			String responseJson = responseJsonObject.toString();
			exchange.respond(responseJson);
		}

	}
	
	

}
