package com.xqkj.commons.proxy;

import java.lang.reflect.Proxy;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/1 3:05 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ProxFactory {

    public static <T> T getInstance(Class<T> interfaceClass,T target){
        try{
            // 1. 类加载器// 2. 代理需要实现的接口，可以有多个// 3. 方法调用的实际处理者
            return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                    new Class<?>[] {interfaceClass},
                    new BasicInvocationHandler(target));
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static <T> T getInstance(Class<T> interfaceClass,Class<? extends T> targetClass){
        try{
            // 1. 类加载器// 2. 代理需要实现的接口，可以有多个// 3. 方法调用的实际处理者
            return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                    new Class<?>[] {interfaceClass},
                    new BasicInvocationHandler(targetClass.newInstance()));
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
