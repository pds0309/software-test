package com.prac.softwaretest.integ;


import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.MemberInfoResponse;
import com.prac.softwaretest.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

// 실제 Servlet 환경에서 테스트
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// test 전용 프로필을 활성화 하여 로컬(개발) 환경 또는 운영환경과 테스트 환경을 분리
@ActiveProfiles("test")
class MemberIntegrationTest {

    //webEnvironment 활성화로 bean 이 생성되어 있어 주입 가능하다
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("단일 회원 조회 API")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FindMemberByIdTest {
        private Member member;

        @BeforeAll
        void setMember() {
            member = memberRepository.save(SampleMember.of());
        }

        @Test
        @DisplayName("정상적으로 조회 결과를 리턴한다.")
        void shouldReturnMemberInfoResponse() {
            ResponseEntity<MemberInfoResponse> memberInfoResponseResponseEntity = testRestTemplate.exchange(
                    "/api/v1/members/" + member.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<MemberInfoResponse>() {
                    });

            assertThat(memberInfoResponseResponseEntity).isNotNull();
            assertThat(memberInfoResponseResponseEntity.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            assertThat(memberInfoResponseResponseEntity.getBody())
                    .isInstanceOfSatisfying(MemberInfoResponse.class, m -> {
                        assertThat(m.getId()).isEqualTo(member.getId());
                        assertThat(m.getName()).isEqualTo(member.getName());
                    });
        }

        @Test
        @DisplayName("정상적으로 조회 결과를 리턴한다 버전2 - getForEntity")
        void shouldReturnMemberInfoResponse2() {
            ResponseEntity<MemberInfoResponse> memberInfoResponseResponseEntity = testRestTemplate.getForEntity(
                    "/api/v1/members/" + member.getId(),
                    MemberInfoResponse.class
            );
            assertThat(memberInfoResponseResponseEntity).isNotNull();
            assertThat(memberInfoResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(memberInfoResponseResponseEntity.getBody()).isNotNull();
        }

        // 단일 회원 조회 관련 또 다른 테스트 케이스를 구성할 수 있다.
        // void test()

    }
}
