package kr.dataportal.invitation.config.aop;

import kr.dataportal.invitation.config.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
@RestControllerAdvice
public class ApiRestControllerAdvice {

    /**
     * 400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorResponseDto handleException(ConstraintViolationException e) {
        // TODO logging
        return ErrorResponseDto.BAD_REQUEST;
    }

    /**
     * 400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResponseDto handleException(MethodArgumentNotValidException e) {
        // TODO logging
        return ErrorResponseDto.BAD_REQUEST;
    }

    /*
     * 403
     */
    // TODO Authorization Exception Handling

    /**
     * 404
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    ErrorResponseDto handleException(NoHandlerFoundException e) {
        // TODO logging
        return ErrorResponseDto.NOT_FOUND;
    }

    /**
     * 500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorResponseDto handleException(Exception e) {
        // TODO logging
        return ErrorResponseDto.INTERNAL_SERVER_ERROR;
    }
}
