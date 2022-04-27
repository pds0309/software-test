package com.prac.softwaretest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.MemberInfoResponse;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.exception.MemberNotFoundException;
import com.prac.softwaretest.security.SecurityConfig;
import com.prac.softwaretest.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest 의 경우 스프링 시큐리티도 빈 스캔 대상에 포함되어져 주입됨
// 이 경우 Spring Security에 보통 다른 여러 의존성들이 포함되어 있는데 얘네들 때매 다음과 같은 예외 발생함
//Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'securityConfig' defined in file [D:\spring_security_basic\software-test\software-test\target\classes\com\prac\softwaretest\security\SecurityConfig.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.security.crypto.password.PasswordEncoder' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
@WebMvcTest(controllers = MemberController.class,
        excludeFilters = {
        // SecurityConfig 에 대한 의존성을 테스트를 위한 스캔 대상에서 제외함
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders
            .webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("POST: /api/v1/members : 회원을 저장합니다")
    void saveMemberTest() throws Exception {
        SignUpRequest signUpRequest =
                SignUpRequest.builder()
                        .name(SampleMember.SUCCESS_NAME)
                        .age(SampleMember.SUCCESS_AGE)
                        .password("1234")
                        .build();
        // given
//        SignUpResponse signUpResponse = new SignUpResponse(1L);
        given(memberService.saveMember(any())).willReturn(new SignUpResponse(1L));

        // when
        mvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest))
        )
                // then
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("Get: /api/v1/members/{id} : 회원 하나를 조회합니다")
    @Nested
    class FindById {

        @DisplayName("등록된 Member id 를 입력받았어요")
        @Nested
        class ExistResourceRequest {

            @DisplayName("200:OK 와 MemberDTO를 반환")
            @Test
            void itShouldReturnOkAndMember() throws Exception {
                Long id = 1L;
                given(memberService.findById(id)).
                        willReturn(MemberInfoResponse.builder()
                                .name(SampleMember.SUCCESS_NAME)
                                .id(id).build());

                //when
                ResultActions actions = mvc.perform(get("/api/v1/members/" + id)).andDo(print());
                //then
                actions
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").value(SampleMember.SUCCESS_NAME))
                        .andDo(print());
            }
        }

        @DisplayName("등록되지 않은 Member Id 를 입력 받았어요")
        @Nested
        class NotExistsResourceRequest {

            @DisplayName("404:NOT_FOUND 예외를 반환")
            @Test
            void itShouldThrowsNotFoundException() throws Exception {
                //given
                given(memberService.findById(SampleMember.NOT_EXIST_MEMBER_ID))
                        .willThrow(new MemberNotFoundException());

                //when
                mvc.perform(get("/api/v1/members/" + SampleMember.NOT_EXIST_MEMBER_ID))
                        .andExpect(status().isNotFound())
                        .andDo(print());
            }

        }

    }

}
