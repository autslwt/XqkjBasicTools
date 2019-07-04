package com.xqkj.commons.intecepter.impl;

import com.xqkj.commons.intecepter.BasicMethodIntecepter;
import com.xqkj.commons.intecepter.ProxyMethodWithCatchExceptionInteCeptor;
import com.xqkj.commons.intecepter.utils.ProxyMethodIntecepterChainManagerContainer;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 3:50 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public abstract class BasicMethodWithCatchExceptionInteCeptorImpl
        implements ProxyMethodWithCatchExceptionInteCeptor,BasicMethodIntecepter {

    @Override
    public void init(){
        ProxyMethodIntecepterChainManagerContainer.addIntecepterToManager(getManagerName(),this);
    }

}
