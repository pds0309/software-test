package com.prac.softwaretest.integ;


import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.MemberInfoResponse;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

// 실제 Servlet 환경에서 테스트
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// test 전용 프로필을 활성화 하여 로컬(개발) 환경 또는 운영환경과 테스트 환경을 분리
// 이 예제에서는 테스트 환경에서만 In-Memory H2 데이터베이스를 사용하도록 하는 설정을 하였다
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberIntegrationTest {

    //webEnvironment 활성화로 bean 이 생성되어 있어 주입 가능하다
    @Autowired
    private TestRestTemplate testRestTemplate;

    // 회원 조회 로직의 Flow 체크를 위해 사전 작업이 필요해 repository 를 주입하여 사용했음
    @Autowired
    private MemberRepository memberRepository;

    private Member member = null;

    @BeforeAll
    void setMember() {
        member = memberRepository.save(SampleMember.of());
    }

    @Nested
    @DisplayName("단일 회원 조회 API")
            // 클래스 전역으로 필요한 사전 작업(회원 하나 미리 등록해두기) 을 가능하게 해줌
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FindMemberByIdTest {
//        private Member member;

//        @BeforeAll
//        void setMember() {
//            member = memberRepository.save(SampleMember.of());
//        }

        // exchange 요청 방식
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

        // getForEntity 요청 방식
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

    @Nested
    @DisplayName("회원 가입 API")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RegisterMemberTest {

        //        private Member member;
        private HttpHeaders headers = new HttpHeaders();

        @BeforeAll
        void setMember() {
            headers.add("Content-Type", String.valueOf(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("새로운 회원으로 정상적으로 가입합니다")
        void shouldResponseRegisterSuccess() {

            // existed real member : 김갑환
            SignUpRequest signUpRequest =
                    SignUpRequest.builder()
                            .name("최번개")
                            .age(24)
                            .build();
            HttpEntity<SignUpRequest> request = new HttpEntity<>(signUpRequest, headers);
            assertThat(
                    testRestTemplate.exchange(
                            "/api/v1/members",
                            HttpMethod.POST,
                            request,
                            new ParameterizedTypeReference<Void>() {
                            })
            ).isNotNull()
                    .isInstanceOfSatisfying(ResponseEntity.class, re -> {
                        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                        assertThat(re.getHeaders().containsKey("Location")).isTrue();

                        Long newMemberId = Long.parseLong(
                                re.getHeaders()
                                        .get("Location")
                                        .get(0)
                                        .replace("/members/", ""));
                        assertThat(memberRepository.findById(newMemberId))
                                .isPresent();
                    });
        }

        @Test
        @DisplayName("이미 존재하는 이름으로 가입을 시도해 가입에 실패합니다")
        void shouldThrowsRuntimeExceptionDueToDuplicatedNameRequest() {
            // existed real member : 김갑환
            SignUpRequest signUpRequest =
                    SignUpRequest.builder()
                            .name("김갑환")
                            .age(24)
                            .build();
            assertThat(
                    testRestTemplate.exchange(
                            "/api/v1/members",
                            HttpMethod.POST,
                            new HttpEntity<>(signUpRequest, headers),
                            new ParameterizedTypeReference<Void>() {
                            })
            ).isInstanceOfSatisfying(
                    ResponseEntity.class, re ->
                            // 이 테스트에서 500 status 를 발견하고 핸들러에 추가함
                            assertThat(re.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
            );
        }

        @Test
        @DisplayName("잘못된 나이의 입력으로 가입을 시도해 가입에 실패합니다")
        void shouldThrowsMethodArgumentNotValidExceptionDueToInvalidAgeRequest() {
            SignUpRequest signUpRequest =
                    SignUpRequest.builder()
                            .name("장거한")
                            .age(-999)
                            .build();
            assertThat(
                    testRestTemplate.exchange(
                            "/api/v1/members",
                            HttpMethod.POST,
                            new HttpEntity<>(signUpRequest, headers),
                            new ParameterizedTypeReference<Void>() {
                            })
            ).isInstanceOfSatisfying(
                    ResponseEntity.class, re ->
                            assertThat(re.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
            );
        }

    }
}
