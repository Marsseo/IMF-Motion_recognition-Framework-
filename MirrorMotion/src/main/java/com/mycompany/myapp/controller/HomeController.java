package com.mycompany.myapp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import Motion.Action;
import Motion.ActionInterface;
import Motion.Main;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public static Motion.Main main;
	
/*	@Autowired
	private Action action;

	@Autowired
	private ActionInterface actioninterface;
	*/
    
	@RequestMapping("/")
	public String home() throws Exception  {
		main= new Main(new Action());
		
		main.start();
	
		return "home";
	}
	
	
	
}
