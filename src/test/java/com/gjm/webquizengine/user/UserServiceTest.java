package com.gjm.webquizengine.user;

import com.gjm.webquizengine.JwtUtils;
import com.gjm.webquizengine.user.dto.LoginDto;
import com.gjm.webquizengine.user.error_handling.UserAlreadyExistException;
import com.gjm.webquizengine.user.error_handling.UserDoesNotExistException;
import com.gjm.webquizengine.user.error_handling.UserWrongPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    public static final String FAKE_USERNAME = "fake gjm";
    public static final String FAKE_PASSWORD = "fake password";

    @Mock
    private JwtUtils jwtUtils;

    private User exampleUser;
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserRepository userRepository = new FakeUserRepository();
        exampleUser = new User("gjm", "123456", "gjm@gmail.com");
        userService = new UserService(userRepository, jwtUtils);

        exampleUser.encodePassword();
        userRepository.save(exampleUser);
    }

    @Test
    void registerExistingUser() {
        assertThrows(UserAlreadyExistException.class, () -> userService.register(exampleUser));
    }

    @Test
    void loginNoExistingUser() {
        LoginDto fakeLoginDto = new LoginDto(FAKE_USERNAME, FAKE_PASSWORD);

        assertThrows(UserDoesNotExistException.class, () -> userService.login(fakeLoginDto));
    }

    @Test
    void loginFakePassword() {
        LoginDto fakePasswordLoginDto = new LoginDto("gjm", FAKE_PASSWORD);

        assertThrows(UserWrongPasswordException.class, () -> userService.login(fakePasswordLoginDto));
    }

    @Test
    void loginCorrect() {
        LoginDto realLoginDto = new LoginDto("gjm", "123456");
        when(jwtUtils.generateToken(realLoginDto.getUsername())).thenReturn("token");

        assertEquals(jwtUtils.generateToken(realLoginDto.getUsername()), userService.login(realLoginDto));
    }
}