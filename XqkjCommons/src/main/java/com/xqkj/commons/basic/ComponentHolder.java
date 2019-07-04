package com.xqkj.commons.basic;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 4:52 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ComponentHolder {

    <T> T getComponentByName(String componentName,Class<T> clazz);
}
