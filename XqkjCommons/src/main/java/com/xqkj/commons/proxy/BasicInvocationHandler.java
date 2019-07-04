package com.xqkj.commons.proxy;

import com.xqkj.commons.intecepter.ProxyMethodIntecepterChainManager;
import com.xqkj.commons.intecepter.utils.ProxyMethodIntecepterChainRunUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/1 2:51 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicInvocationHandler implements InvocationHandler {

    protected Object target;

    public BasicInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //注入代理对象
        Method targetMethod = null;
        if (target != null && target instanceof ProxyObjAware) {
            //TODO 为了提高性能应该进行缓存 -- 弱引用(target)->map(method,targetMethod),target被销毁后设置map=null；
            //TODO 暂时先不提供目标方法的拦截了
            //targetMethod=target.getClass().getMethod(method.getName(),method.getParameterTypes());
            if (target instanceof ProxyObjAware) {
                ((ProxyObjAware) target).setProxyObj(proxy);
            }
        }
        //
        ProxyMethodIntecepterChainManager proxyMethodIntecepterChainManager =
                ProxyMethodIntecepterChainRunUtil.getProxyMethodIntecepterChainManagerIfNeed(method, targetMethod);
        if (proxyMethodIntecepterChainManager != null) {
            return ProxyMethodIntecepterChainRunUtil.runInIntecepterChains(proxyMethodIntecepterChainManager,
                    proxy, method, target, targetMethod, args);
        }
        //
        Object retValue = method.invoke(target, args);
        return retValue;
    }

}