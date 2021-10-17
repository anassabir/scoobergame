package com.anas.scoobergame.exception;

public class MapperToPojoException extends AbstractBaseException {

    private static final long serialVersionUID = 7240001193133466057L;

    public MapperToPojoException(final Exception exception) {
        super(exception.getMessage());
    }
}
