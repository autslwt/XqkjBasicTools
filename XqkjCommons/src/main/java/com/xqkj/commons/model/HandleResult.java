package com.xqkj.commons.model;

import java.util.Map;

/**
 * @author lwt<br>
 * @description <br>
 * @date 2019/5/19
 */
public class HandleResult<T> {

    private int code;

    private boolean isSuccess;

    private String msg;

    private T entity;

    private Map<String,Object> extMap;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public static <T> HandleResult<T> success(){
        return success(null);
    }

    public static <T> HandleResult<T> success(T entity){
        HandleResult<T> handleResult=new HandleResult<>();
        handleResult.setSuccess(true);
        handleResult.setEntity(entity);
        return handleResult;
    }

    public static <T> HandleResult<T> failed(int code){
        return failed(code,null);
    }

    public static <T> HandleResult<T> failed(String msg){
        return failed(-1,msg);
    }

    public static <T> HandleResult<T> failed(int code,String msg){
        HandleResult<T> handleResult=new HandleResult<>();
        handleResult.setSuccess(false);
        handleResult.setCode(code);
        handleResult.setMsg(msg);
        return handleResult;
    }
}
