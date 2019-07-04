package com.xqkj.commons.helper;

import com.xqkj.commons.model.BasicContextVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwt<br>
 * @description <br>
 * @date 2019/5/19
 */
public class MethodRunInteceptorsManager {

    private static Map<String,MethodRunInteceptor> methodRunInteceptorMap=new HashMap<>();

    public static <T,P extends BasicContextVO<T>> void registInteceptor(String key,
                      MethodRunInteceptor<T,P> methodRunInteceptor){
        methodRunInteceptorMap.put(key,methodRunInteceptor);
    }

    public static <T,P extends BasicContextVO<T>> MethodRunInteceptor<T,P> getInteceptor(String key){
        return methodRunInteceptorMap.get(key);
    }

}
