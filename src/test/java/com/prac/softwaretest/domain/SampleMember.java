package com.prac.softwaretest.domain;

public class SampleMember {

    private static final String SUCCESS_NAME = "김갑환";
    private static final int SUCCESS_AGE = 25;

    public static Member of() {
        return new Member(SUCCESS_NAME, SUCCESS_AGE);
    }

}
