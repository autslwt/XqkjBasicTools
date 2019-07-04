package com.xqkj.commons.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/26 11:15 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTitleInforAnno {

    /**
     *
     * @return String
     * @date: 2016年8月19日 上午10:24:48
     */
    String title() default "";

}
