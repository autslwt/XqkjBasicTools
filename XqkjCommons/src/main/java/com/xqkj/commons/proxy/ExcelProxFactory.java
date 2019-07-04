package com.xqkj.commons.proxy;

import com.xqkj.commons.basic.FunctionRunExcutor;
import com.xqkj.commons.export.*;
import com.xqkj.commons.export.impl.*;
import com.xqkj.commons.export.intecepter.*;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.progress.RunProgressMonitor;
import com.xqkj.commons.progress.impl.DefExportExcelRunProgressMonitor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/27 4:14 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelProxFactory {

    public static final String defExcelFileMakerName="defExcelFileMaker";

    public static final String defAnnoDataExcelFileMakerName="defAnnoDataExcelFileMaker";

    public static final String defAnnoDateExcelFileWriterName="defAnnoDateExcelFileWriter";

    public static final String defExcelHeaderRowWriterName="defExcelHeaderRowWriter";

    public static final String defExcelRowCellWriterName="defExcelRowCellWriter";

    public static final String defExcelExportAsyExcuterName="defExcelExportAsyExcuter";

    public static final String defExcelExportSynExcuterName="defExcelExportSynExcuter";

    public static final String defFileUploaderName="defFileUploader";

    public static final String defExportExcelRunProgressMonitorName="defExportExcelRunProgressMonitor";


    private static Map<String,Object> excelProxObjMap =new ConcurrentHashMap<>();

    private static Map<String,Object> excelOriObjMap=new ConcurrentHashMap<>();

    private static final boolean isInited=false;

    private static Lock registRefLock=new ReentrantLock();

    static {
        initDatas();
        initIntecepters();
    }

    /**
     * 每添加一个默认获取方法，在这里注册一下默认的key并且运行一些，以便能够使用getRegistedObj方法获取到他们
     */
    private static final void initDatas(){
        if(isInited){
            return;
        }
        //
        ExcelProxFactory.getDefExcelFileMaker();
        ExcelProxFactory.getDefAnnoDataExcelFileMaker();
        ExcelProxFactory.getDefExcelFileWriter();
        ExcelProxFactory.getDefAnnoDateExcelFileWriter();
        //
        ExcelProxFactory.getDefExcelHeaderRowWriter();
        ExcelProxFactory.getDefExcelRowCellWriter();
        //
        ExcelProxFactory.getDefExcelExportAsyExcuter();
        ExcelProxFactory.getDefExcelExportSynExcuter();
        //
        ExcelProxFactory.getDefFileUpLoader();
        //
        ExcelProxFactory.getDefExportExcelRunProgressMonitor();
    }

    /**
     * 因为代理对象才能够使用到拦截器，所以在工厂类的静态方法中初始化内置的拦截器。
     * 客户自定义的有客户自己注册
     */
    private static void initIntecepters(){
        new AnnoDataExcelFileMakerMakeAndWriteAnnoExcelFileIntecepter().init();
        new ExcelFileMakerMakeAndWriteExcelFileIntecepter().init();
        new ExcelFileWriterWriteBodyRowDataIntecepter().init();
        new ExcelFileWriterWriteBodyRowListDataIntecepter().init();
        new ExcelFileWriterWriteExcelDataIntecepter().init();
        new ExcelFileWriterWriteHeaderRowDataIntecepter().init();
        new ExcelFileWriterWriteRowCellDataIntecepter().init();
        new FileUpLoaderUploadFileIntecepter().init();
    }

    public static <T> T getRegistedObj(String registedName){
        return (T) excelProxObjMap.get(registedName);
    }

    public static ExcelFileMaker getDefExcelFileMaker(){
        ExcelFileMaker excelFileMaker = getDefProxyObject(defExcelFileMakerName,ExcelFileMaker.class,DefExcelFileMaker.class);
        excelFileMaker.setExcelFileWriter(getDefExcelFileWriter());
        return excelFileMaker;
    }

    public static AnnoDataExcelFileMaker getDefAnnoDataExcelFileMaker(){
        AnnoDataExcelFileMaker annoDataExcelFileMaker = getDefProxyObject(defAnnoDataExcelFileMakerName,
                AnnoDataExcelFileMaker.class,DefAnnoDataExcelFileMaker.class);
        annoDataExcelFileMaker.setAnnoDataExcelFileWriter(getDefAnnoDateExcelFileWriter());
        return annoDataExcelFileMaker;
    }

    public static AnnoDataExcelFileWriter getDefAnnoDateExcelFileWriter(){
        return getDefProxyObject(defAnnoDateExcelFileWriterName,AnnoDataExcelFileWriter.class,DefAnnoDataExcelFileWriter.class);
    }

    public static ExcelFileWriter getDefExcelFileWriter(){
        return getDefAnnoDateExcelFileWriter();
    }

    public static ExcelHeaderRowWriter getDefExcelHeaderRowWriter(){
        return getDefNormalObject(defExcelHeaderRowWriterName,ExcelHeaderRowWriter.class,DefExcelHeaderRowWriter.class);
    }

    public static ExcelRowCellWriter getDefExcelRowCellWriter(){
        return getDefNormalObject(defExcelRowCellWriterName,ExcelRowCellWriter.class,DefExcelRowCellWriter.class);
    }

    public static FunctionRunExcutor<ExportExcelRequestVO,String> getDefExcelExportAsyExcuter(){
        return getDefNormalObject(defExcelExportAsyExcuterName,FunctionRunExcutor.class,DefExcelExportFunctionAsyRunExcutor.class);
    }

    public static FunctionRunExcutor<ExportExcelRequestVO,String> getDefExcelExportSynExcuter(){
        return getDefNormalObject(defExcelExportSynExcuterName,FunctionRunExcutor.class,DefExcelExportFunctionSynRunExcutor.class);
    }

    public static FileUpLoader getDefFileUpLoader(){
        return getDefProxyObject(defFileUploaderName,FileUpLoader.class,DefFileUploader.class);
    }

    public static RunProgressMonitor getDefExportExcelRunProgressMonitor(){
        return getDefNormalObject(defExportExcelRunProgressMonitorName,RunProgressMonitor.class,DefExportExcelRunProgressMonitor.class);
    }

    private static <T> T getDefProxyObject(String defName,Class<T> interfaceClass,Class<? extends T> targetClass){
        T proxyObj=(T) excelProxObjMap.get(defName);
        if(proxyObj==null){
            try{
                proxyObj= registToProxObject(defName,interfaceClass,targetClass.newInstance());
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }
        return proxyObj;
    }

    private static <T> T getDefNormalObject(String defName,Class<T> interfaceClass,Class<? extends T> targetClass){
        T proxyObj=(T) excelProxObjMap.get(defName);
        if(proxyObj==null){
            try{
                proxyObj= registToNormalObject(defName,interfaceClass,targetClass.newInstance());
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }
        return proxyObj;
    }

    /**************************************/

    private static <T,P extends T> T registObject(String registName, Class<T> interfaceClass, P registObj,boolean isNormal){
        if(StringUtils.isBlank(registName)){
            throw new RuntimeException("被注册对象的名称不能为空");
        }
        if(registObj==null){
            throw new RuntimeException("被注册的对象不能为空不能为空");
        }
        Object mappedObj= excelProxObjMap.get(registName);
        Object orignalObj=excelOriObjMap.get(registName);
        if(mappedObj==null){
            try{
                registRefLock.lock();
                if(excelProxObjMap.get(registName)==null){
                    excelOriObjMap.put(registName,registObj);
                    if(!isNormal){
                        Object proxObj=ProxFactory.getInstance(interfaceClass,registObj);
                        excelProxObjMap.put(registName,proxObj);
                        mappedObj=proxObj;
                    }else{
                        excelProxObjMap.put(registName,registObj);
                        mappedObj=registObj;
                    }
                    orignalObj=registObj;
                }
            }finally {
                registRefLock.unlock();
            }
        }
        //
        if(registObj!=orignalObj){
            throw new RuntimeException("注册名称为"+registName+"的对象已经存在");
        }
        return (T)mappedObj;
    }

    public static <T,P extends T> T registToProxObject(String registName, Class<T> interfaceClass, P registObj){
        return registObject(registName,interfaceClass,registObj,false);
    }

    public static <T,P extends T> T registToNormalObject(String registName, Class<T> interfaceClass, P registObj){
        return registObject(registName,interfaceClass,registObj,true);
    }

    /**
     *
     * @param registName
     * @return
     */
    public static Object unregistObject(String registName){
        Object mappedObj= excelProxObjMap.get(registName);
        if(mappedObj!=null){
            try{
                registRefLock.lock();
                if(excelProxObjMap.get(registName)!=null){
                   mappedObj = excelProxObjMap.remove(registName);
                   excelOriObjMap.remove(registName);
                }
            }finally {
                registRefLock.unlock();
            }
        }
        return mappedObj;
    }
}
