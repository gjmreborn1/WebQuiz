package com.gjm.webquizengine.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull(message = "Username can't be null")
    @NotEmpty(message = "Username can't be empty")
    private String username;

    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password can't be empty")
    private String password;
}
