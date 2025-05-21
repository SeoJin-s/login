package com.example.login.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.login.dto.PwHistoryDto;

@Mapper
public interface PwHistoryMapper {
	
	// 사용한 비밀번호 확인 : 재사용 방지
	int countSamePw(@Param("id") String id, @Param("pw") String pw);
	
	// 변경된 비밀번호 이력 추가 
	Integer insertPwHistory(PwHistoryDto dto);
	
	// 전체 id 목록
	List<String> getAllUserIds();
	
	// 하나의 아이디당 최근 변경이력 5개만 남기고 삭제
	int deleteOldPwHistory(@Param("id") String id);
	
	int countPwHistoryById(@Param("id") String id);
	int deleteOldestPwHistory(@Param("id") String id);
}
