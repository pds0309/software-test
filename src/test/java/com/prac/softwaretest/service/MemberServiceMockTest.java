package com.prac.softwaretest.service;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/**
 * 요구사항 : 회원 가입
 */

class MemberServiceMockTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService();
    }

    @Test
    @DisplayName("회원이 정상적으로 저장될 수 있다.(회원가입할 수 있다.)")
    void saveMemberTest() {
        SignUpRequest signUpRequest =
                SignUpRequest.builder()
                        .name(SampleMember.SUCCESS_NAME)
                        .age(SampleMember.SUCCESS_AGE)
                        .build();

        Member member = SampleMember.of();
        given(memberRepository.save(member)).willReturn(member);
        SignUpResponse signUpResponse = memberService.saveMember(signUpRequest);

        assertNotNull(signUpResponse);
        assertEquals(member.getId(), signUpResponse.getId());
    }
}
