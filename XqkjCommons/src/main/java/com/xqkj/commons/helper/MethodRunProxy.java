package com.xqkj.commons.helper;

import com.xqkj.commons.model.BasicContextVO;
import com.xqkj.commons.model.HandleResult;

/**
 * @author lwt<br>
 * @description 放法的静态代理类，可以提供前置处理方法，后置处理方法和异常处理方法<br>
 * @date 2019/5/19
 */
public class MethodRunProxy {


    /**
     *
     * @param param
     * @param targetMethod
     * @param aroundMethod
     * @param <T>
     * @param <P>
     * @return
     */
    public static <T,P extends BasicContextVO<T>> HandleResult<T> runMethod(P param,
                  TargetMethodRunFunction<T,P> targetMethod, MethodRunInteceptor<T,P> aroundMethod)
                  throws Exception{
        if(aroundMethod==null){
            return targetMethod.run(param);
        }
        if(aroundMethod instanceof MethodRunWithHandleExceptionInteceptor){
            MethodRunWithHandleExceptionInteceptor withHandleExAroundMethod=(MethodRunWithHandleExceptionInteceptor)aroundMethod;
            try{
                return runMethodWithAround(param,targetMethod,aroundMethod);
            }catch (Exception ex){
                return withHandleExAroundMethod.onException(ex,param);
            }
        }
        return runMethodWithAround(param,targetMethod,aroundMethod);
    }

    private static <T,P extends BasicContextVO<T>> HandleResult<T> runMethodWithAround(P param,
                   TargetMethodRunFunction<T,P> targetMethod, MethodRunInteceptor<T,P> aroundMethod)
                   throws Exception{
        HandleResult<T> beforeResult=aroundMethod.before(param);
        setLastHanleResult(param,beforeResult);
        if(beforeResult!=null && beforeResult.isSuccess()){
            HandleResult<T> runResult=targetMethod.run(param);
            setLastHanleResult(param,runResult);
        }
        HandleResult<T> afterResult=aroundMethod.after(param);
        setLastHanleResult(param,afterResult);
        return afterResult;
    }

    private static <T,P extends BasicContextVO<T>> void setLastHanleResult(P contextVO,HandleResult<T> lastResult){
        if(contextVO!=null){
            contextVO.setLastHandleResult(lastResult);
        }
    }
}
