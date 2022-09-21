package com.prac.softwaretest.member;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.MemberInfoResponse;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.exception.MemberNotFoundException;
import com.prac.softwaretest.member.MemberRepository;
import com.prac.softwaretest.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
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


    @Nested
    @DisplayName("아이디로의 회원 조회")
    class FindMemberById {
        private Member member = null;

        @BeforeEach
        void setMember() {
            member = memberRepository.save(SampleMember.of());
        }

        @Test
        @DisplayName("존재하는 아이디에 대한 조회 요청 시 결과를 성공적으로 응답한다")
        void itShouldReturnResult() {
            //given
            Long id = member.getId();
            //when
            MemberInfoResponse memberInfoResponse = memberService.findById(id);
            //then
            assertThat(memberInfoResponse).isNotNull();
            assertThat(memberInfoResponse.getId()).isEqualTo(id);
            assertThat(memberInfoResponse.getName()).isEqualTo(SampleMember.SUCCESS_NAME);
        }

        @Test
        @DisplayName("존재하지 않는 아이디에 대한 조회 요청 시 예외를 반환합니다")
        void itShouldThrowMemberNotFoundException() {
            //given
            Long id = SampleMember.NOT_EXIST_MEMBER_ID;
            //when
            assertThatThrownBy(() -> memberService.findById(id))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

}
