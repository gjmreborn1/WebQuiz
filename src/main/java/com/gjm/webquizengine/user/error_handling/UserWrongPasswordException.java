package com.gjm.webquizengine.user.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserWrongPasswordException extends RuntimeException {
}
