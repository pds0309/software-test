package com.prac.softwaretest.member;

import com.prac.softwaretest.dto.MemberInfoResponse;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = memberService.saveMember(signUpRequest);

        return ResponseEntity.created(URI.create("/members/" + signUpResponse.getId())).build();
//        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberInfoResponse> findById(@PathVariable Long id) {
        MemberInfoResponse memberInfoResponse = memberService.findById(id);

        return ResponseEntity.ok().body(memberInfoResponse);
    }

}
