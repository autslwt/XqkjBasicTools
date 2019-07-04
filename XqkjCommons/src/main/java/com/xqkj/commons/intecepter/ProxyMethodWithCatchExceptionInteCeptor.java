package com.xqkj.commons.intecepter;

import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.commons.model.ObjectHolder;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 3:17 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ProxyMethodWithCatchExceptionInteCeptor extends ProxyMethodIntecepter {
    /**
     *
     * @param exHolder
     * @param intecepterArgs
     */
    void afterException(ObjectHolder<Exception> exHolder, IntecepterArgsVO intecepterArgs);
}
