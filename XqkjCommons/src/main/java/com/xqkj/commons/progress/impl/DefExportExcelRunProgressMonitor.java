package com.xqkj.commons.progress.impl;

import com.xqkj.commons.event.EventManager;
import com.xqkj.commons.event.SingleTypeEventListenter;
import com.xqkj.commons.progress.RunInforDao;
import com.xqkj.commons.progress.RunProgressMonitor;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.utils.Assert;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 11:43 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefExportExcelRunProgressMonitor implements RunProgressMonitor {

    private RunInforDao runInforDao;

    private int detLogCount=100;

    private AtomicInteger runTimes=new AtomicInteger(0);

    private Map<Class,SingleTypeEventListenter> eventListenerMap =new ConcurrentHashMap<>();

    private List<AbstractMonitorSingleTypeEventListener> innerEventListenerList=new ArrayList<>();

    private List<AbstractMonitorSingleTypeEventListener> customEventListenerList=new ArrayList<>();

    private Lock lock=new ReentrantLock();

    public DefExportExcelRunProgressMonitor(){

    }

    public DefExportExcelRunProgressMonitor(RunInforDao runInforDao){
        this.runInforDao=runInforDao;
    }

    @Override
    public void start() {
        Assert.isNotNull(runInforDao,"请先设置runInforDao");
        if(runTimes.get()==0){
            try{
                lock.lock();
                if(runTimes.get()==0){
                    initListeners();
                    runTimes.incrementAndGet();
                    return;
                }
            }finally {
                lock.unlock();
            }
        }
        runTimes.incrementAndGet();
    }

    @Override
    public void stop() {
        int currentRunTimes=runTimes.decrementAndGet();
        if(currentRunTimes==0){
            stopListeners();
        }
        if(currentRunTimes<0){
            runTimes.set(0);
        }
    }

    @Override
    public void stopAll() {
        try{
            lock.lock();
            stopListeners();
            runTimes.set(0);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void setRunInforDao(RunInforDao runInforDao) {
        this.runInforDao=runInforDao;
    }

    @Override
    public RunInforDao getRunInforDao() {
        return runInforDao;
    }

    @Override
    public RunInforModel getRunInforByKey(String key) {
        return runInforDao.getByKey(key);
    }

    public void addCustomEventListener(List<AbstractMonitorSingleTypeEventListener> evenetList) {
        if(CollectionUtils.isNotEmpty(evenetList)){
            customEventListenerList.addAll(evenetList);
        }
    }

    @Override
    public int getDetLogCount() {
        return detLogCount;
    }

    @Override
    public void setDetLogCount(int detLogCount) {
        this.detLogCount = detLogCount;
    }

    private void initListeners(){
        //
        ExportDataInitEventListener exportDataInitEventListener=new ExportDataInitEventListener();
        innerEventListenerList.add(exportDataInitEventListener);
        //这三个事件都是excel整体开始或者结束的事件
        MakeAndWriteAnnoExcelFileSingleTypeEventListener makeAndWriteAnnoExcelFileEventListener=new MakeAndWriteAnnoExcelFileSingleTypeEventListener();
        innerEventListenerList.add(makeAndWriteAnnoExcelFileEventListener);
        MakeAndWriteExcelFileSingleTypeEventListener makeAndWriteExcelFileEventListener =new MakeAndWriteExcelFileSingleTypeEventListener();
        innerEventListenerList.add(makeAndWriteExcelFileEventListener);
        WriteDateToExcelSingleTypeEventListener writeDateToExcelEventListener =new WriteDateToExcelSingleTypeEventListener();
        innerEventListenerList.add(writeDateToExcelEventListener);
        //这个事件可以记录excel头部开始写和结束
        WriteHeaderDateToSheetSingleTypeEventListener writeHeaderDateToSheetEventListener =new WriteHeaderDateToSheetSingleTypeEventListener();
        innerEventListenerList.add(writeHeaderDateToSheetEventListener);
        //写行数据
        WriteDateToRowSingleTypeEventListener writeDateToRowEventListener =new WriteDateToRowSingleTypeEventListener();
        writeDateToRowEventListener.setDetLogCount(detLogCount);
        innerEventListenerList.add(writeDateToRowEventListener);
        //上传监听
        BasicFileUploadEventListener basicFileUploadEventListener=new BasicFileUploadEventListener();
        innerEventListenerList.add(basicFileUploadEventListener);
        //初始化内置的事件监听
        innerEventListenerList.forEach(innerListener->innerListener.init(runInforDao,eventListenerMap));
        //初始化客户定制的事件监听
        if(CollectionUtils.isNotEmpty(customEventListenerList)){
            customEventListenerList.forEach(customListener->customListener.init(runInforDao,eventListenerMap));
        }
        //
        eventListenerMap.entrySet().forEach(item-> EventManager.registEventListenter(item.getKey(),item.getValue()));
    }

    private void stopListeners(){
        eventListenerMap.entrySet().forEach(item-> EventManager.removeEventListenter(item.getValue()));
    }


}
