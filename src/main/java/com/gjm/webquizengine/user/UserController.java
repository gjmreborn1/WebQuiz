package com.gjm.webquizengine.user;

import com.gjm.webquizengine.user.dto.LoginDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid User user) {
        userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginDto loginDto) {
        return userService.login(loginDto);
    }
}
