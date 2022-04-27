package com.prac.softwaretest.security.auth;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Spring Security 에서 로그인 요청으로부터 얻어온 인증 정보를 가지고 저장소에서 유저 정보를 가져오는 역할을 함
// 여기서는 DB에서 유저 정보를 `username` 을 이용해 가져오는 역할을 한다.
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * - `loadUserByUsername()`
     * - `username` 으로 사용자 정보 조회 한다.
     * - 권한 조회해 SimpleGrantedAuthority 목록을 세팅
     * - Authentication -> principal 객체에 UserDetails 저장
     * - Authentication -> authorities 객체에 권한 저장
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없음"));
        return new MemberDetails(member);
    }
}
