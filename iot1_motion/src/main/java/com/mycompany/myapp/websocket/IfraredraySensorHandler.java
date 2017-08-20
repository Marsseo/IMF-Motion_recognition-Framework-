package com.mycompany.myapp.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mycompany.myapp.controller.HomeController;

@Component
public class IfraredraySensorHandler extends TextWebSocketHandler implements ApplicationListener{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IfraredraySensorHandler.class);
	
	private List<WebSocketSession> list = new Vector<>();
	
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
			JSONObject jsonObject = new JSONObject(json);
			double distance = Double.parseDouble(jsonObject.getString("distance"));
			jsonObject.put("time", getUTCTime(new Date().getTime()));
			jsonObject.put("distance", distance);
			json = jsonObject.toString();
			try {
			for(WebSocketSession session:list){
				
				session.sendMessage(new TextMessage(json));
				
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
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
	
	@PostConstruct
	public void init() throws MqttException{
		
		
		this.myClientId = MqttClient.generateClientId();
		this.topicRequest = "/Hwasung Seo/ifraredray/request";
		this.topicResponse = "/Hwasung Seo/ifraredray/response";
	
		mqttClient = new MqttClient(url, myClientId);
		
		mqttClient.setCallback(callback);
		
		mqttClient.connect();
		
		subscribe();
		
			
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.info("");
		list.add(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		LOGGER.info("");		
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.info("");
		list.remove(session);
	}
	
	public long getUTCTime(long localTime){
		long utcTime = 0;
		TimeZone tz = TimeZone.getDefault();
		try{
			int offset = tz.getOffset(localTime);
			utcTime = localTime + offset;
		}catch(Exception e){}
		return utcTime;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		
		if(event instanceof ContextClosedEvent){
			try {
				close();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
