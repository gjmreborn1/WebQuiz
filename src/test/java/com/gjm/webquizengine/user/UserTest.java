package com.gjm.webquizengine.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    public static final String PASSWORD = "secret password";
    public static final String PASSWORD_FAKE = "fake secret password";

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword(PASSWORD);
    }

    @Test
    void encodePassword() {
        user.encodePassword();

        Assertions.assertNotEquals(PASSWORD, user.getPassword());
    }

    @Test
    void equalsPassword() {
        user.encodePassword();

        Assertions.assertTrue(user.equalsPassword(PASSWORD));
        Assertions.assertFalse(user.equalsPassword(PASSWORD_FAKE));
    }
}