package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.dto.Member;


public interface MemberDao {
	public Member memberSelectByMid(String mid);
	public String memberInsert(Member member);
	public void memberDelete(String mid);
}
