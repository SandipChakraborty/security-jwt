package com.sandip.security.jwt.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class CustomGeneralException extends Exception {
    private static final long serialVersionUID = -9079454849611061074L;

    @Getter @Setter
    private HttpStatus httpStatus;

    public CustomGeneralException() {
        super();
    }

    public CustomGeneralException(final String message) {
        super(message);
    }

    public CustomGeneralException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomGeneralException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
