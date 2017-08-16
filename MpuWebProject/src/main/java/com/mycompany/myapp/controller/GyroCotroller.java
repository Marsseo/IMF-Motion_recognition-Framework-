package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class GyroCotroller {
	
	private static String ipAddress="";
	
	public static String getIpAddress() {
		return ipAddress;
	}

	@RequestMapping(value = "/gyroTest", method = RequestMethod.GET)
	public String gyroTestGet(){
		return "gyroTest";
	}
	
	@RequestMapping(value = "/gyroTest", method = RequestMethod.POST)
	public String gyroTestPost(String ip){
		
		ipAddress = ip;
		
		return "gyroTest";
	}

}
