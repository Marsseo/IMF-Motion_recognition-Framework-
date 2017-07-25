package Motion.server;

import Motion.MotionCheck;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(ButtonResource.class);
	
	
	private static ButtonResource instance;
	public static String currStatus;
	
	public ButtonResource() throws Exception {
		super("ifraredray");
		instance = this;
		
	}

	public static ButtonResource getInstance() {

		return instance;
	}

	@Override
	public void handleGET(CoapExchange exchange) {

	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{"sensor":"button","status":"on"} 이런식으로
	//{"sensor":"status"} 이런식으로 요청

		try{
		String requestJson = exchange.getRequestText();
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		if (sensor.equals("button")) {
			String status= requestJsonObject.getString("status");
			currStatus=status;
			MotionCheck.buttonAddData(currStatus);
			
		}else if (sensor.equals("status")) {

		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("status", String.valueOf(currStatus));
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
