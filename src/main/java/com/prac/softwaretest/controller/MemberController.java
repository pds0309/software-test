package com.prac.softwaretest.controller;

import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = memberService.saveMember(signUpRequest);

//        return ResponseEntity.created(URI.create("/members/" + signUpResponse.getId())).build();
        return ResponseEntity.ok().build();
    }

}
