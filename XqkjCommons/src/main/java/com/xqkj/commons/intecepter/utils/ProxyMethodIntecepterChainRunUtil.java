package com.xqkj.commons.intecepter.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xqkj.annotation.MethodInteceptAnno;
import com.xqkj.commons.helper.ArgNamesHodler;
import com.xqkj.commons.intecepter.ProxyMethodIntecepterChain;
import com.xqkj.commons.intecepter.ProxyMethodIntecepterChainManager;
import com.xqkj.commons.intecepter.ProxyMethodWithCatchExceptionInteCeptor;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.commons.model.ObjectHolder;
import com.xqkj.commons.utils.Assert;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 11:18 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ProxyMethodIntecepterChainRunUtil {

    private static Map<String, String[]> argNameArrMap;

    static {
        argNameArrMap = ArgNamesHodler.argNameArrMap;
    }

    /**
     *
     * @param method
     * @param targetMethod
     */
    public static ProxyMethodIntecepterChainManager getProxyMethodIntecepterChainManagerIfNeed(Method method,Method targetMethod) {
        ProxyMethodIntecepterChainManager proxyMethodIntecepterChainManager = tryGetProxyMethodIntecepterChainManager(method, targetMethod);
        if (proxyMethodIntecepterChainManager != null && proxyMethodIntecepterChainManager.getIntecepterChainLength() > 0) {
            return proxyMethodIntecepterChainManager;
        }
        return null;
    }

    /**
     *
     * @param proxyMethodIntecepterChainManager
     * @param proxy
     * @param method
     * @param target
     * @param targetMethod
     * @param args
     * @return
     * @throws Exception
     */
    public static Object runInIntecepterChains(ProxyMethodIntecepterChainManager proxyMethodIntecepterChainManager,
                                                Object proxy, Method method,
                                                Object target, Method targetMethod,
                                                Object[] args)throws Exception {
        IntecepterArgsVO intecepterArgsVO = convertToIntecepterArgsVO(proxy, method, target, targetMethod, args);
        if (!proxyMethodIntecepterChainManager.hasExceptionIntercepter()) {
            return runInNormIntercepterChain(proxyMethodIntecepterChainManager, intecepterArgsVO);
        }
        return runInExceptionCatchIntercepterChain(proxyMethodIntecepterChainManager, intecepterArgsVO);
    }

    private static IntecepterArgsVO convertToIntecepterArgsVO(Object proxy, Method method,
                                                              Object target, Method targetMethod,
                                                              Object[] args) throws Exception {
        IntecepterArgsVO intecepterArgsVO = new IntecepterArgsVO();
        intecepterArgsVO.setArgs(args);
        intecepterArgsVO.setMethod(method);
        intecepterArgsVO.setProxy(proxy);
        intecepterArgsVO.setTarget(target);
        //
        MethodInteceptAnno methodInteceptAnno = getMethodInteceptAnno(method, targetMethod);
        if (methodInteceptAnno != null) {
            String[] argNameArray = null;
            if (StringUtils.isNotBlank(methodInteceptAnno.argsNamesHolderClass())) {
                String className=methodInteceptAnno.argsNamesHolderClass();
                argNameArray = tryGetArgNameArray(className);
            } else if (StringUtils.isNotBlank(methodInteceptAnno.argsNames())) {
                argNameArray = methodInteceptAnno.argsNames().split(",");
            }
            if (argNameArray != null && argNameArray.length > 0) {
                int argNameArrayLength = argNameArray.length;
                int argsLength = args.length;
                Assert.isTrue(argNameArrayLength == argsLength, "切面注解声明的参数个数与实际个数不一致：" + method.getName());
                //
                for (int i = 0; i < argNameArrayLength; i++) {
                    intecepterArgsVO.getArgMap().put(argNameArray[i], args[i]);
                }
            }
        }

        //
        return intecepterArgsVO;
    }

    private static String[] tryGetArgNameArray(String className){
        String[] argNameArray = argNameArrMap.get(className);
        if(argNameArray==null){
            synchronized (ProxyMethodIntecepterChainRunUtil.class){
                argNameArray = argNameArrMap.get(className);
                if(argNameArray==null){
                    try {
                        Class.forName(className);
                    } catch (ClassNotFoundException e) {

                    }
                    argNameArray = argNameArrMap.get(className);
                    if(argNameArray==null){
                        argNameArray=new String[]{};
                        argNameArrMap.put(className,argNameArray);
                    }
                }
            }
        }
        return argNameArray;
    }

    /**
     * 获取切面逻辑管理器
     *
     * @param proxyMethod
     * @param targetMethod
     * @return
     * @throws NoSuchMethodException
     */
    private static ProxyMethodIntecepterChainManager tryGetProxyMethodIntecepterChainManager(Method proxyMethod,
                                                                                             Method targetMethod) {
        ProxyMethodIntecepterChainManager proxyMethodIntecepterChainManager = null;
        MethodInteceptAnno methodInteceptAnno = getMethodInteceptAnno(proxyMethod, targetMethod);
        if (methodInteceptAnno != null && StringUtils.isNotBlank(methodInteceptAnno.managerName())) {
            proxyMethodIntecepterChainManager = ProxyMethodIntecepterChainManagerContainer.getManager(methodInteceptAnno.managerName());
        }
        //
        return proxyMethodIntecepterChainManager;
    }

    private static MethodInteceptAnno getMethodInteceptAnno(Method proxyethod, Method targetMethod) {
        //优先解释接口拦截注解
        MethodInteceptAnno methodInteceptAnno = proxyethod.getAnnotation(MethodInteceptAnno.class);
        if (methodInteceptAnno == null && targetMethod != null) {
            //看看目标类有没有切面注解声明
            methodInteceptAnno = targetMethod.getAnnotation(MethodInteceptAnno.class);
        }
        return methodInteceptAnno;
    }

    /**
     * @param proxyMethodIntecepterChainManager
     * @param intecepterArgsVO
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static Object runInNormIntercepterChain(ProxyMethodIntecepterChainManager proxyMethodIntecepterChainManager,
                                                    IntecepterArgsVO intecepterArgsVO) throws InvocationTargetException, IllegalAccessException {
        ProxyMethodIntecepterChain header = proxyMethodIntecepterChainManager.getHeader();
        ProxyMethodIntecepterChain tail = proxyMethodIntecepterChainManager.getTail();
        return doRunInNormIntercepterChain(header, tail, intecepterArgsVO);

    }

    /**
     * @param header
     * @param tail
     * @param intecepterArgsVO
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static Object doRunInNormIntercepterChain(ProxyMethodIntecepterChain header, ProxyMethodIntecepterChain tail,
                                                      IntecepterArgsVO intecepterArgsVO) throws InvocationTargetException, IllegalAccessException {

        InteceptReturnVO preInteceptReturnVO = runPreHandles(header, tail, intecepterArgsVO);
        if (preInteceptReturnVO != null && !preInteceptReturnVO.isGoingOn()) {
            return preInteceptReturnVO.getNotGoingonReturnObj();
        }
        Object retVal = intecepterArgsVO.getMethod().invoke(intecepterArgsVO.getTarget(), intecepterArgsVO.getArgs());
        //
        InteceptReturnVO afterInteceptReturnVO = runAfterHandles(header, tail, retVal, intecepterArgsVO);
        if (afterInteceptReturnVO != null && !afterInteceptReturnVO.isGoingOn()) {
            return afterInteceptReturnVO.getNotGoingonReturnObj();
        }
        return retVal;

    }

    /**
     * @param proxyMethodIntecepterChainManager
     * @param intecepterArgsVO
     * @return
     */
    private static Object runInExceptionCatchIntercepterChain(ProxyMethodIntecepterChainManager proxyMethodIntecepterChainManager,
                                                              IntecepterArgsVO intecepterArgsVO) throws Exception{
        ProxyMethodIntecepterChain header = proxyMethodIntecepterChainManager.getHeader();
        ProxyMethodIntecepterChain tail = proxyMethodIntecepterChainManager.getTail();
        try {
            return doRunInNormIntercepterChain(header, tail, intecepterArgsVO);
        } catch (Exception ex) {
            ObjectHolder<Exception> exHolder=new ObjectHolder(ex);
            runExceptionHandles(header, tail, exHolder, intecepterArgsVO);
            if(exHolder.getHoleObj()!=null){
                throw exHolder.getHoleObj();
            }
            return null;
        }
    }

    /**
     * @param header
     * @param tail
     * @param intecepterArgsVO
     * @return
     */
    private static InteceptReturnVO runPreHandles(ProxyMethodIntecepterChain header, ProxyMethodIntecepterChain tail,
                                                  IntecepterArgsVO intecepterArgsVO) {
        ProxyMethodIntecepterChain currentChain = header.getNextInteCepterChain();
        while (currentChain != tail) {
            InteceptReturnVO inteceptReturnVO = currentChain.getCurrentInteCepter().preHandle(intecepterArgsVO);
            if (inteceptReturnVO != null && !inteceptReturnVO.isGoingOn()) {
                return inteceptReturnVO;
            }
            currentChain = currentChain.getNextInteCepterChain();
        }
        return null;
    }

    /**
     * @param header
     * @param tail
     * @param retVal
     * @param intecepterArgsVO
     */
    private static InteceptReturnVO runAfterHandles(ProxyMethodIntecepterChain header, ProxyMethodIntecepterChain tail,
                                                    Object retVal, IntecepterArgsVO intecepterArgsVO) {
        ProxyMethodIntecepterChain currentChain = tail.getPreInteCepterChain();
        while (currentChain != header) {
            InteceptReturnVO inteceptReturnVO = currentChain.getCurrentInteCepter().afterHandle(retVal, intecepterArgsVO);
            if (inteceptReturnVO != null && !inteceptReturnVO.isGoingOn()) {
                return inteceptReturnVO;
            }
            currentChain = currentChain.getPreInteCepterChain();
        }
        return null;
    }

    /**
     * @param header
     * @param tail
     * @param exHolder
     * @param intecepterArgsVO
     */
    private static void runExceptionHandles(ProxyMethodIntecepterChain header, ProxyMethodIntecepterChain tail,
                                            ObjectHolder<Exception> exHolder, IntecepterArgsVO intecepterArgsVO) {
        ProxyMethodIntecepterChain currentChain = header.getNextInteCepterChain();
        while (currentChain != tail) {
            if (currentChain.getCurrentInteCepter() instanceof ProxyMethodWithCatchExceptionInteCeptor) {
                ((ProxyMethodWithCatchExceptionInteCeptor) currentChain.getCurrentInteCepter()).afterException(exHolder, intecepterArgsVO);
            }
            currentChain = currentChain.getNextInteCepterChain();
        }
    }
}
