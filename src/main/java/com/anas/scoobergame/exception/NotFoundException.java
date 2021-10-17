package com.anas.scoobergame.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6468887396026058223L;

    public NotFoundException(final String message) {
        super(message);
    }
}
