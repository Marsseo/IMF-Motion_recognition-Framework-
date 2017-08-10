package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class GyroExploreCotroller {
	@RequestMapping("/gyroExplore")
	public String gyroTest(){
		return "gyroExplore";
	}

}
