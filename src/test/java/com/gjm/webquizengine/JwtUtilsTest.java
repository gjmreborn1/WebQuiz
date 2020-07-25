package com.gjm.webquizengine;

import com.gjm.webquizengine.user.error_handling.JwtSecurityException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {
    public static final String USERNAME = "gjm";

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void decodeToken() {
        String token = jwtUtils.generateToken(USERNAME);

        assertEquals(USERNAME, jwtUtils.decodeToken(token));
    }

    @Test
    void validateIncorrectToken() {
        assertThrows(JwtSecurityException.class, () -> jwtUtils.validateToken(null));
        assertThrows(JwtSecurityException.class, () -> jwtUtils.validateToken(""));
    }

    @Test
    void validateMalformedToken() {
        String token = jwtUtils.generateToken(USERNAME) + "XAXAXA";

        assertThrows(JwtSecurityException.class, () -> jwtUtils.validateToken(token));
    }

    @Test
    void validateCorrectToken() {
        String token = jwtUtils.generateToken(USERNAME);

        assertDoesNotThrow(() -> jwtUtils.validateToken(token));
    }
}