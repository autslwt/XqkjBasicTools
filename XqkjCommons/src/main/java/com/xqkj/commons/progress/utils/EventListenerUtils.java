package com.xqkj.commons.progress.utils;

import com.alibaba.fastjson.JSON;
import com.xqkj.commons.constant.EventTypeContants;
import com.xqkj.commons.constant.RunInfoStatusEnum;
import com.xqkj.commons.event.*;
import com.xqkj.commons.export.model.*;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.progress.model.RunInforModel;
import org.apache.commons.lang3.StringUtils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 5:44 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class EventListenerUtils {

    public static RunInforModel resetRunStatus(RunInforModel runInforModel,MakeAndWriteAnnoExcelFileEvent event){
        int eventType=event.getEventType();
        HandleResult<FileMakeResult> retVal=(HandleResult<FileMakeResult>)event.getRetValue();
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        return resetFileCreateStatus(runInforModel,eventType,retVal,excelWriteExtInfoVO);
    }

    public static RunInforModel resetRunStatus(RunInforModel runInforModel,MakeAndWriteExcelFileEvent event){
        int eventType=event.getEventType();
        HandleResult<FileMakeResult> retVal=(HandleResult<FileMakeResult>)event.getRetValue();
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        return resetFileCreateStatus(runInforModel,eventType,retVal,excelWriteExtInfoVO);
    }

    public static RunInforModel resetRunStatus(RunInforModel runInforModel,WriteDateToExcelEvent event){
        int eventType=event.getEventType();
        HandleResult<ExcelWriteResult> retVal=(HandleResult<ExcelWriteResult>)event.getRetValue();
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        return resetExcelFileWriteStatus(runInforModel,eventType,retVal,excelWriteExtInfoVO);
    }

    public static RunInforModel resetRunStatus(RunInforModel runInforModel,WriteDateToRowEvent event){
        int eventType=event.getEventType();
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        String dec=RunInfoStatusEnum.FILE_MAKE_RUNNING.getDesc();
        if(excelWriteExtInfoVO.getHasHandledAmount()!=null){
            dec="正在倒出第 "+excelWriteExtInfoVO.getHasHandledAmount()+" 条;";
            if(excelWriteExtInfoVO.getDateSumAmount()!=null){
                dec+="共 "+excelWriteExtInfoVO.getDateSumAmount()+" 条;";
            }
        }
        return resetHeadAndRowWriteStatus(runInforModel,eventType,excelWriteExtInfoVO,dec);
    }

    public static RunInforModel resetRunStatus(RunInforModel runInforModel,WriteHeaderDateToSheetEvent event){
        int eventType=event.getEventType();
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        String dec=RunInfoStatusEnum.FILE_MAKE_RUNNING.getDesc();
        return resetHeadAndRowWriteStatus(runInforModel,eventType,excelWriteExtInfoVO,dec);
    }

    public static RunInforModel resetRunStatus(RunInforModel runInforModel,BasicFileUploadEvent event){
        int eventType=event.getEventType();
        FileUpLoadExtInfoVO fileUpLoadExtInfoVO= event.getFileUpLoadExtInfoVO();
        Object retVal=event.getRetValue();
        return resetFileUploadStatus(runInforModel,eventType,fileUpLoadExtInfoVO,retVal);
    }


    private static RunInforModel resetFileCreateStatus(RunInforModel runInforModel,int eventType,
                                                       HandleResult<FileMakeResult> retVal,
                                                       ExcelWriteExtInfoVO excelWriteExtInfoVO){

        if(StringUtils.isNotBlank(excelWriteExtInfoVO.getUid())){
            runInforModel=(runInforModel==null?new RunInforModel():runInforModel);
            runInforModel.setKey(excelWriteExtInfoVO.getUid());
            runInforModel.setType(excelWriteExtInfoVO.getExportType());
            runInforModel.setParentType(excelWriteExtInfoVO.getParentType());
            if(runInforModel.getShowName()!=null){
                runInforModel.setShowName(excelWriteExtInfoVO.getFileAlisName());
            }
            if(eventType== EventTypeContants.TYPE_BEFORE_RUN){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_START.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_START.getDesc());
            }else if(eventType== EventTypeContants.TYPE_AFTER_RUN){
                if(retVal==null || !retVal.isSuccess() || retVal.getEntity().getFile()==null){
                    runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_ERROR.getCode());
                    String errmsg=retVal!=null?":"+retVal.getMsg():"";
                    runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_ERROR.getDesc()+errmsg);
                }else{
                    runInforModel.setFilePath(retVal.getEntity().getFile().getAbsolutePath());
                    runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_OVER.getCode());
                    runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_OVER.getDesc());
                }

            }else if(eventType== EventTypeContants.TYPE_RUN_EXCEPTION){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_ERROR.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_ERROR.getDesc());
            }
            System.out.println(JSON.toJSONString(runInforModel));
        }
        return runInforModel;
    }

    private static RunInforModel resetExcelFileWriteStatus(RunInforModel runInforModel,int eventType,
                                                       HandleResult<ExcelWriteResult> retVal,
                                                       ExcelWriteExtInfoVO excelWriteExtInfoVO){

        if(StringUtils.isNotBlank(excelWriteExtInfoVO.getUid())){
            runInforModel=(runInforModel==null?new RunInforModel():runInforModel);
            runInforModel.setKey(excelWriteExtInfoVO.getUid());
            runInforModel.setType(excelWriteExtInfoVO.getExportType());
            runInforModel.setParentType(excelWriteExtInfoVO.getParentType());
            if(runInforModel.getShowName()!=null){
                runInforModel.setShowName(excelWriteExtInfoVO.getFileAlisName());
            }
            if(eventType== EventTypeContants.TYPE_BEFORE_RUN){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_START.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_START.getDesc());
            }else if(eventType== EventTypeContants.TYPE_AFTER_RUN){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_OVER.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_OVER.getDesc());
            }else if(eventType== EventTypeContants.TYPE_RUN_EXCEPTION){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_ERROR.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_ERROR.getDesc());
            }
            System.out.println(JSON.toJSONString(runInforModel));
        }
        return runInforModel;
    }


    private static RunInforModel resetHeadAndRowWriteStatus(RunInforModel runInforModel,int eventType,ExcelWriteExtInfoVO excelWriteExtInfoVO,String desc){

        if(StringUtils.isNotBlank(excelWriteExtInfoVO.getUid())){
            runInforModel=(runInforModel==null?new RunInforModel():runInforModel);
            runInforModel.setKey(excelWriteExtInfoVO.getUid());
            runInforModel.setType(excelWriteExtInfoVO.getExportType());
            runInforModel.setParentType(excelWriteExtInfoVO.getParentType());
            if(runInforModel.getShowName()!=null){
                runInforModel.setShowName(excelWriteExtInfoVO.getFileAlisName());
            }
            if(eventType== EventTypeContants.TYPE_AFTER_RUN){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_RUNNING.getCode());
                runInforModel.setDesc(desc);
                System.out.println(JSON.toJSONString(runInforModel));
            }else if(eventType== EventTypeContants.TYPE_RUN_EXCEPTION){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_MAKE_ERROR.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_MAKE_ERROR.getDesc());
            }
        }
        return runInforModel;
    }

    private static RunInforModel resetFileUploadStatus(RunInforModel runInforModel,int eventType,
                                                       FileUpLoadExtInfoVO fileUpLoadExtInfoVO,Object retVal){

        if(StringUtils.isNotBlank(fileUpLoadExtInfoVO.getUid())){
            runInforModel=(runInforModel==null?new RunInforModel():runInforModel);
            runInforModel.setKey(fileUpLoadExtInfoVO.getUid());
            if(eventType== EventTypeContants.TYPE_BEFORE_RUN){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_UPLOAD_RUNNING.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_UPLOAD_RUNNING.getDesc());
            }else if(eventType== EventTypeContants.TYPE_AFTER_RUN){
                HandleResult<FileUpLoadResult> handleResult=(HandleResult<FileUpLoadResult>) retVal;
                if(handleResult==null || !handleResult.isSuccess() || handleResult.getEntity()==null){
                    runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_UPLOAD_ERROR.getCode());
                    String errMsg=(handleResult==null||StringUtils.isBlank(handleResult.getMsg()))
                            ?RunInfoStatusEnum.FILE_UPLOAD_ERROR.getDesc():handleResult.getMsg();
                    runInforModel.setDesc(errMsg);
                }else{
                    runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_UPLOAD_OVER.getCode());
                    runInforModel.setDesc(RunInfoStatusEnum.FILE_UPLOAD_OVER.getDesc());
                    runInforModel.setDownLoadUrl(handleResult.getEntity().getFileUrl());
                }
            }else if(eventType== EventTypeContants.TYPE_RUN_EXCEPTION){
                runInforModel.setCurrentStatus(RunInfoStatusEnum.FILE_UPLOAD_ERROR.getCode());
                runInforModel.setDesc(RunInfoStatusEnum.FILE_UPLOAD_ERROR.getDesc());
            }
            System.out.println(JSON.toJSONString(runInforModel));
        }
        return runInforModel;
    }
}
