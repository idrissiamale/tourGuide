package com.muserlocation.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * ErrorResponse's role is to store custom error messages.
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private String requestURL;
    private HttpStatus status;
}
