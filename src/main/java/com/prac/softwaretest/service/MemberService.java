package com.prac.softwaretest.service;


import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.dto.MemberInfoResponse;
import com.prac.softwaretest.dto.SignUpRequest;
import com.prac.softwaretest.dto.SignUpResponse;
import com.prac.softwaretest.exception.MemberNotFoundException;
import com.prac.softwaretest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public SignUpResponse saveMember(SignUpRequest signUpRequest) {

        checkDuplicatedName(signUpRequest.getName());

        Member member = memberRepository.save(signUpRequest.toEntity());
        return new SignUpResponse(member.getId());
    }

    private void checkDuplicatedName(String name) {
        if (memberRepository.findByName(name).isPresent()) {
            throw new RuntimeException("중복되는 이름이 있다.");
        }
    }


    public MemberInfoResponse findById(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

        return MemberInfoResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }

}
