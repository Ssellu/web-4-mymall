package com.megait.mymall.service;

import com.megait.mymall.domain.Member;
import com.megait.mymall.domain.MemberType;
import com.megait.mymall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service // @Controller 와 @Repository 사이의 비지니스 로직 담당
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 기본 관리자 계정 생성
    @PostConstruct  // MemberService 객체 생성 시 무조건 실행할 메서드
    public void createAdminMember(){
        /*Member member = new Member();
        member.setEmail("admin@test.com");
        member.setPassword("1q2w3e4r!");
        member.setJoinedAt(LocalDateTime.now());
        member.setMemberType(MemberType.ROLE_ADMIN);
        memberRepository.save(member);*/

        memberRepository.save(Member.builder()
                .email("admin@test.com")
                .password("1q2w3e4r!")
                .joinedAt(LocalDateTime.now())
                .memberType(MemberType.ROLE_ADMIN)
                .build()
        );
    }

}
