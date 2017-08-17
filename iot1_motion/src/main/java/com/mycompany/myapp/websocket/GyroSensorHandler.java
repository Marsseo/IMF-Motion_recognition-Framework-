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


@Component
public class GyroSensorHandler extends TextWebSocketHandler implements ApplicationListener{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GyroSensorHandler.class);
	
	private List<WebSocketSession> list = new Vector<>();
	private CoapClient coapClient;
	private CoapObserveRelation coapObserveRelation;
	
	
	@PostConstruct
	public void init(){
		coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.109/gyroscope");
		
		System.out.println("test"+coapClient.getURI());
		
		coapObserveRelation = coapClient.observe(new CoapHandler() {
			
			@Override
			public void onLoad(CoapResponse response) {
				String json = response.getResponseText();
				JSONObject jsonObject = new JSONObject(json);
				double yawAngle = Double.parseDouble(jsonObject.getString("yawAngle"));
				double pitchAngle = Double.parseDouble(jsonObject.getString("pitchAngle"));
				double rollAngle = Double.parseDouble(jsonObject.getString("rollAngle"));
				jsonObject.put("time", getUTCTime(new Date().getTime()));
				jsonObject.put("yawAngle", yawAngle);
				jsonObject.put("pitchAngle", pitchAngle);
				jsonObject.put("rollAngle", rollAngle);
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
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
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
			coapObserveRelation.proactiveCancel();
			coapClient.shutdown();
		}
		
	}
}
