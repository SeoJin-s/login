package com.example.login.dto;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoginHistoryDto {
	private int no;
	private String id;
	private LocalDateTime logindate;
}
