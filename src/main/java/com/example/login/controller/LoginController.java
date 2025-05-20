package com.example.login.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.login.dto.MemberDto;
import com.example.login.service.LoginService;
import com.example.login.service.MailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class LoginController {
	
	private final LoginService loginService;
	private final MailService mailService;
	
	//로그인 성공 시 호출
	@PostMapping("/login")
	public String login(@RequestParam("memberId") String memberId,
	                    @RequestParam("memberPw") String memberPw,
	                    RedirectAttributes redirect,
	                    HttpSession session) {

	    MemberDto member = loginService.login(memberId, memberPw);

	    if (member == null) {
	        redirect.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
	        return "redirect:/loginForm";
	    }

	    session.setAttribute("loginMember", member);

	    return "redirect:/memberList";
	}

	
	//휴먼처리
	@ResponseBody
	@PostMapping("/dormant")
	public String dormant() {
		loginService.processDormantAccounts();
		return "휴먼처리됨";
	}
	
	// 전체 목록 + 페이징
	@GetMapping("/memberList")
	public String memberList(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
	                         Model model) {
	    int rowPerPage = 10;
	    int pagePerGroup = 10;

	    int totalRow = loginService.getMemberTotalCount();
	    int lastPage = (int) Math.ceil((double) totalRow / rowPerPage);

	    int startPage = ((currentPage - 1) / pagePerGroup) * pagePerGroup + 1;
	    int endPage = Math.min(startPage + pagePerGroup - 1, lastPage);

	    int beginRow = (currentPage - 1) * rowPerPage;

	    // ✅ 순서 맞춰서 호출!
	    List<MemberDto> memberList = loginService.getMemberListByPage(beginRow, rowPerPage);

	    model.addAttribute("memberList", memberList);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("lastPage", lastPage);

	    return "memberList";
	}
	
	@PostMapping("/toggleActive")
	public String toggleActive(@RequestParam("id") String id) {
	    loginService.toggleMemberActive(id);
	    return "redirect:/memberList";
	}
	
	// 비밀번호찾기
	@PostMapping("/findPw")
	public String findPw(@RequestParam String id,
	                     @RequestParam String email,
	                     RedirectAttributes redirect) {
		MemberDto member = (MemberDto) loginService.selectMemberOne(id);
	    
		if (member == null || member.getEmail() == null || !member.getEmail().equals(email)) {
		    redirect.addFlashAttribute("msg", "일치하는 회원이 없습니다.");
		    return "redirect:/rechangeMemberPwForm";
		}

	    // 랜덤 비번 생성
	    String tempPw = UUID.randomUUID().toString().substring(0, 8);
	    loginService.updateNewPwAfterMail(id, tempPw);

	    // 이메일로 발송 (생략 가능)
	    mailService.sendMail(email, "임시 비밀번호", "임시 비밀번호는 " + tempPw + "입니다.");

	    redirect.addFlashAttribute("msg", "임시 비밀번호가 이메일로 전송되었습니다.");
	    redirect.addAttribute("memberId", id);
	    return "redirect:/rechangeMemberPwForm";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
	    return "loginForm"; //
	}
	
	@GetMapping("/findPwForm")
	public String findPwForm() {
	    return "findPwForm"; // 🔥 JSP 파일명 그대로!
	}
	
	@GetMapping("/rechangeMemberPwForm")
	public String rechangeMemberPwForm(@RequestParam(required = false) String memberId, Model model) {
	    model.addAttribute("memberId", memberId); // 🔥 JSP에서 이 값 사용
	    return "rechangeMemberPw"; // JSP 파일명
	}
	
	@PostMapping("/rechangeMemberPw")
	public String rechangeMemberPw(
	    @RequestParam("memberId") String memberId,
	    @RequestParam("memberPw") String memberPw,          // 임시 비밀번호
	    @RequestParam("newMemberPw") String newMemberPw,    // 새 비밀번호
	    RedirectAttributes redirect
	) {
	    MemberDto member = loginService.selectMemberOne(memberId);

	    if (!member.getPw().equals(memberPw)) {
	        redirect.addFlashAttribute("msg", "임시 비밀번호가 일치하지 않습니다.");
	        return "redirect:/rechangeMemberPwForm?memberId=" + memberId;
	    }

	    loginService.updateNewPwAfterMail(memberId, newMemberPw);

	    redirect.addFlashAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
	    return "redirect:/loginForm";
	}
}