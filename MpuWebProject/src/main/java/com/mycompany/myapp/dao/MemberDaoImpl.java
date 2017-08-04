package com.mycompany.myapp.dao;

import java.sql.Connection;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.dto.Member;

@Component
public class MemberDaoImpl implements MemberDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	Connection conn=null;
	
	@Override
	public Member memberSelectByMemail(String memail){
//		System.out.println(memail);
		Member member=sqlSessionTemplate.selectOne("member.selectByMemail", memail);
//		int member=sqlSessionTemplate.selectOne("member.countAll", memail);
//		System.out.println(member);
		return member;
	}
	
}
