package com.xqkj.commons.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/12 10:35 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class IntecepterArgsVO {
    /**
     *
     */
    private Object target;
    /**
     *
     */
    private Object proxy;
    /**
     *
     */
    private Method method;
    /**
     *
     */
    private Method targetMethod;
    /**
     *
     */
    private Object[] args;
    /**
     *
     */
    private Map<String,Object> argMap=new HashMap<>();

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Map<String, Object> getArgMap() {
        return argMap;
    }

    public void setArgMap(Map<String, Object> argMap) {
        this.argMap = argMap;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }
}
