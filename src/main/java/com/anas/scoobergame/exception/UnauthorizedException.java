package com.anas.scoobergame.exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -4261983384022325620L;

    public UnauthorizedException(final String message) {
        super(message);
    }
}
