package com.mycompany.myapp.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.mycompany.myapp.websocket.ActionHandler;

import Motion.Interfaces.ActionInterface;

@Component
public class Action implements ActionInterface  {
	
	@Override
	public void action(String result) {
		
		switch (result) {
			case "left":
				System.out.println("herasdf");
				if (!ActionHandler.list.isEmpty()) {
					try {
						for (WebSocketSession session : ActionHandler.list) {
							session.sendMessage(new TextMessage("left"));
							
							System.out.println("kk");
						}
					} catch (Exception e) {
						
					}
				}
				break;
			case "right":
				
				break;
			
			default:
				if (!ActionHandler.list.isEmpty()) {
					try {
						for (WebSocketSession session : ActionHandler.list) {
							session.sendMessage(new TextMessage("left"));
							System.out.println("---------------------------------------------------");
							System.out.println("---------------------------------------------------");
							System.out.println("---------------------------------------------------");
							System.out.println("왼쪽이랍신다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
							System.out.println("---------------------------------------------------");
							System.out.println("---------------------------------------------------");
							System.out.println("---------------------------------------------------");
							System.out.println("---------------------------------------------------");
						}
					} catch (Exception e) {
						
					}
				}
				
				break;
		}
		
	}
	
}
