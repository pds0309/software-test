package com.prac.softwaretest.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Long id
 * String name : 널일 수 없으며 이름은 10글자 이하여야 한다.
 * int age : 널일 수 없으며 0 이상 200 미만이어야 한다.
 */
@DisplayName("Member 엔티티 클래스 테스트")
class MemberTest {

    private Member member;

    private static final String SUCCESS_NAME = "김갑환";
    private static final int SUCCESS_AGE = 25;

    @Test
    @DisplayName("멤버 객체를 초기화 할 수 있다.")
    void initMemberTest() {
        // given + when
        member = new Member(SUCCESS_NAME, SUCCESS_AGE);
        // then
        assertEquals(SUCCESS_NAME, member.getName());
        assertEquals(SUCCESS_AGE, member.getAge());
    }

}
