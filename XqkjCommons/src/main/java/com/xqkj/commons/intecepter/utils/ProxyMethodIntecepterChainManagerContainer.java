package com.xqkj.commons.intecepter.utils;

import com.xqkj.commons.intecepter.ProxyMethodIntecepter;
import com.xqkj.commons.intecepter.ProxyMethodIntecepterChainManager;
import com.xqkj.commons.intecepter.impl.ProxyMethodIntecepterChainManagerImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 4:32 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ProxyMethodIntecepterChainManagerContainer {

    private static final Map<String,ProxyMethodIntecepterChainManager> managerMap=new ConcurrentHashMap<>();

    public static ProxyMethodIntecepterChainManager getManager(String name){
        return managerMap.get(name);
    }

    public static void addManager(String name,ProxyMethodIntecepterChainManager manager){
        managerMap.put(name,manager);
    }

    public static void addIntecepterToManager(String managerName,ProxyMethodIntecepter proxyMethodIntecepter){
        ProxyMethodIntecepterChainManager proxyMethodIntecepterChainManager =managerMap.get(managerName);
        if(proxyMethodIntecepterChainManager ==null){
            proxyMethodIntecepterChainManager =new ProxyMethodIntecepterChainManagerImpl();
            managerMap.put(managerName, proxyMethodIntecepterChainManager);
        }
        proxyMethodIntecepterChainManager.addLast(proxyMethodIntecepter);
    }

}
