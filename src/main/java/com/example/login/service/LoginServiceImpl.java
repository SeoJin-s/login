package com.example.login.service;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.login.dto.MemberDto;
import com.example.login.dto.PwHistoryDto;
import com.example.login.mapper.LoginMapper;
import com.example.login.mapper.PwHistoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	
	// Mapper
    private final LoginMapper loginMapper;
    private final JavaMailSender mailSender;
    private final PwHistoryMapper pwHistoryMapper;
    
    /**
     * 로그인 성공 시 로그인 이력 추가
     */
    @Override
    public void addLoginHistory(String id) {
        int row = loginMapper.insertLoginHistory(id);
        log.info("로그인 이력 저장: {}, rows={}", id, row);
    }
    /**
     * 매월 25일 23시 59분 59초
     * 1년 이상 미접속 회원을 휴면처리 후, 안내 메일 전송
     */
    @Override
    public void processDormantAccounts() {
        List<String> dormantIds = loginMapper.selectDormantIds();

        if (dormantIds.isEmpty()) {
            log.info("휴면 대상 없음");
            return;
        }

        int row = loginMapper.updateMemberToDormant(null);
        log.info("휴면 처리된 회원 수: {}", row);

        for (String id : dormantIds) {
            sendDormantMail(id);
        }
    }
    /**
     * 휴면 안내 메일 전송
     */
    private void sendDormantMail(String memberId) {
        String email = loginMapper.selectEmailById(memberId); // 🔥 mapper에 정의돼 있어야 함

        if (email == null || email.isBlank()) {
            log.warn("이메일 없음 → {}", memberId);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[Notice] 휴면 계정 안내");
            message.setText(
                    String.format("""
                    안녕하세요, %s 님.
                    
                    1년 이상 로그인 기록이 없어 계정이 휴면 상태로 전환되었습니다.

                    로그인 시 자동 복구되며,
                    보안을 위해 비밀번호를 꼭 변경해 주세요!

                    감사합니다.
                    """, memberId)
            );

            mailSender.send(message);
            log.info("메일 발송 완료 → {}", email);
        } catch (Exception e) {
            log.error(" 메일 전송 실패 → {} : {}", email, e.getMessage());
        }
    }
    /**
     * 전체 회원 목록 조회 (관리자용)
     */
    @Override
    public List<?> getAllMembers() {
        return loginMapper.selectAllMember();
    }
    
    /**
     * 회원 목록 페이징 조회
     */
    @Override
    public List<MemberDto> getMemberListByPage(int beginRow, int rowPerPage) {
        return loginMapper.selectMemberListByPage(beginRow, rowPerPage);
    }

    /**
     * 전체 회원 수 조회
     */
    @Override
    public int getMemberTotalCount() {
        return loginMapper.selectMemberCount();
    }
    
    /**
     * 회원 상태(활성/비활성) 토글
     */
    @Override
    public void toggleMemberActive(String id) {
        loginMapper.toggleMemberActive(id);
    }
    
    /**
     * 임시 비밀번호 또는 새 비밀번호 저장 (비번 찾기 후)
     */
    @Override
    public void updateNewPwAfterMail(String id, String tempPw) {
        loginMapper.updateNewPwAfterMail(id, tempPw);
    }
    
    /**
     * 단일 회원 정보 조회
     */
    @Override
    public MemberDto selectMemberOne(String id) {
        return (MemberDto) loginMapper.selectMemberOne(id);
    }
    //로그인
    @Override
    public MemberDto login(String memberId, String memberPw) {
    	MemberDto member = loginMapper.selectMemberOne(memberId);

        if (member != null) {
            log.info("입력 PW: '{}'", memberPw);
            log.info("DB PW: '{}'", member.getPw());

            if (member.getPw().trim().equals(memberPw.trim())) {
                return member;
            } else {
                log.warn("비밀번호 불일치!");
            }
        } else {
            log.warn("아이디 '{}' 에 해당하는 회원 없음!", memberId);
        }
        return null;
    }
    
    // 이전 비밀번호
    @Override
    public boolean isUsedPwBefore(String id, String newPw) {
    	int count = pwHistoryMapper.countSamePw(id, newPw);
    	return count > 0;
    }
    
    @Override
    public void addPwHistory(String id, String newPw) {
        // 1. 새 비밀번호 이력 추가
        pwHistoryMapper.insertPwHistory(new PwHistoryDto(id, newPw));
        
        // 2. 현재 이력 개수 조회
        int count = pwHistoryMapper.countPwHistoryById(id);

        // 3. 6개 이상이면 → 가장 오래된 이력 삭제
        if (count > 5) {
            int deleted = pwHistoryMapper.deleteOldestPwHistory(id);
            log.info("이력 정리: ID {} → 가장 오래된 {}건 삭제", id, deleted);
        }
    }
}
