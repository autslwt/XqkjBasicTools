package com.xqkj.commons.proxy;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/2 12:21 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ProxyObjAware<T> {

    protected T proxyObj;

    public T getProxyObj(){
        return proxyObj;
    }

    public T getProxyObjOrThis(){
        return proxyObj==null?(T)this:proxyObj;
    }

    public void setProxyObj(T proxyObj){
        this.proxyObj=proxyObj;
    }
}
