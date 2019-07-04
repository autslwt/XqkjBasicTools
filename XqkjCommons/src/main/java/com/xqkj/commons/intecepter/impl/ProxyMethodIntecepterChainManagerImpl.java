package com.xqkj.commons.intecepter.impl;

import com.xqkj.commons.intecepter.ProxyMethodIntecepter;
import com.xqkj.commons.intecepter.ProxyMethodIntecepterChain;
import com.xqkj.commons.intecepter.ProxyMethodIntecepterChainManager;
import com.xqkj.commons.intecepter.ProxyMethodWithCatchExceptionInteCeptor;
import com.xqkj.commons.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 3:49 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ProxyMethodIntecepterChainManagerImpl implements ProxyMethodIntecepterChainManager {

    private boolean hasExceptionIntercepter=false;

    private int intecepterChainLength=0;

    private ProxyMethodIntecepterChain header=new ProxyMethodIntecepterChainImpl();

    private ProxyMethodIntecepterChain tail=new ProxyMethodIntecepterChainImpl();

    private Map<String,Boolean> addedFlagMap=new ConcurrentHashMap<>();

    public ProxyMethodIntecepterChainManagerImpl(){
        header.setNextInteCepterChain(tail);
        tail.setPreInteCepterChain(header);
    }

    @Override
    public void addFirst(ProxyMethodIntecepter proxyMethodIntecepter) {
        Assert.isNotNull(proxyMethodIntecepter,"proxyMethodIntecepter 不能为空");
        if(testIsAdded(proxyMethodIntecepter)){
            return;
        }
        ProxyMethodIntecepterChain nextChain = header.getNextInteCepterChain();
        ProxyMethodIntecepterChain newChain=new ProxyMethodIntecepterChainImpl();
        newChain.setCurrentInteCepter(proxyMethodIntecepter);
        newChain.setNextInteCepterChain(nextChain);
        newChain.setPreInteCepterChain(header);
        //
        nextChain.setPreInteCepterChain(newChain);
        header.setNextInteCepterChain(newChain);
        //
        afterAddSuccess(proxyMethodIntecepter);
    }

    @Override
    public void addLast(ProxyMethodIntecepter proxyMethodIntecepter) {
        Assert.isNotNull(proxyMethodIntecepter,"proxyMethodIntecepter 不能为空");
        if(testIsAdded(proxyMethodIntecepter)){
            return;
        }
        ProxyMethodIntecepterChain preChain = tail.getPreInteCepterChain();
        ProxyMethodIntecepterChain newChain=new ProxyMethodIntecepterChainImpl();
        newChain.setCurrentInteCepter(proxyMethodIntecepter);
        newChain.setNextInteCepterChain(tail);
        newChain.setPreInteCepterChain(preChain);
        //
        preChain.setNextInteCepterChain(newChain);
        tail.setPreInteCepterChain(newChain);
        //
        afterAddSuccess(proxyMethodIntecepter);
    }

    @Override
    public void addBefore(ProxyMethodIntecepter proxyMethodIntecepter, Class<? extends ProxyMethodIntecepter> beforeClassType) {
        Assert.isNotNull(proxyMethodIntecepter,"proxyMethodIntecepter 不能为空");
        if(testIsAdded(proxyMethodIntecepter)){
            return;
        }
        ProxyMethodIntecepterChain currentChain=header.getNextInteCepterChain();
        ProxyMethodIntecepterChain newChain=new ProxyMethodIntecepterChainImpl();
        newChain.setCurrentInteCepter(proxyMethodIntecepter);
        while (currentChain!=tail){
            if(currentChain.getCurrentInteCepter().getClass().equals(beforeClassType)){
                newChain.setNextInteCepterChain(currentChain);
                newChain.setPreInteCepterChain(currentChain.getPreInteCepterChain());
                currentChain.getPreInteCepterChain().setNextInteCepterChain(newChain);
                //
                currentChain.setPreInteCepterChain(newChain);
                break;
            }
            currentChain=currentChain.getNextInteCepterChain();
        }
        if(currentChain==tail){
            addLast(proxyMethodIntecepter);
        }
        afterAddSuccess(proxyMethodIntecepter);
    }

    @Override
    public void addAfter(ProxyMethodIntecepter proxyMethodIntecepter, Class<? extends ProxyMethodIntecepter> afterClassType) {
        Assert.isNotNull(proxyMethodIntecepter,"proxyMethodIntecepter 不能为空");
        if(testIsAdded(proxyMethodIntecepter)){
            return;
        }
        ProxyMethodIntecepterChain currentChain=header.getNextInteCepterChain();
        ProxyMethodIntecepterChain newChain=new ProxyMethodIntecepterChainImpl();
        newChain.setCurrentInteCepter(proxyMethodIntecepter);
        while (currentChain!=tail){
            if(currentChain.getCurrentInteCepter().getClass().equals(afterClassType)){
                newChain.setPreInteCepterChain(currentChain);
                newChain.setNextInteCepterChain(currentChain.getNextInteCepterChain());
                currentChain.getNextInteCepterChain().setPreInteCepterChain(newChain);
                //
                currentChain.setNextInteCepterChain(newChain);
                break;
            }
            currentChain=currentChain.getNextInteCepterChain();
        }
        if(currentChain==tail){
            addLast(proxyMethodIntecepter);
        }
        afterAddSuccess(proxyMethodIntecepter);
    }

    @Override
    public ProxyMethodIntecepterChain getHeader() {
        return header;
    }

    @Override
    public ProxyMethodIntecepterChain getTail() {
        return tail;
    }

    @Override
    public boolean hasExceptionIntercepter() {
        return hasExceptionIntercepter;
    }

    @Override
    public int getIntecepterChainLength() {
        return intecepterChainLength;

    }

    private void afterAddSuccess(ProxyMethodIntecepter proxyMethodIntecepter){
        if(proxyMethodIntecepter instanceof ProxyMethodWithCatchExceptionInteCeptor){
            hasExceptionIntercepter=true;
        }
        intecepterChainLength++;
        //
        markIsAdded(proxyMethodIntecepter);
    }

    private boolean testIsAdded(ProxyMethodIntecepter proxyMethodIntecepter){
        if(addedFlagMap.get(proxyMethodIntecepter.getClass().toString())!=null){
            return true;
        }
        return false;
    }

    private void markIsAdded(ProxyMethodIntecepter proxyMethodIntecepter){
        addedFlagMap.put(proxyMethodIntecepter.getClass().toString(),true);
    }

}
