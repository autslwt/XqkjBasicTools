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
public class SingletonDefSimpleExcelExportFacade extends DefSimpleExcelExportFacade{

    public static volatile SimpleExcelExportFacade instance;

    private SingletonDefSimpleExcelExportFacade(){

    }

    public static SimpleExcelExportFacade getInstance(){
        if(instance==null){
            synchronized (SingletonDefSimpleExcelExportFacade.class){
                instance=new SingletonDefSimpleExcelExportFacade();
            }
        }
        return instance;
    }

    public static List<RunInforModel> listRunInfoModel(RunInforQuery query){
        return getInstance().queryRunInfoList(query);
    }

    public static RunInforModel getRunInfoModelByKey(String key){
        return getInstance().getRunInfoByKey(key);
    }
}
