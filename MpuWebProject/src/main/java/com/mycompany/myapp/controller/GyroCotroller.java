package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class GyroCotroller {
	@RequestMapping("/chart")
	public String gyroTest(){
		return "chart";
	}

}
