package com.prac.softwaretest.service;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 요구사항 : 회원 가입
 */

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("회원이 정상적으로 저장될 수 있다.(회원가입할 수 있다.)")
    void saveMemberTest() {
        SignUpRequest signUpRequest =
                SignUpRequest.builder()
                        .name(SampleMember.SUCCESS_NAME)
                        .age(SampleMember.SUCCESS_AGE)
                        .build();

        SignUpResponse signUpResponse = memberService.saveMember(signUpRequest);
        assertNotNull(signUpResponse);

        Member member = memberRepository.findById(signUpResponse.getId()).orElse(null);
        assertNotNull(member);
    }
    
}
