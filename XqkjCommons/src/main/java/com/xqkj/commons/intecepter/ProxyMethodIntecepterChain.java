package com.xqkj.commons.intecepter;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 3:29 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ProxyMethodIntecepterChain {
    /**
     *
     * @param proxyMethodIntecepter
     */
    void setCurrentInteCepter(ProxyMethodIntecepter proxyMethodIntecepter);

    /**
     *
     * @return
     */
    ProxyMethodIntecepter getCurrentInteCepter();

    /**
     *
     * @param proxyMethodIntecepterChain
     */
    void setPreInteCepterChain(ProxyMethodIntecepterChain proxyMethodIntecepterChain);

    /**
     *
     * @return
     */
    ProxyMethodIntecepterChain getPreInteCepterChain();

    /**
     *
     * @param proxyMethodIntecepterChain
     */
    void setNextInteCepterChain(ProxyMethodIntecepterChain proxyMethodIntecepterChain);

    /**
     *
     * @return
     */
    ProxyMethodIntecepterChain getNextInteCepterChain();
}
