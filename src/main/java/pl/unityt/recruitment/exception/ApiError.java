package pl.unityt.recruitment.exception;

import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public record ApiError(@NotNull int status, @NotNull String error, String message, @NotNull ZonedDateTime timestamp) {
}
