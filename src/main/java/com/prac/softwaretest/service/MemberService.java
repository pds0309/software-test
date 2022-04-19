package com.prac.softwaretest.service;


import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public SignUpResponse saveMember(SignUpRequest signUpRequest) {

        Member member = memberRepository.save(signUpRequest.toEntity());
        return new SignUpResponse(member.getId());
    }
}
