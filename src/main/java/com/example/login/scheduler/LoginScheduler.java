package com.example.login.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.login.mapper.PwHistoryMapper;
import com.example.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginScheduler {
	
	private final LoginService loginService;
	private final PwHistoryMapper pwHistoryMapper;
	
	// 매월 25일 23시 59분 59초 
	//@Scheduled(cron = "")
		public void dormantMemberSchedule() {
		log.info("휴먼 처리 실행 시작");
		loginService.processDormantAccounts();
		log.info("휴먼 처리 완료");
	}
	
	/* 매월 1일 0시 0분 0초에 실핼
	 *  각 사용자 id에서 최근 5개 이외의 이력 삭제
	 */
	@Scheduled(cron = "0 0 0 1 * ?")
	public void cleanOldPwHistory() {
		log.info("이력 삭제");
		
		List<String> idList = pwHistoryMapper.getAllUserIds();
			for(String id : idList) {
				int deletedCount = pwHistoryMapper.deleteOldPwHistory(id);
				log.info("ID: {} -> {}건 삭제", id, deletedCount);
			}
			
			log.info("삭제 완");
	}
}
