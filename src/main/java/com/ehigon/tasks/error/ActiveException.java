package com.ehigon.tasks.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "resource is not active (future task)")
public class ActiveException extends RuntimeException{
}
