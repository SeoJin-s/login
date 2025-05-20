package com.example.login.service;

import java.lang.reflect.Member;
import java.util.List;

import com.example.login.dto.MemberDto;

public interface LoginService {
	
	//로그인 저장
	void addLoginHistory(String id);
	
	// 1년 이상 미접속 휴먼 ㄱ
	void processDormantAccounts();
	
	// 전체
	List<?> getAllMembers();
	
	// 페이징
	List<MemberDto> getMemberListByPage(int beginRow, int rowPerPage);
	int getMemberTotalCount();
	
	void toggleMemberActive(String id);
	
	void updateNewPwAfterMail(String id, String tempPw);
	
	MemberDto selectMemberOne(String id);
	
	MemberDto login(String memberId, String memberPw);
	
	
}
