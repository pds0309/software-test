package com.prac.softwaretest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.MemberInfoResponse;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.exception.MemberNotFoundException;
import com.prac.softwaretest.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

//    @BeforeEach
//    void setUp() {
//        this.mvc = MockMvcBuilders
//                .standaloneSetup(new MemberController(memberService))
//                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
//                .build();
//    }

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
