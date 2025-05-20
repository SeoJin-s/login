package com.example.login.service;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.login.dto.MemberDto;
import com.example.login.mapper.LoginMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final JavaMailSender mailSender;

    @Override
    public void addLoginHistory(String id) {
        int row = loginMapper.insertLoginHistory(id);
        log.info("로그인 이력 저장: {}, rows={}", id, row);
    }

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

    @Override
    public List<?> getAllMembers() {
        return loginMapper.selectAllMember();
    }
    
    @Override
    public List<MemberDto> getMemberListByPage(int beginRow, int rowPerPage) {
        return loginMapper.selectMemberListByPage(beginRow, rowPerPage);
    }

    @Override
    public int getMemberTotalCount() {
        return loginMapper.selectMemberCount();
    }
    @Override
    public void toggleMemberActive(String id) {
        loginMapper.toggleMemberActive(id);
    }
    
    @Override
    public void updateNewPwAfterMail(String id, String tempPw) {
        loginMapper.updateNewPwAfterMail(id, tempPw);
    }
    
    @Override
    public MemberDto selectMemberOne(String id) {
        return (MemberDto) loginMapper.selectMemberOne(id);
    }
    //로그인
    @Override
    public MemberDto login(String memberId, String memberPw) {
        MemberDto member = loginMapper.selectMemberOne(memberId);
        if (member != null && member.getPw().equals(memberPw)) {
            return member;
        }
        return null;
    }
}
