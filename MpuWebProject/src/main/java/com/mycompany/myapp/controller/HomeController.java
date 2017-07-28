package com.mycompany.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.myapp.dto.Member;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
/*	private Facebook facebook;
	private ConnectionRepository connectionRepository;
	*/
	//이 클래스라 로거임을 선언
		private static final Logger LOGGER= LoggerFactory.getLogger(HomeController.class);

		
		
		
		@RequestMapping("/")
		public String home() {
		
			LOGGER.info("/요청처리함");
			return "home";
		}

/*	public HomeController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook=facebook;
		this.connectionRepository= connectionRepository;
	}
	
	@RequestMapping("/")
	public String home(Model model) {
		if(connectionRepository.findPrimaryConnection(Facebook.class)!=null){
			model.addAttribute("facebookProfile",facebook.
		}
		
		return "home";
	}*/

	
}
