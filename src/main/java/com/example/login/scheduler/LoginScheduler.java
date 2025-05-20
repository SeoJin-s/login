package com.example.login.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginScheduler {
	
	private final LoginService loginService;
	
	// 매월 25일 23시 59분 59초 
	//@Scheduled(cron = "")
		public void dormantMemberSchedule() {
		log.info("휴먼 처리 실행 시작");
		loginService.processDormantAccounts();
		log.info("휴먼 처리 완료");
	}
	
}
