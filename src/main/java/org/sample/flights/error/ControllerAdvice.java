package org.sample.flights.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {

        return new ResponseEntity<>(ErrorResponse
                .builder()
                .reason(String.format("Failed validation for field: %s with value: %s Date must be in ISO format: YYYY-mm-dd",
                        exception.getName(), exception.getValue()))
                .errorCode(exception.getErrorCode()).
                build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleInternalServerError() {

        return new ResponseEntity<>(ErrorResponse
                .builder()
                .reason("Internal Server error. Please contact server admin @")
                .errorCode("00000").
                build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
