package com.example.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.login.dto.SampleForm;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class SampleController {
	// 입력 폼 페이지 보여주기
	@GetMapping("/addSample")
	public String addSample() {
		return "addSample";
	}

	
	//  폼 제출 처리
	@PostMapping("/addSample")
	public String addSample(@Valid SampleForm sampleForm, Errors err, Model model) {

	    /*
	     *  커맨드 객체 sampleForm이 생성될 때
	     * 1) @Valid를 통해 자동으로 유효성 검사가 수행됨
	     * 2) 유효성 실패 시, Errors 객체에 에러 정보가 담긴다
	     *    (FieldError, ObjectError 등)
	     */

	    log.info(sampleForm.toString()); // 🔍 입력값 디버깅용 로그 출력

	    //  유효성 검사 실패 시 (에러가 존재하면)
	    if (err.hasErrors()) {

	        //  모든 필드 에러를 꺼내서 JSP에서 출력할 수 있도록 모델에 담는다
	        for (FieldError fe : err.getFieldErrors()) {
	            /*
	             * model.addAttribute("필드명 + 'ErrMsg'", 에러 메시지)
	             * 예: name 필드 → nameErrMsg 속성에 메시지 저장
	             *    → JSP에서 ${nameErrMsg}로 출력 가능
	             */
	            model.addAttribute(fe.getField() + "ErrMsg", fe.getDefaultMessage());
	        }

	        //  유효성 실패 → 입력 폼 페이지로 다시 이동 (데이터 유지 & 에러 메시지 표시)
	        return "addSample";
	    }

	    //  유효성 검사 통과 시 → 다른 페이지로 리다이렉트 (성공 처리) 또한 sample 이 DB에 입력
	    return "redirect:/"; // TODO: 성공 후 이동할 URL로 적절히 변경할 것!
	}

}