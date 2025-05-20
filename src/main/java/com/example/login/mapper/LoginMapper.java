package com.example.login.mapper;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.login.dto.MemberDto;

@Mapper
public interface LoginMapper {
	//로그인 성공
		int insertLoginHistory(@Param("id") String id);
		
	// 1년 미접속
		List<String> selectDormantIds();
	
	// 휴먼 변경
		int updateMemberToDormant(@Param("cutoff") LocalDateTime cutoff);
		
	// 전체 조회
		List<MemberDto> selectAllMember();
		
	// 메일 전송
		String selectEmailById(@Param("id") String id);
		
	// 휴먼 해제
		int updateActiveToOn(@Param("id") String id); 
		
	// 페이징
		List<MemberDto> selectMemberListByPage(@Param("beginRow") int beginRow,
                @Param("rowPerPage") int rowPerPage);
		
		int selectMemberCount();
		//
		int toggleMemberActive(@Param("id") String id);
		
		// 비밀번호 찾기
		int updateNewPwAfterMail(@Param("id") String id, @Param("pw") String pw);
		
		MemberDto selectMemberOne(@Param("id") String id);
}
