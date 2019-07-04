package com.xqkj.commons.event;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/1 3:35 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicEvent {
    protected transient Object source;

    private Integer eventType;

    public BasicEvent(){

    }

    public BasicEvent(Object source,Integer eventType){
        this.source=source;
        this.eventType=eventType;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }
}
