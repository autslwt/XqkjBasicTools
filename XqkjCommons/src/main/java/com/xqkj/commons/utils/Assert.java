package com.xqkj.commons.utils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 4:28 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class Assert {
    public static void isNotNull(Object obj,String msg){
        if(obj==null){
            throw new RuntimeException(msg);
        }
    }

    public static void isTrue(boolean testBoolean,String msg){
        if(!testBoolean){
            throw new RuntimeException(msg);
        }
    }
}
