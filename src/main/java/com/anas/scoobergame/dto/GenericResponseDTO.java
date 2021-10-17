package com.anas.scoobergame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Collections;
import java.util.List;

//@ApiModel(value = "Response", description = "Response to request")
public final class GenericResponseDTO<T> {

    public static final GenericResponseDTO<Void> OK = GenericResponseDTO.okSingleResult();

    private boolean success;
    @JsonInclude(Include.NON_EMPTY)
    private String reason;
//    private List<T> listResponse;
    private T response;
    private String errorCode;
    private String message;
    private String lang="en";

//    @ApiModelProperty(value = "Success of request", required = true)
    public boolean isSuccess() {
        return success;
    }

//    @ApiModelProperty(value = "Returned payload", required = true)
//    public List<T> getListResponse() {
//        if (listResponse != null) {
//            return listResponse;
//        }
//
//        return Collections.emptyList();
//    }

    public static GenericResponseDTO<Void> okSingleResult() {
        return okSingleResult(null);
    }


    public static <T> GenericResponseDTO<T> okSingleResult(final T returnedObject) {
        final GenericResponseDTO<T> dto = new GenericResponseDTO<>();
        dto.success = true;
        dto.response = returnedObject;
        return dto;
    }

//    public static <T> GenericResponseDTO<T> okListResult(final List<T> returnedList) {
//        final GenericResponseDTO<T> dto = new GenericResponseDTO<>();
//
//        dto.success = true;
//        dto.listResponse = returnedList;
//
//        return dto;
//    }

    public static <T> GenericResponseDTO<T> error(final String reason) {
        final GenericResponseDTO<T> response = new GenericResponseDTO<>();

        response.reason = reason;

        return response;
    }

//    public static <T> GenericResponseDTO<T> error(final String reason, final List<T> returnedList) {
//        final GenericResponseDTO<T> response = new GenericResponseDTO<>();
//
//        response.reason = reason;
//        response.listResponse = returnedList;
//
//        return response;
//    }

    public static <T> GenericResponseDTO<T> error(final String reason, final String errorCode, final String message) {
        final GenericResponseDTO<T> response = new GenericResponseDTO<>();

        response.reason = reason;
        response.errorCode = errorCode;
        response.message = message;

        return response;
    }

//    @ApiModelProperty(value = "Error message if success=false")
    public String getReason() {
        return reason;
    }

    public static GenericResponseDTO<Void> getOK() {
        return OK;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

//    public void setListResponse(List<T> listResponse) {
//        this.listResponse = listResponse;
//    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String getClientTransactionId() {
//        return clientTransactionId;
//    }
//
//    public void setClientTransactionId(String clientTransactionId) {
//        this.clientTransactionId = clientTransactionId;
//    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
