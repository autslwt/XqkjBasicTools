package com.xqkj.annotation.handler;

import com.xqkj.annotation.model.ExceuteResult;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：lwt-mac  haha    <br>
 * @ Date       ：2019/6/25 5:23 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface MethodInteceptAnnoHandler {

    public static final String arrArgName="arrArgName";
    public static final String classStrName="classStrName";
    public static final String argsArrMapKey="argsArrMapKey";


    /**
     * 处理注解
     * @param processingEnv
     * @param e
     * @return
     */
    ExceuteResult<String> exceute(ProcessingEnvironment processingEnv, Element e);

}
