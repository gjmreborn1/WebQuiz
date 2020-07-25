package com.gjm.webquizengine.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    public static final String PASSWORD = "secret password";
    public static final String PASSWORD_FAKE = "fake secret password";

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(null, PASSWORD, null);
    }

    @Test
    void encodePassword() {
        user.encodePassword();

        assertNotEquals(PASSWORD, user.getPassword());
    }

    @Test
    void equalsPassword() {
        user.encodePassword();

        assertTrue(user.equalsPassword(PASSWORD));
        assertFalse(user.equalsPassword(PASSWORD_FAKE));
    }
}