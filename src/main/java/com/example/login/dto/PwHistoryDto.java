package com.example.login.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class PwHistoryDto {
	private int no;
	private String id;
	private String pw;
	private LocalDateTime changeDate;
	
	// 생성자 insert
	public PwHistoryDto(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
}
