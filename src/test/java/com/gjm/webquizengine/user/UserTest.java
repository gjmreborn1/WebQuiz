package com.gjm.webquizengine.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    public static final String PASSWORD_CORRECT = "secret password";
    public static final String PASSWORD_FAKE = "fake secret password";

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword(PASSWORD_CORRECT);
    }

    @Test
    void encodePassword() {
        user.encodePassword();

        assertNotEquals(PASSWORD_CORRECT, user.getPassword());
    }

    @Test
    void equalsCorrectPassword() {
        user.encodePassword();

        assertTrue(user.equalsPassword(PASSWORD_CORRECT));
    }

    @Test
    void equalsFakePassword() {
        user.encodePassword();

        assertFalse(user.equalsPassword(PASSWORD_FAKE));
    }
}