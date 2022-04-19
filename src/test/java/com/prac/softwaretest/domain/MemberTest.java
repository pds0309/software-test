package com.prac.softwaretest.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

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

    private static Stream<String> blankStrings() {
        return Stream.of("", " ", null);
    }

    @Test
    @DisplayName("멤버 객체를 초기화 할 수 있다.")
    void initMemberTest() {
        // given + when
        member = new Member(SUCCESS_NAME, SUCCESS_AGE);
        // then
        assertEquals(SUCCESS_NAME, member.getName());
        assertEquals(SUCCESS_AGE, member.getAge());
    }

    @ParameterizedTest
    @MethodSource("blankStrings")
    @ValueSource(strings = "열글자가넘는길고더러운이름이다")
    @DisplayName("Member의 이름은 null이거나 공백이거나 10글자 초과일 수 없다.")
    void initMemberFailWithInvalidNameParameter(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            Member.builder()
                    .name(name)
                    .age(25)
                    .build();
        });
    }


}
