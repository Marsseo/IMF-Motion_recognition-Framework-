package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class GyroCotroller {
	
	@RequestMapping("/gyroTest")
	public String gyroTest(){
		return "gyroTest";
	}

}
