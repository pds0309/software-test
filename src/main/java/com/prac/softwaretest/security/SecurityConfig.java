package com.prac.softwaretest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService detailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 토큰 기반 인증 사용 시 csrf 토큰 따로 사용할 필요 X
                .csrf().disable()
                .sessionManagement()
                // 자동으로 세션 만들어주는거 필요없다.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .httpBasic().disable()
//                .formLogin().disable()
                .authorizeRequests()
                // antMatcher: 지정 url 을 white list 로 만들어줌
                .antMatchers("/api/v1/members/**").permitAll()
                .anyRequest()
                .authenticated();
//        http
//                .authorizeRequests()
//                .antMatchers("/","/api/v1/members/login",)
    }


    // 클라이언트로부터 요청을 받으면 AuthenticationManger 가 여기에 인증처리를 DaoAuthenticationProvider
    // DaoAuthenticationProvider: AuthenticationProvider 구현체로 사용자 인증 정보를 DB와 대조해줌
    // PasswordEncorder 필수
    // UserDetailsService 설정 필요
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(detailsService);
        return provider;
    }

}
