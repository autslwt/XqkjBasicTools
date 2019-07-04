package com.xqkj.commons.intecepter.impl;

import com.xqkj.commons.intecepter.ProxyMethodIntecepter;
import com.xqkj.commons.intecepter.ProxyMethodIntecepterChain;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 3:47 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ProxyMethodIntecepterChainImpl implements ProxyMethodIntecepterChain {

    private ProxyMethodIntecepter proxyMethodIntecepter;

    private ProxyMethodIntecepterChain preInteCepterChain;

    private ProxyMethodIntecepterChain nextInteCepterChain;

    @Override
    public void setCurrentInteCepter(ProxyMethodIntecepter proxyMethodIntecepter) {
        this.proxyMethodIntecepter = proxyMethodIntecepter;
    }

    @Override
    public ProxyMethodIntecepter getCurrentInteCepter() {
        return proxyMethodIntecepter;
    }

    @Override
    public void setPreInteCepterChain(ProxyMethodIntecepterChain proxyMethodIntecepterChain) {
        this.preInteCepterChain= proxyMethodIntecepterChain;
    }

    @Override
    public ProxyMethodIntecepterChain getPreInteCepterChain() {
        return preInteCepterChain;
    }

    @Override
    public void setNextInteCepterChain(ProxyMethodIntecepterChain proxyMethodIntecepterChain) {
        this.nextInteCepterChain= proxyMethodIntecepterChain;
    }

    @Override
    public ProxyMethodIntecepterChain getNextInteCepterChain() {
        return nextInteCepterChain;
    }
}
