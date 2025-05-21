package com.example.login.service;

import java.lang.reflect.Member;
import java.util.List;

import com.example.login.dto.MemberDto;

public interface LoginService {
	
	// 로그인 이력
	void addLoginHistory(String id);
	
	// 휴면 전환
	void processDormantAccounts();
	
	// 회원 전체/페이징
	List<?> getAllMembers();
	List<MemberDto> getMemberListByPage(int beginRow, int rowPerPage);
	int getMemberTotalCount();
	
	// 상태 토글
	void toggleMemberActive(String id);
	
	// 비밀번호 업데이트
	void updateNewPwAfterMail(String id, String tempPw);
	
	// 단일 회원 조회
	MemberDto selectMemberOne(String id);
	
	// 로그인
	MemberDto login(String memberId, String memberPw);

	// 추가 기능
	boolean isUsedPwBefore(String id, String newPw);
	void addPwHistory(String id, String newPw);
}

