package com.prac.softwaretest.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("인자로 얻은 전화번호가 13글자 이상일 때는 부적절한 전화번호이다.")
    void itShouldValidatePhoneNumberHasLengthBiggerThanN() {
        // Given
        String phone = "+82106564515951";

        // When
        boolean isValid = validator.test(phone);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("인자로 얻은 전화번호가 +82로 시작하지 않을 때는 부적절한 전화번호이다.")
    void itShouldValidatePhoneNumberDoesNotStart82() {
        // Given
        String phone = "+771065645159";

        // When
        boolean isValid = validator.test(phone);

        // Then
        assertFalse(isValid);
    }

}
