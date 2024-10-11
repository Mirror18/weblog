package com.mirror.weblog.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mirror
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();

    }
}
