package com.prac.softwaretest.service;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 요구사항 : 회원 가입
 */

@SpringBootTest
@Transactional
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


    @Nested
    @DisplayName("회원가입을 할 수 없다")
    class DoNotSaveMember {

//        @BeforeEach
//        void setUp() {
//            memberRepository.deleteAll();
//        }

        @Test
        @DisplayName("이미 있는 이름으로 회원가입 요청을 한다면")
        void duplicatedMemberName() {

            Member alreadySavedMember = memberRepository.save(SampleMember.of());

            SignUpRequest signUpRequest =
                    SignUpRequest.builder()
                            .name(SampleMember.SUCCESS_NAME)
                            .age(SampleMember.SUCCESS_AGE)
                            .build();

            assertEquals(alreadySavedMember.getName(), signUpRequest.getName());

            assertThrows(RuntimeException.class, () -> {
                memberService.saveMember(signUpRequest);
            });
        }

    }

}
