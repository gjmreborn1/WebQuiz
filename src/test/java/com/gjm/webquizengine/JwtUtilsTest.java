package com.gjm.webquizengine;

import com.gjm.webquizengine.user.error_handling.JwtSecurityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtUtilsTest {
    public static final String USERNAME = "gjm";

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void decodeToken() {
        String token = jwtUtils.generateToken(USERNAME);

        Assertions.assertEquals(USERNAME, jwtUtils.decodeToken(token));
    }

    @Test
    void validateToken() {
        String token = jwtUtils.generateToken(USERNAME) + "XAXAXA";

        Assertions.assertThrows(JwtSecurityException.class,
                () -> jwtUtils.validateToken(null));
        Assertions.assertThrows(JwtSecurityException.class,
                () -> jwtUtils.validateToken(""));

        Assertions.assertThrows(JwtSecurityException.class,
                () -> jwtUtils.validateToken(token));
    }
}