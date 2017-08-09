package com.mycompany.myapp.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
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
import org.springframework.web.bind.support.SessionStatus;

import com.mycompany.myapp.dto.Member;
import com.mycompany.myapp.service.MemberService;

@Controller
@SessionAttributes({"member"})
public class JoinController {
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

	@Resource(name="memberServiceImpl")
	private MemberService service;
	
	@RequestMapping("/join")
	public String join() {
		return "join";
		
	}
	
	@RequestMapping("join/insert")

	public String insert(Member member, User profile, String name, String email, String mid) {
	
		
		member.setMname(name);
		member.setMemail(email);
		
		member.setMid(mid);
		
		logger.info("join");
		System.out.println("프로필출력");
		System.out.println(profile.getEmail());
		System.out.println(profile.getName());
		System.out.println(profile.getId());
		System.out.println("------------------------------------------");
		System.out.println("멤버출력");
		System.out.println(member.getMemail());
		System.out.println(member.getMname());
		System.out.println(member.getMid());
		
		service.memberJoin(member);
		
		return "redirect:/";
	}

	
	
}
