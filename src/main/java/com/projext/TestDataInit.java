package com.projext;

import com.projext.domain.member.Member;
import com.projext.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test1");
        member.setName("양재혁");

        memberRepository.save(member);
    }
}
