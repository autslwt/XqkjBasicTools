package com.xqkj.commons.model;

/**
 * @author lwt<br>
 * @description <br>
 * @date 2019/5/19
 */
public class BasicContextVO<T> {

    private HandleResult<T> lastHandleResult;

    private Exception lastException;

    public HandleResult<T> getLastHandleResult() {
        return lastHandleResult;
    }

    public void setLastHandleResult(HandleResult<T> lastHandleResult) {
        this.lastHandleResult = lastHandleResult;
    }

    public Exception getLastException() {
        return lastException;
    }

    public void setLastException(Exception lastException) {
        this.lastException = lastException;
    }
}
