package Motion.server;

import Motion.GyroMotions;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
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
		
		Thread thread = new Thread(){
			@Override
			public void run() {
				while(true){
					try{
						changed();
						Thread.sleep(500);
					}catch(Exception e){
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
		responseJsonObject.put("yawAngle", String.valueOf(currYawAngle) );
		responseJsonObject.put("pitchAngle", String.valueOf(currPitchAngle) );
		responseJsonObject.put("rollAngle", String.valueOf(currRollAngle) );
		
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{"sensor":"change","yawAngle":"100","pitchAngle":"100","rollAngle":"100"} 이런식으로
	//{"sensor":"status"} 이런식으로 요청
	
	
	
		try{
		String requestJson = exchange.getRequestText();
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		if (sensor.equals("gyroscope")) {
			double yawAngle= Double.parseDouble(requestJsonObject.getString("yawAngle"));
			double pitchAngle= Double.parseDouble(requestJsonObject.getString("pitchAngle"));
			double rollAngle= Double.parseDouble(requestJsonObject.getString("rollAngle"));
			currYawAngle=yawAngle;
			currPitchAngle=pitchAngle;
			currRollAngle=rollAngle;
			GyroMotions.gyroAddData(currYawAngle, currPitchAngle, currRollAngle);
		}else if (sensor.equals("status")) {

		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("yawAngle", String.valueOf(currYawAngle));
		responseJsonObject.put("pitchAngle", String.valueOf(currPitchAngle));
		responseJsonObject.put("rollAngle", String.valueOf(currRollAngle));
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		}catch(Exception e){
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "fail");
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		}

	}

}
