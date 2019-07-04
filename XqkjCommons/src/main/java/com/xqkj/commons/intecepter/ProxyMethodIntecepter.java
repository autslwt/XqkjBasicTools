package com.xqkj.commons.intecepter;

import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;

import java.lang.reflect.Method;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 3:16 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ProxyMethodIntecepter {

    /**
     * 返回 null 或者 InteceptReturnVO的goingOn==true才会继续后续的拦截器及目标方法的执行，<br>
     * 否则返回 InteceptReturnVO的notGoingonReturnObj值
     * @param intecepterArgs
     * @return
     */
    InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs);

    /**
     * 返回 null 或者 InteceptReturnVO的goingOn==true才会继续后续的拦截器的执行，<br>
     * 否则返回 InteceptReturnVO的notGoingonReturnObj值
     * @param retValue
     * @param intecepterArgs
     * * @return
     */
    InteceptReturnVO afterHandle(Object retValue, IntecepterArgsVO intecepterArgs);

}
