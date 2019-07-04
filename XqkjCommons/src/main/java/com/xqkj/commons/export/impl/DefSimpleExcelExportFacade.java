package com.xqkj.commons.export.impl;

import com.xqkj.commons.basic.FunctionRunExcutor;
import com.xqkj.commons.export.SimpleExcelExportFacade;
import com.xqkj.commons.export.handler.SimpleExcelExportHandlerDispatcher;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.export.model.SimpleExcelExportConfigVO;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.progress.RunInforDao;
import com.xqkj.commons.progress.RunProgressMonitor;
import com.xqkj.commons.progress.impl.DefRedisRunInforDao;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.model.RunInforQuery;
import com.xqkj.commons.proxy.ExcelProxFactory;
import com.xqkj.commons.utils.Assert;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 单例
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 4:52 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefSimpleExcelExportFacade implements SimpleExcelExportFacade{

    private RunProgressMonitor runProgressMonitor;

    private FunctionRunExcutor<ExportExcelRequestVO,String> asyRunExcuter;

    private FunctionRunExcutor<ExportExcelRequestVO,String> synRunExcuter;

    private boolean isInit=false;

    private Lock lock=new ReentrantLock();

    public DefSimpleExcelExportFacade(){

    }

    @Override
    public void init(SimpleExcelExportConfigVO config) {
        if(isInit){
            return;
        }
        try{
            lock.lock();
            if(!isInit){
                runInit(config);
            }
            isInit=true;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public RunProgressMonitor getRunProgressMonitor() {
        return runProgressMonitor;
    }

    @Override
    public List<RunInforModel> queryRunInfoList(RunInforQuery query){
        RunInforDao runInforDao=runProgressMonitor.getRunInforDao();
        Assert.isNotNull(runInforDao,"runProgressMonitor.runInforDao不能为空");
        return runInforDao.queryList(query);
    }

    @Override
    public RunInforModel getRunInfoByKey(String key){
        RunInforDao runInforDao=runProgressMonitor.getRunInforDao();
        Assert.isNotNull(runInforDao,"runProgressMonitor.runInforDao不能为空");
        return runInforDao.getByKey(key);
    }

    @Override
    public HandleResult<String> asyExportExcel(ExportExcelRequestVO requestVO) {
        Assert.isTrue(isInit,"请先初始化");
        //是否异步执行，依赖于asyRunExcuter的执行方案
        return SimpleExcelExportHandlerDispatcher.dispatchToHandlerInExcutor(requestVO,asyRunExcuter);
    }

    @Override
    public HandleResult<String> synExportExcel(ExportExcelRequestVO requestVO) {
        Assert.isTrue(isInit,"请先初始化");
        //是同步执行，依赖于synRunExcuter的执行方案
        return SimpleExcelExportHandlerDispatcher.dispatchToHandlerInExcutor(requestVO,synRunExcuter);
    }

    private void runInit(SimpleExcelExportConfigVO config){
        if(config.isUseRunProgressMonitor()){
            //初始化进度监视器
            RunInforDao runInforDao=config.getCustomerRunInforDao();
            runProgressMonitor=config.getCustomerRunProgressMonitor();
            if(runProgressMonitor==null){
                runProgressMonitor= ExcelProxFactory.getDefExportExcelRunProgressMonitor();
                if(runInforDao==null){
                    Assert.isNotNull(config.getStringRedisTemplate(),"使用默认的runInforDao时，必须提供stringRedisTemplate");
                    DefRedisRunInforDao defRedisRunInforDao=new DefRedisRunInforDao();
                    defRedisRunInforDao.setStringRedisTemplate(config.getStringRedisTemplate());
                    runInforDao=defRedisRunInforDao;
                }
                runProgressMonitor.setRunInforDao(runInforDao);
                runProgressMonitor.setDetLogCount(config.getRunLogCount());
            }
            runProgressMonitor.start();
        }
        //初始化异步执行器
        if(StringUtils.isNotBlank(config.getCustomerAsyRunExcuterName())){
            asyRunExcuter=ExcelProxFactory.getRegistedObj(config.getCustomerAsyRunExcuterName());
        }
        if(asyRunExcuter==null){
            asyRunExcuter=ExcelProxFactory.getDefExcelExportAsyExcuter();
        }
        //初始化同步执行器
        if(StringUtils.isNotBlank(config.getCustomerSynRunExcuterName())){
            synRunExcuter=ExcelProxFactory.getRegistedObj(config.getCustomerSynRunExcuterName());
        }
        if(synRunExcuter==null){
            synRunExcuter=ExcelProxFactory.getDefExcelExportSynExcuter();
        }
    }
}
