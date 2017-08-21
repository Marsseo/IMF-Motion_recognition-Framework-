package Motion.server;

import Motion.run.MotionCheck;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IRResource extends CoapResource {

	private static final Logger logger = LoggerFactory.getLogger(IRResource.class);
	
	
	private static IRResource instance;
	public static double irDistance;
	
	public IRResource() throws Exception {
		super("infraredray");
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
		thread.start();
		
	}

	public static IRResource getInstance() {

		return instance;
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("distance", String.valueOf(irDistance) );
		
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{"sensor":"infraredray","distance":"100"} 이런식으로
	//{"sensor":"status"} 이런식으로 요청

		try{
		String requestJson = exchange.getRequestText();
		JSONObject requestJsonObject = new JSONObject(requestJson);
		String sensor = requestJsonObject.getString("sensor");
		if (sensor.equals("infraredray")) {
			double distance= Double.parseDouble(requestJsonObject.getString("distance"));
			irDistance=distance;
			MotionCheck.irAddData(irDistance);
			
		}else if (sensor.equals("status")) {

		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("distance", String.valueOf(irDistance));
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
