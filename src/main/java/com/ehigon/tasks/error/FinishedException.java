package com.ehigon.tasks.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "resource is actually finished")
public class FinishedException extends RuntimeException{
}
