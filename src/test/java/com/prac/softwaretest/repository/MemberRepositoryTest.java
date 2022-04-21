package com.prac.softwaretest.repository;

import com.prac.softwaretest.domain.Member;
import com.prac.softwaretest.domain.SampleMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("Member 는 Save 될 수 있다.")
    void saveMemberTest() {
        // given
        Member member = SampleMember.of();

        // when
        memberRepository.save(member);

        // then
        Member savedMember = memberRepository.findById(member.getId()).orElse(null);
        System.out.println(savedMember.getId());
        assertNotNull(savedMember);
        assertEquals(member, savedMember);

    }

    @Test
    @DisplayName("Member 는 Save 될 수 있다2")
    void saveMemberTest2() {

        // given
        Member member = SampleMember.of();
        // when
        memberRepository.save(member);
        // then
        Optional<Member> maybeMember = memberRepository.findById(member.getId());
        assertThat(maybeMember)
                .isPresent()
                .hasValueSatisfying(m -> {
                    assertThat(m.getId()).isEqualTo(member.getId());
                    assertThat(m.getAge()).isEqualTo(member.getAge());
                });
    }

}
