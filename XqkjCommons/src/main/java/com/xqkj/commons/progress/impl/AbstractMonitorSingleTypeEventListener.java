package com.xqkj.commons.progress.impl;

import com.xqkj.commons.event.BasicEvent;
import com.xqkj.commons.event.SingleTypeEventListenter;
import com.xqkj.commons.progress.RunInforDao;

import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 3:51 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public abstract class AbstractMonitorSingleTypeEventListener<T extends BasicEvent> implements SingleTypeEventListenter<T> {

    protected RunInforDao runInforDao;

    protected abstract Class getEventClass();

    public void init(RunInforDao runInforDao,Map<Class,SingleTypeEventListenter> eventListenerMap){
        this.runInforDao=runInforDao;
        eventListenerMap.put(getEventClass(),this);
    }
}
