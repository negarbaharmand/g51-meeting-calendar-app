package se.lexicon.exception;

public class UserExpiredException extends Exception {

    public UserExpiredException(String message) {
        super(message);
    }

    public UserExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
