package com.xqkj.commons.test.listener;

import com.alibaba.fastjson.JSON;
import com.xqkj.commons.event.SingleTypeEventListenter;
import com.xqkj.commons.event.WriteDateToCellEvent;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/1 3:53 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class TestWriteDateToCellSingleTypeEventListenter implements SingleTypeEventListenter<WriteDateToCellEvent> {
    @Override
    public void handledEvent(WriteDateToCellEvent event) {
        System.out.println("TestWriteDateToCellSingleTypeEventListenter handledEvent:event="+ JSON.toJSONString(event));
    }
}
