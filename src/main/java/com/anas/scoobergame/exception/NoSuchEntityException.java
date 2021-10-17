package com.anas.scoobergame.exception;

public class NoSuchEntityException
        extends AbstractBaseException {

    private static final long serialVersionUID = -3767566628664648464L;

    public NoSuchEntityException(final String message) {
        super(message);
    }
}
