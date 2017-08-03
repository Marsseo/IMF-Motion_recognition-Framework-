package com.mycompany.myapp.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.myapp.dto.Member;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes({"member"})
public class HomeController {
	
/*	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private Facebook facebook;
	@Autowired
	private ConnectionRepository connectionRepository;
	*/
/*	@Autowired
	private FacebookConnectionFactory connectionFactory;
	
	@Autowired
	private OAuth2Parameters oAuth2Parameters;
	
	public String getAutorizeUrl(){
		OAuth2Operations oAuth2Operations=connectionFactory.getOAuthOperations();
		String authorizeUrl=oAuth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
		return authorizeUrl;
	}
	
	@Value("${facebook.redirect.uri}")
	private String redirectUri;
	
public Facebook getFacebookUserInfo(String code){
	OAuth2Operations oAuth2Operations= connectionFactory.getOAuthOperations();
	
	AccessGrant accessGrant= oAuth2Operations.exchangeForAccess(code, redirectUri, null);
	String accessToken=accessGrant.getAccessToken();
	
	Connection<Facebook> connection=connectionFactory.createConnection(accessGrant);
	Facebook facebook= connection==null? new FacebookTemplate(accessToken):connection.getApi();
	
	UserOperations userOperations=facebook.userOperations();
	FacebookProfile
}
	
OAuth2Operations oAuth2Operations=connectionFactory.getOAuthOperations();
AccessGrant accessGrant= oAuth2Operations.exchangeForAccess(arg0, arg1, arg2);
String accessToken=accessGrant.getAccessToken();
	*/
	

			
		
	@RequestMapping("/")
		public String home() {
		
		return "home";
	}
	
	@RequestMapping("/gyroTest")
	public String GyroTest() {
	
		
		return "gyroTest";
	}
	
	
/*	@Inject
	public HomeController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook=facebook;
		this.connectionRepository= connectionRepository;
	}
	
	@RequestMapping("/")
	public String home(Model model) {
		if(connectionRepository.findPrimaryConnection(Facebook.class)==null){
			
			return "redirect:/connect/facebook";
		}*/
	/*	
		Facebook facebook= new FacebookTemplate((String) session.getAttribute("ACCESS_TOKEN"));
	
		User profile=facebook.fetchObject("me", User.class,"email");
		
		Member member = new Member();
		
		 String memail=profile.getEmail();
		 member.setMemail(memail);
		 
		 model.addAttribute("member",member);*/
/*		
		model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
		PagedList<Post> feed=facebook.feedOperations().getFeed();
		model.addAttribute("feed", feed);
	
		
		return "home";
	}*/
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//	 
///*	@Autowired
//	private FacebookConnectionFactory connectionFactory;
//	
//	
//
//	private OAuth2Operations oAuth2Operations;
//@Autowired
//	private OAuth2Parameters oAuth2Parameters;
//	
//	
//	Facebook facebook;				// 사용을 편안하게 하기위해!
//	String delete;*/
//	
//@Autowired
//	ConnectionFactoryLocator cFactoryLocator; 	// Connection provide Class
//	OAuth2Operations auth2Operations; 		// OAuth2.0 interface
//	FacebookConnectionFactory connectionFactory; 	// facebook Connection Class
//	Facebook facebook;				// 사용을 편안하게 하기위해!
//	String delete;
//	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public void fb(HttpServletRequest req,HttpServletResponse res) throws IOException {
//		logger.info("accessToken을 얻어오기 위한 동의");
//		logger.info("토큰은 setRedirectUri 여기에 지정한 페이지로 돌려줌");
//		/*OAuth2Operations oAuth2Operations=connectionFactory.getOAuthOperations();
//		oAuth2Parameters = new OAuth2Parameters();
//		oAuth2Parameters.setScope("email,public_profile");
//		oAuth2Parameters.setRedirectUri("http://localhost:8080/MpuWebProject/home");
//		
//		
//		String authorizeUrl=oAuth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
//		*/
//
//		//facebook이란 id로 연결객체 생성
//		connectionFactory = (FacebookConnectionFactory) cFactoryLocator.getConnectionFactory("facebook");
//		//OAuth2 인증을 사용하겠다.
//		auth2Operations = connectionFactory.getOAuthOperations();
//		OAuth2Parameters parameters = new OAuth2Parameters();
//		// 얻어올 권한 https://developers.facebook.com/docs/facebook-login/permissions
//		parameters.setScope("email,public_profile");
//		//Redirection 주소
//		parameters.setRedirectUri("http://localhost:8080/MpuWebProject/home");
//		//권한코드 생성
//		String authorizeUrl = auth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
//		//access 토큰요청
//		res.sendRedirect(authorizeUrl);
//	}
///*	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String fb(HttpServletRequest req,HttpServletResponse res) throws IOException {
//		logger.info("accessToken을 얻어오기 위한 동의");
//		logger.info("토큰은 setRedirectUri 여기에 지정한 페이지로 돌려줌");
//		
//		//facebook이란 id로 연결객체 생성
//		connectionFactory = (FacebookConnectionFactory) cFactoryLocator.getConnectionFactory("facebook");
//		//OAuth2 인증을 사용하겠다.
//		auth2Operations = connectionFactory.getOAuthOperations();
//		OAuth2Parameters parameters = new OAuth2Parameters();
//		// 얻어올 권한 https://developers.facebook.com/docs/facebook-login/permissions
//		parameters.setScope("email,public_profile");
//		//Redirection 주소
////		parameters.setRedirectUri("http://localhost:8080/MpuWebProject/home");
//
//		//권한코드 생성
//		String authorizeUrl = auth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
//		//access 토큰요청
//		
////		res.sendRedirect(authorizeUrl);
//		return "home";
//	}*/
//	
//
//	@RequestMapping(value = "home", method = RequestMethod.GET)
//	public String home(String code,HttpServletRequest req,HttpServletResponse res, Model model) {
//	
//		String accessToken = code;
//
//		AccessGrant accessGrant= auth2Operations.exchangeForAccess(accessToken, "http://localhost:8080/MpuWebProject/", null);
//		// 토큰의 유효기간이 지났으면 다시 받아와~
//		Long expireTime =  accessGrant.getExpireTime();
//		if (expireTime != null && expireTime < System.currentTimeMillis()) {
//			accessToken = accessGrant.getRefreshToken();
//			logger.info("accessToken is expired. refresh token = {}" , accessToken);
//		}
//
//		// 컨트롤을 위한 정보 받아오기
//		Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
//		facebook = connection == null ? new FacebookTemplate(accessToken) : connection.getApi();
//
//		// 실제 사용	
//  	/*	User facebookProfile = facebook.userOperations().getUserProfile();	// 사용자 정보가져오기
//		PagedList<Post> feed =  facebook.feedOperations().getFeed();							// 피드정보 가져오기 기본값이 25개
//		*/
//		String [] fields = { "email",  "first_name", "last_name" };
//		User userProfile = facebook.fetchObject("me", User.class, fields);
//		/*delete = facebook.feedOperations().updateStatus("테스트중입니다");
//		System.out.println("deleteid : " + delete);*/
//		
//		// 데이터를 페이지로 넘기자
//		
//		Member member= new Member();
//		String memail= userProfile.getEmail();
//		member.setMemail(memail);
//		model.addAttribute("member", member);
//		System.out.println(memail);
//		return "home";
//
//	}
}
