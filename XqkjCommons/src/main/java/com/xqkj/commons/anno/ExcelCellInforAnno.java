package com.xqkj.commons.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCellInforAnno {

    /**
     * code码，用于特殊逻辑处理
     * @return String
     * @date: 2016年8月19日 上午10:24:48
     */
    String cellCode() default "";

    /**
     * 标题
     * @return
     * @date: 2016年8月19日 上午10:24:48
     */
    String header() default "";

    /**
     * 格式化模式
     * @author liwentao
     * @return
     *@date 2017年11月14日
     */
    String format() default "";

    /**
     * index
     * @return
     * @date: 2016年8月19日 上午10:24:58
     */
    int index() default 0;

    /**
     *
     * 列宽,约等于西文字符个数
     * @author liwentao
     * @return
     */
    int columWidth() default 10;
}
