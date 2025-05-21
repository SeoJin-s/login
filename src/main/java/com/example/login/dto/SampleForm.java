package com.example.login.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SampleForm {
	@NotBlank(message = "아이디를 입력하세요")
	@Size(min = 4, max = 10, message = "아이디는 4자 이상 10자 이하로 입력하세요.")
	private String name;	
	
	
	@NotNull(message = "나이를 입력하세요")
	@Min(value = 0, message = "나이는 0~200")
	@Max(value = 200, message = "나이는 0~200")
	private int age;
}
