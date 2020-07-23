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

        exampleUser = new User("gjm", "123456", "gjm@gmail.com");

        realLoginDto = new LoginDto("gjm", "123456");
        fakeLoginDto = new LoginDto(FAKE_USERNAME, FAKE_PASSWORD);
        fakePasswordLoginDto = new LoginDto("gjm", FAKE_PASSWORD);
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