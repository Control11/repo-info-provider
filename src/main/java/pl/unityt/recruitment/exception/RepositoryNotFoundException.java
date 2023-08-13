package pl.unityt.recruitment.exception;

public class RepositoryNotFoundException extends RuntimeException {
    public RepositoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
