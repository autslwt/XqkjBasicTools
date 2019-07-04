package com.xqkj.commons.event;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/1 3:39 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface SingleTypeEventListenter<T extends BasicEvent> {

    void handledEvent(T event);
}
