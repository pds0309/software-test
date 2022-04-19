package com.prac.softwaretest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.softwaretest.domain.SampleMember;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
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
                        .build();
        // given
//        SignUpResponse signUpResponse = new SignUpResponse(1L);
        given(memberService.saveMember(signUpRequest)).willReturn(new SignUpResponse(1L));

        // when
        mvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest))
        )
                // then
                .andExpect(status().isOk());
    }
}
