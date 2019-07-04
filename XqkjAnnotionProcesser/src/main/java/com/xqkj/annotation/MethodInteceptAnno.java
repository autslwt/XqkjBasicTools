package com.xqkj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInteceptAnno {

    /**
     * 切面管理器的名称
     * @return
     * @date: 2016年8月19日 上午10:24:48
     */
    String managerName() default "";

    /**
     * 参数名列表字符串，使用西文逗号分隔开
     * @return
     */
    String argsNames() default "";

    /**
     * 编译期间是否生成一个参数名称类，优先使用该值
     * @return
     */
    //String argsArrMapKey() default "";

    /**
     *
     * @return
     */
    String argsNamesHolderClass() default "";

}

