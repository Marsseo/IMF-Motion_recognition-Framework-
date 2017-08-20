package com.mycompany.myapp.websocket;

import java.util.Date;
import java.util.TimeZone;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class MqttSubscriber {
	
	private String url = "tcp://106.253.56.122:1883";
	private String myClientId;
	private String topicRequest;
	private String topicResponse;
	
	private int qos = 1;
	private MqttClient mqttClient;

	private MqttCallback callback = new MqttCallback(){
		
		@Override
		public void deliveryComplete(IMqttDeliveryToken imdt) {
			
		}
		@Override
		public void messageArrived(String string, MqttMessage mm) throws Exception {
			
			String json = new String(mm.getPayload());
			System.out.println(json);
			
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
	
	public MqttSubscriber( String sensor ) throws MqttException {
		
		this.myClientId = MqttClient.generateClientId();
		this.topicRequest = "/Hwasung Seo/"+sensor+"/request";
		this.topicResponse = "/Hwasung Seo/"+sensor+"/response";
	
		mqttClient = new MqttClient(url, myClientId);
		
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
		mqttClient.subscribe(topicResponse);
	}
	
	public void publish(String targetClientId, String text) throws MqttException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", text);
		String json = jsonObject.toString();
		
		MqttMessage mqttMessage = new MqttMessage(json.getBytes());
		mqttMessage.setQos(qos);
		mqttClient.publish(topicRequest , mqttMessage);
	}
	
		
	public static void main(String[] args) throws Exception{
		MqttSubscriber subscriber = new MqttSubscriber("ultrasonic");
				
		
		subscriber.subscribe();
		System.out.println("start subscriber");

		System.out.println("press any keys...");
		System.in.read();
		Thread.sleep(1000);
	}
	
}
