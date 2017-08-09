package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.dto.Member;


public interface MemberDao {
	public Member memberSelectByMemail(String memail);
	public String memberInsert(Member member);
}
