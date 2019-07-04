package com.xqkj.commons.event;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 4:17 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface MultiTypeEventListenter {
    
    <T extends BasicEvent> void handledEvent(T event);

    <T extends BasicEvent> boolean support(T event);
}
