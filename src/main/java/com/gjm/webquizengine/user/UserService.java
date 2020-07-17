package com.gjm.webquizengine.user;

import com.gjm.webquizengine.JwtUtils;
import com.gjm.webquizengine.user.dto.LoginDto;
import com.gjm.webquizengine.user.error_handling.UserAlreadyExistException;
import com.gjm.webquizengine.user.error_handling.UserDoesNotExistException;
import com.gjm.webquizengine.user.error_handling.UserWrongPasswordException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public void register(User user) {
        User previousUser = userRepository.findByUsername(user.getUsername());
        if(previousUser != null) {
            throw new UserAlreadyExistException();
        }

        userRepository.save(user);
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        if(user == null) {
            throw new UserDoesNotExistException();
        }

        if(user.getPassword().equals(loginDto.getPassword())) {
            return jwtUtils.generateToken(user.getUsername());
        } else {
            throw new UserWrongPasswordException();
        }
    }
}
