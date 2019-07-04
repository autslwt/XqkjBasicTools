package com.xqkj.commons.event;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/1 3:36 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class EventManager {

    private static List<MultiTypeEventListenter> multiTypeEventListenterList=new ArrayList<>();
    private static Map<String,List<SingleTypeEventListenter>> listenterMap=new ConcurrentHashMap<>();
    private static Map<SingleTypeEventListenter,String> listenterListKeyMap=new ConcurrentHashMap<>();

    public static <T extends BasicEvent> void publicEvent(T event){
        List<SingleTypeEventListenter> listenterList=listenterMap.get(event.getClass().getName());
        if(CollectionUtils.isNotEmpty(listenterList)){
            listenterList.forEach(singleTypeEventListenter -> singleTypeEventListenter.handledEvent(event));
        }
        if(CollectionUtils.isNotEmpty(multiTypeEventListenterList)){
            multiTypeEventListenterList.forEach(multiTypeEventListenter -> {
                if(multiTypeEventListenter.support(event)){
                    multiTypeEventListenter.handledEvent(event);
                }
            });
        }
    }

    public static <T extends BasicEvent> void registEventListenter(Class<T> eventClass, SingleTypeEventListenter<T> singleTypeEventListenter){
        String key=eventClass.getName();
        List<SingleTypeEventListenter> listenterList=listenterMap.get(key);
        if(listenterList==null){
            listenterList=new ArrayList<>();
            listenterMap.put(eventClass.getName(), listenterList);
        }
        listenterList.add(singleTypeEventListenter);
        //
        listenterListKeyMap.put(singleTypeEventListenter,key);
    }

    public static <T extends MultiTypeEventListenter> void registMultiTypeEventListenter(T eventListenter){
        if(!multiTypeEventListenterList.contains(eventListenter)){
            multiTypeEventListenterList.add(eventListenter);
        }
    }

    public static <T extends BasicEvent> boolean removeEventListenter(SingleTypeEventListenter<T> singleTypeEventListenter){
        String key=listenterListKeyMap.get(singleTypeEventListenter);
        if(StringUtils.isBlank(key)){
            return true;
        }
        //
        List<SingleTypeEventListenter> listenterList=listenterMap.get(key);
        List<SingleTypeEventListenter> newListenterList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(listenterList)){
            listenterList.forEach(tmpListenter->{
                if(tmpListenter!= singleTypeEventListenter){
                    newListenterList.add(tmpListenter);
                }
            });
            listenterMap.put(key,newListenterList);
        }
        listenterListKeyMap.remove(singleTypeEventListenter);
        return true;
    }

    public static <T extends MultiTypeEventListenter> void removeMultiTypeEventListenter(T eventListenter){
        if(multiTypeEventListenterList.contains(eventListenter)){
            List<MultiTypeEventListenter> newListenterList=new ArrayList<>();
            multiTypeEventListenterList.forEach(tmpEventListenter->{
                if(tmpEventListenter!=eventListenter){
                    newListenterList.add(tmpEventListenter);
                }
            });
            multiTypeEventListenterList=newListenterList;
        }
    }
}
