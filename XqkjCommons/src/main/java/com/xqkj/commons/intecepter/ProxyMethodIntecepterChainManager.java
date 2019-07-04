package com.xqkj.commons.intecepter;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 3:24 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ProxyMethodIntecepterChainManager {

    void addFirst(ProxyMethodIntecepter proxyMethodIntecepter);

    void addLast(ProxyMethodIntecepter proxyMethodIntecepter);

    void addBefore(ProxyMethodIntecepter proxyMethodIntecepter, Class<? extends ProxyMethodIntecepter> beforeClassType);

    void addAfter(ProxyMethodIntecepter proxyMethodIntecepter, Class<? extends ProxyMethodIntecepter> afterClassType);

    ProxyMethodIntecepterChain getHeader();

    ProxyMethodIntecepterChain getTail();

    boolean hasExceptionIntercepter();

    int getIntecepterChainLength();

}
