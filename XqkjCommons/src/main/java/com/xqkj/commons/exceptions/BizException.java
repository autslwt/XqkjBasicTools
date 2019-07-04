package com.xqkj.commons.exceptions;

public class BizException extends RuntimeException{

    public BizException(){
        super();
    }

    public BizException(String msg){
        super(msg);
    }

    public BizException(Exception ex){
        super(ex);
    }
}
