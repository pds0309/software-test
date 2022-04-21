package com.prac.softwaretest.domain;

public class SampleMember {

    public static final String SUCCESS_NAME = "김갑환";
    public static final int SUCCESS_AGE = 25;

    public static final Long NOT_EXIST_MEMBER_ID = 0L;

    public static Member of() {
        return new Member(SUCCESS_NAME, SUCCESS_AGE);
    }

}
