package com.gjm.webquizengine.user;

import com.gjm.webquizengine.JwtUtils;
import com.gjm.webquizengine.user.dto.LoginDto;
import com.gjm.webquizengine.user.error_handling.UserAlreadyExistException;
import com.gjm.webquizengine.user.error_handling.UserDoesNotExistException;
import com.gjm.webquizengine.user.error_handling.UserWrongPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    public static final String FAKE_USERNAME = "fake gjm";
    public static final String FAKE_PASSWORD = "fake password";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private UserService userService;
    private User exampleUser;
    private LoginDto realLoginDto;
    private LoginDto fakeLoginDto;
    private LoginDto fakePasswordLoginDto;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userService = new UserService(userRepository, jwtUtils);

        exampleUser = new User();
        exampleUser.setUsername("gjm");
        exampleUser.setPassword("123456");
        exampleUser.setEmail("gjm@gmail.com");

        realLoginDto = new LoginDto();
        realLoginDto.setUsername("gjm");
        realLoginDto.setPassword("123456");

        fakeLoginDto = new LoginDto();
        fakeLoginDto.setUsername(FAKE_USERNAME);
        fakeLoginDto.setPassword(FAKE_PASSWORD);

        fakePasswordLoginDto = new LoginDto();
        fakePasswordLoginDto.setUsername("gjm");
        fakePasswordLoginDto.setPassword(FAKE_PASSWORD);
    }

    @Test
    void register() {
        userRepository.save(exampleUser);

        Assertions.assertThrows(UserAlreadyExistException.class,
                () -> userService.register(exampleUser));
    }

    @Test
    void login() {
        Assertions.assertThrows(UserDoesNotExistException.class,
                () -> userService.login(fakeLoginDto));

        exampleUser.encodePassword();
        userRepository.save(exampleUser);
        Assertions.assertThrows(UserWrongPasswordException.class,
                () -> userService.login(fakePasswordLoginDto));

        Assertions.assertEquals(jwtUtils.generateToken(realLoginDto.getUsername()),
                userService.login(realLoginDto));
    }
}