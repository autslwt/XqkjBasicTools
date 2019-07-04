package com.xqkj.annotation.model;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/27 11:05 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExceuteResult<T> {

    private boolean isSuccess;

    private String message;

    private int code;

    private T result;

    public ExceuteResult(){

    }

    public static <T> ExceuteResult<T> failureResult(int code,String message){
        ExceuteResult<T> exceuteResult=new ExceuteResult<>();
        exceuteResult.setSuccess(false);
        exceuteResult.setCode(code);
        exceuteResult.setMessage(message);
        return exceuteResult;
    }

    public static <T> ExceuteResult<T> failureResult(String message){
        return failureResult(-1,message);
    }

    public static <T> ExceuteResult<T> successResult(T result){
        ExceuteResult<T> exceuteResult=new ExceuteResult<>();
        exceuteResult.setSuccess(true);
        exceuteResult.setCode(1);
        exceuteResult.setResult(result);
        return exceuteResult;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
