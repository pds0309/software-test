package com.prac.softwaretest.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneValidatorTest {

    private PhoneValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PhoneValidator();
    }

    @Test
    void itShouldValidatePhoneNumber() {
        // Given
        String phone = "+821065645159";

        // When
        boolean isValid = validator.test(phone);

        // Then
        assertTrue(isValid);
    }

}
