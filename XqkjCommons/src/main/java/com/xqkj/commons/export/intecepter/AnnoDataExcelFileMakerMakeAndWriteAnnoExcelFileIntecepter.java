package com.xqkj.commons.export.intecepter;

import com.xqkj.commons.constant.EventTypeContants;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.event.EventManager;
import com.xqkj.commons.event.MakeAndWriteAnnoExcelFileEvent;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.intecepter.impl.BasicMethodWithCatchExceptionInteCeptorImpl;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.commons.model.ObjectHolder;
import com.xqkj.commons.utils.Utils;
import com.xqkj.methodargs.AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile;

import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 11:09 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class AnnoDataExcelFileMakerMakeAndWriteAnnoExcelFileIntecepter
        extends BasicMethodWithCatchExceptionInteCeptorImpl {

    @Override
    public void afterException(ObjectHolder<Exception> exHolder, IntecepterArgsVO intecepterArgs){
        MakeAndWriteAnnoExcelFileEvent exceptionEvent=convertToEvent(intecepterArgs,EventTypeContants.TYPE_RUN_EXCEPTION);
        if(exceptionEvent!=null){
            EventManager.publicEvent(exceptionEvent);
        }
    }

    @Override
    public InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs) {
        MakeAndWriteAnnoExcelFileEvent beginEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_BEFORE_RUN);
        if(beginEvent!=null){
            EventManager.publicEvent(beginEvent);
        }
        return null;
    }

    @Override
    public InteceptReturnVO afterHandle(Object retValue, IntecepterArgsVO intecepterArgs) {
        MakeAndWriteAnnoExcelFileEvent afterEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_AFTER_RUN,retValue);
        if(afterEvent!=null){
            EventManager.publicEvent(afterEvent);
        }
        return null;
    }

    private MakeAndWriteAnnoExcelFileEvent convertToEvent(IntecepterArgsVO intecepterArgs, int eventType){
        return convertToEvent(intecepterArgs,eventType,null);
    }

    private MakeAndWriteAnnoExcelFileEvent convertToEvent(IntecepterArgsVO intecepterArgs,int eventType,Object retVal){
        Map<String,Object> argsMap = intecepterArgs.getArgMap();
        if(argsMap==null || argsMap.isEmpty()){
            return null;
        }
        String filePath= Utils.getAndCastMapValue(argsMap, AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile.filePath);
        String fileName= Utils.getAndCastMapValue(argsMap,AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile.fileName);
        String extName= Utils.getAndCastMapValue(argsMap,AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile.extName);
        ExcelWriteExtInfoVO excelWriteExtInfoVO=Utils.getAndCastMapValue(argsMap,AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile.excelWriteExtInfoVO);
        BasicPageQuery query=Utils.getAndCastMapValue(argsMap,AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile.query);
        Class dateType=Utils.getAndCastMapValue(argsMap,AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile.dateType);
        //
        MakeAndWriteAnnoExcelFileEvent makeEvent=new MakeAndWriteAnnoExcelFileEvent(this, eventType,
                filePath,fileName,extName,query,excelWriteExtInfoVO,dateType,retVal);
        return makeEvent;
    }

    @Override
    public String getManagerName(){
        return IntecepterManagerNames.AnnoDataExcelFileMaker_MakeAndWriteAnnoExcelFile;
    }
}
