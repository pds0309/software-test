package com.prac.softwaretest.utils;

import java.util.function.Predicate;

public class PhoneValidator implements Predicate<String> {

    @Override
    public boolean test(String phone) {
        return phone.startsWith("+82") && phone.length() == 13;
    }

}
