package com.xqkj.commons.export.model;

import com.xqkj.commons.basic.FunctionRunExcutor;
import com.xqkj.commons.progress.RunInforDao;
import com.xqkj.commons.progress.RunProgressMonitor;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 3:40 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class SimpleExcelExportConfigVO {
    /**
     * 是否启用进度监视器，默认启用
     */
    private boolean useRunProgressMonitor=true;
    /**
     * 使用默认的runInforDao实现则必填<br>
     * 否则需要指定runInforDao
     */
    private StringRedisTemplate stringRedisTemplate;
    /**
     * excel生成行过程中的监控频率--100表示每100行记录一次进度
     */
    private int runLogCount=100;


    /**
     * 替换默认的异步执行器--该异步执行器必须已经向ExcelProxFactory注册了
     */
    private String customerAsyRunExcuterName;
    /**
     * 替换默认的同步执行器--该同步执行器必须已经向ExcelProxFactory注册了
     */
    private String customerSynRunExcuterName;
    /**
     * 替换默认的进程持久化方案
     */
    private RunInforDao customerRunInforDao;
    /**
     * 内部不会帮忙拼装RunInforDao，请自行拼装好--一般情况使用默认的就可以了
     */
    private RunProgressMonitor customerRunProgressMonitor;

    /**
     *
     */
    public SimpleExcelExportConfigVO(){

    }

    public boolean isUseRunProgressMonitor() {
        return useRunProgressMonitor;
    }

    public void setUseRunProgressMonitor(boolean useRunProgressMonitor) {
        this.useRunProgressMonitor = useRunProgressMonitor;
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public RunInforDao getCustomerRunInforDao() {
        return customerRunInforDao;
    }

    public void setCustomerRunInforDao(RunInforDao customerRunInforDao) {
        this.customerRunInforDao = customerRunInforDao;
    }

    public RunProgressMonitor getCustomerRunProgressMonitor() {
        return customerRunProgressMonitor;
    }

    public void setCustomerRunProgressMonitor(RunProgressMonitor customerRunProgressMonitor) {
        this.customerRunProgressMonitor = customerRunProgressMonitor;
    }

    public String getCustomerAsyRunExcuterName() {
        return customerAsyRunExcuterName;
    }

    public void setCustomerAsyRunExcuterName(String customerAsyRunExcuterName) {
        this.customerAsyRunExcuterName = customerAsyRunExcuterName;
    }

    public String getCustomerSynRunExcuterName() {
        return customerSynRunExcuterName;
    }

    public void setCustomerSynRunExcuterName(String customerSynRunExcuterName) {
        this.customerSynRunExcuterName = customerSynRunExcuterName;
    }

    public int getRunLogCount() {
        return runLogCount;
    }

    public void setRunLogCount(int runLogCount) {
        this.runLogCount = runLogCount;
    }
}
