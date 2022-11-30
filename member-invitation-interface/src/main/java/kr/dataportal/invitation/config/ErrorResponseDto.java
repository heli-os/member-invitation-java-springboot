package kr.dataportal.invitation.config;

import org.springframework.http.HttpStatus;

/**
 * @author Heli
 * Created on 2022. 11. 30
 */
public record ErrorResponseDto(
        String code,
        String message
) {

    public static final ErrorResponseDto BAD_REQUEST = toErrorResponseDto(HttpStatus.BAD_REQUEST);
    public static final ErrorResponseDto FORBIDDEN = toErrorResponseDto(HttpStatus.FORBIDDEN);
    public static final ErrorResponseDto NOT_FOUND = toErrorResponseDto(HttpStatus.NOT_FOUND);
    public static final ErrorResponseDto INTERNAL_SERVER_ERROR = toErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR);


    private static ErrorResponseDto toErrorResponseDto(final HttpStatus httpStatus) {
        return new ErrorResponseDto(httpStatus.name(), httpStatus.getReasonPhrase());
    }
}
