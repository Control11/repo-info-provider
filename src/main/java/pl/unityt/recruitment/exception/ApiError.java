package pl.unityt.recruitment.exception;

import java.time.ZonedDateTime;

public record ApiError(int status, String error, String message, ZonedDateTime timestamp) {
}
