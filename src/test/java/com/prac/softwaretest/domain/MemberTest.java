package com.prac.softwaretest.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

/**
 * Long id
 * String name : 이름은 10글자 이하여야 한다.
 * int age : 0 이상 200 미만이어야 한다.
 */
@DisplayName("Member 엔티티 클래스 테스트")
class MemberTest {

    private Member member;

    @BeforeEach
    void prepareMember() {
        member = new Member();
    }

}
