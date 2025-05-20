package com.example.login.dto;

import lombok.Data;

@Data
public class MemberDto {
	private String id;
	private String pw;
	private String email;
	private String active;
}
