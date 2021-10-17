package com.anas.scoobergame.exception;

import com.anas.scoobergame.dto.GenericResponseDTO;
import com.anas.scoobergame.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerController {
    private static final Logger log = (Logger) LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponseDTO<String> handleException(final Exception e) {
        logError("General Exception", e);
        return GenericResponseDTO.error(String.format("General Exception <%s>: %s ", e.getStackTrace()[0].getClassName(), e.getMessage()));
    }

    private static void logError(final String title, final Exception e) {
        //log.error(String.format("%s <%s>: %s", title, e.getStackTrace()[0].getClassName(), ExceptionUtils.getStackTrace(e)));
        log.error(title,e);
    }

    @ExceptionHandler(RestInternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericResponseDTO<String> handleRestInternalCustomException(final RestInternalException e) {
        logError("rest internal custom exception", e);
        return GenericResponseDTO.error(String.format("Internal Error <%s>: %s", e.getStackTrace()[0].getClassName(), e.getDetialMessage()), e.getCode(), e.getInternalMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public GenericResponseDTO<String> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        logError("Method not allowed",e);
        return GenericResponseDTO.error(String.format("Method not allowed <%s>: %s", e.getStackTrace()[0].getClassName(), e.getMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GenericResponseDTO<String> handleNoSuchUserException(final NoSuchUserException e) {
        logError("Unauthorized",e);
        return GenericResponseDTO.error(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public GenericResponseDTO<String> handleBaseException(final AbstractBaseException e) {
        logError("Base exception",e);
        return GenericResponseDTO.error(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public GenericResponseDTO<String> handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        logError("Data Integrity exception",e);
        return GenericResponseDTO.error(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GenericResponseDTO<String> handleUnauthorizedException(final UnauthorizedException e) {
        logError("Unauthorized Exception",e);
        return GenericResponseDTO.error(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GenericResponseDTO<String> handleNotFoundException(final NotFoundException e) {
        logError("Not Found Exception",e);

        return GenericResponseDTO.error(e.getMessage());
    }

}
