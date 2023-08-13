package pl.unityt.recruitment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RepositoryNotFoundException.class)
    public ApiError handleException(RepositoryNotFoundException e) {
        log.error(e.toString());
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ApiError(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                ZonedDateTime.now()
        );
    }

}
