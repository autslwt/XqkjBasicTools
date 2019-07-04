package com.xqkj.commons.basic;

import com.xqkj.commons.model.HandleResult;

import java.util.function.Function;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 1:43 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface FunctionRunExcutor<P,R> {

    HandleResult<R> excute(P param, Function<P,HandleResult<R>> runFunction);
}
