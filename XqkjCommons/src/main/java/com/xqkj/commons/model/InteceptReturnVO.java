package com.xqkj.commons.model;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 11:24 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class InteceptReturnVO {

    private boolean goingOn;

    private Object notGoingonReturnObj;

    public boolean isGoingOn() {
        return goingOn;
    }

    public void setGoingOn(boolean goingOn) {
        this.goingOn = goingOn;
    }

    public Object getNotGoingonReturnObj() {
        return notGoingonReturnObj;
    }

    public void setNotGoingonReturnObj(Object notGoingonReturnObj) {
        this.notGoingonReturnObj = notGoingonReturnObj;
    }
}
