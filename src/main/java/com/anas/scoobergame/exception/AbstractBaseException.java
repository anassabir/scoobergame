package com.anas.scoobergame.exception;

public abstract class AbstractBaseException extends RuntimeException {

    private static final long serialVersionUID = 3719964887950949389L;

    public AbstractBaseException(final String message) {
        super(message);
    }
}
