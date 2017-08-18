package com.mycompany.myapp.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.myapp.dao.MemberDaoImpl;
import com.mycompany.myapp.dto.Member;
import com.mycompany.myapp.service.MemberService;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes({ "member" ,"log", "ip"})
public class HomeController {
	

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	String log="log";
	
	private static String ipAddress="";
	
	@Resource(name = "memberServiceImpl")
	private MemberService service;

	@RequestMapping("fb/login")
	public void login(HttpServletResponse response) throws IOException {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("1541339359250690",
				"9477d672e7f7aec8cc02f4c7a17f3552");
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri("http://localhost:8080/iot1_motion/fb/callback");
		params.setScope("public_profile, email");
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(params);

		response.sendRedirect(authorizeUrl);
	}

	@RequestMapping("fb/callback")
	public String callback(@RequestParam("code") String authorizationCode, HttpServletRequest request) {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("1541339359250690",
				"9477d672e7f7aec8cc02f4c7a17f3552");

		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(authorizationCode,
				"http://localhost:8080/iot1_motion/fb/callback", null);
		String token = accessGrant.getAccessToken();
		request.getSession().setAttribute("facebookToken", token);

		return "redirect:/fb";
	}

	@RequestMapping(value = "/fb")
	public String fb(HttpServletRequest request, Model model) throws IOException {
		String accessToken = (String) request.getSession().getAttribute("facebookToken");
		Facebook facebook = new FacebookTemplate(accessToken);
	
		if (facebook.isAuthorized()) {

			// 페이스북에서 프로필을 읽어온다.
			User profile = facebook.fetchObject("me", User.class, "name,email,cover,picture");
			
			
			// 프로필을 모델로 전송
			model.addAttribute("profile", profile);
			
			// 멤버객체 생성
			Member member = new Member();

			// 필드에 저장
			if (profile.getEmail()==null) {
				member.setMemail("");
			}else{
				member.setMemail(profile.getEmail());
			}
			member.setMname(profile.getName());
			member.setMlevel("1");
			
			member.setMid(profile.getId());
			
/*			
			System.out.println(profile.getName());
			System.out.println(profile.getId());*/
			

			// 전송
			model.addAttribute("member", member);
			
			
//			logger.info("Home");
//			System.out.println("------------------------------------------");
//			System.out.println("프로필출력");
//			System.out.println(profile.getEmail());
//			System.out.println(profile.getName());
//			System.out.println(profile.getId());
//			System.out.println("------------------------------------------");
//			System.out.println("멤버출력");
//			System.out.println(member.getMemail());
//			System.out.println(member.getMname());
//			System.out.println(member.getMid());
			// 회원인지 확인
			
			member = service.getMember(member.getMid());

			// 1. 회원가입이 안되있는 경우
			if (member == null) {
				return "join";
			}
			// 2. 회원가입이 되어있는 경우
			else {
				log="login";
				model.addAttribute("log",log);
				model.addAttribute("member", member);
				model.addAttribute("ip", ipAddress);
				
				return "main";
			}

		} else {
			return "redirect:/fb/login";
		}

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homeGet(Model model) {
		/*model.addAttribute("log",log);*/
		return "main";
	}
	
	
	public static String getIpAddress() {
		return ipAddress;
	}

	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String homePost(String ip, Model model){
		
		ipAddress = ip;
		model.addAttribute("ip", ipAddress);
		
		return "main";
	}
}
