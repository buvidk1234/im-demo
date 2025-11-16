package com.it.imdemo.shared.exception;

public class BizException extends RuntimeException {

    private final String code;

    public BizException(BizErrorCode errorCode) {
        super(errorCode.message());
        this.code = errorCode.code();
    }

    public BizException(BizErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.code = errorCode.code();
    }

    public String getCode() {
        return code;
    }
}
