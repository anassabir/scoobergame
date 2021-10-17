package com.anas.scoobergame.exception;

public class NoSuchUserException
        extends RuntimeException {

    private static final long serialVersionUID = 6468887396026058223L;

    public NoSuchUserException(final String message) {
        super(message);
    }
}
