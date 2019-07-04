package com.xqkj.commons.model;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 12:00 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ObjectHolder<T> {

    private T holeObj;

    public ObjectHolder(){
    }

    public ObjectHolder(T holeObj){
        this.holeObj=holeObj;
    }

    public T getHoleObj() {
        return holeObj;
    }

    public void setHoleObj(T holeObj) {
        this.holeObj = holeObj;
    }
}
