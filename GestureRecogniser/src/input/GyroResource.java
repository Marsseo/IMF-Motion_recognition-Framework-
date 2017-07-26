package input;

import coordinates.Coordinate;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import static input.GestureSimulatorWindow2.coapC;


public class GyroResource extends CoapResource {
	
	
	private double x;
	private double y;
	private double z;
	
	
	
	public GyroResource() throws Exception {
        super("gesture");
	
	}

	
	
	@Override
    public void handlePOST(CoapExchange exchange) {
        //{ "command":"change", "direction":"forward", "speed":"1000" }
        //{ "command":"status" }
        try {
            String requestJson = exchange.getRequestText();
            JSONObject requestJsonObject = new JSONObject(requestJson);
            String command = requestJsonObject.getString("command");
		if (command.equals("change")) {
			x=Double.parseDouble(requestJsonObject.getString("X"));
			y=Double.parseDouble(requestJsonObject.getString("Y"));
			z=Double.parseDouble(requestJsonObject.getString("Z"));
			
			coapC = new Coordinate(x,y,z);
			System.out.println(coapC);
		} else if (command.equals("status")) {
		}
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("result", "success");
		responseJsonObject.put("X", String.valueOf(x));
		responseJsonObject.put("Y", String.valueOf(y));
		responseJsonObject.put("Z", String.valueOf(z));
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
		} catch (Exception e) {

			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "fail");
			String responseJson = responseJsonObject.toString();
			exchange.respond(responseJson);
		}
	}
	
}
