package com.ehigon.tasks.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Intenal server error")
public class InternalException extends RuntimeException{
}
