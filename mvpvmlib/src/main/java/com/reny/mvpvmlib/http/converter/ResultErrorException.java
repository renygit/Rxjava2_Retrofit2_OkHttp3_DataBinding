package com.reny.mvpvmlib.http.converter;

public class ResultErrorException extends RuntimeException {

    private int errCode = 0;
    private String errMsg;

    public ResultErrorException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.errMsg = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
