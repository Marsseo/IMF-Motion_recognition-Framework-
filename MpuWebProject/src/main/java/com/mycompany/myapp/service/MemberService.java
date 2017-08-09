package com.mycompany.myapp.service;

import com.mycompany.myapp.dto.Member;

public interface MemberService {
	public Member getMember(String memail);
	public void memberJoin(Member member);
}
