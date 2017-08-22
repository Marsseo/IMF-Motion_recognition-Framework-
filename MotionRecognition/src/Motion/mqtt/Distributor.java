package Motion.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 
 * @author HwaSung Seo
 */

public class Distributor {
	private String url = "tcp://106.253.56.122:1883";
	private String clientId;
	private String request;
	private String response;
	private int qos = 1;
	private MqttClient mqttClient;
	
	private MqttCallback callback = new MqttCallback(){
		
		@Override
		public void deliveryComplete(IMqttDeliveryToken imdt) {
			
		}
		@Override
		public void messageArrived(String string, MqttMessage mm) throws Exception {
			publish(mm.toString());			
		}

		@Override
		public void connectionLost(Throwable thrwbl) {
			try {
				close();
			} catch (MqttException ex) {
				ex.printStackTrace();
			}
		}
		
	};
	
	public Distributor(String clientId, String sensor) throws MqttException {
		
		this.clientId =  MqttClient.generateClientId();
		this.request = "/"+clientId+"/"+sensor+"/request";
		this.response ="/"+clientId+"/"+ sensor+"/response";
		
		mqttClient = new MqttClient(url, clientId);
		
		mqttClient.setCallback(callback);
		
		mqttClient.connect();
	}
	
	
	public void close() throws MqttException{
		if(mqttClient !=null){
			mqttClient.disconnect();
			mqttClient.close();
			mqttClient = null;
		}
	}
	
	public void subscribe() throws MqttException{
		mqttClient.subscribe(request);
	}
	
	public void publish(String json) throws MqttException{

		MqttMessage mqttMessage = new MqttMessage(json.getBytes());
		mqttMessage.setQos(qos);
		mqttClient.publish(response, mqttMessage);
	}
}
