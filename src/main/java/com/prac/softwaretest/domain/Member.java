package com.prac.softwaretest.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "MEMBER_NAME", nullable = false, length = 10)
    private String name;

    @Column(name = "AGE")
    private int age;

    @Builder
    public Member(String name, int age) {
        validName(name);
        this.name = name;
        this.age = age;
    }

    private void validName(String name) {

        Assert.notNull(name, "이름은 NULL일 수 없습니다");

        if ("".equals(name.trim()) || name.length() > 10) {
            throw new IllegalArgumentException("이름은 공백이거나 10글자를 넘을 수 없다");
        }
    }
}
