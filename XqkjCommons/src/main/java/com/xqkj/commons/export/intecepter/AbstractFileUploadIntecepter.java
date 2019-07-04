package com.xqkj.commons.export.intecepter;

import com.xqkj.commons.constant.EventTypeContants;
import com.xqkj.commons.event.BasicFileUploadEvent;
import com.xqkj.commons.event.EventManager;
import com.xqkj.commons.export.model.FileUpLoadExtInfoVO;
import com.xqkj.commons.intecepter.impl.BasicMethodWithCatchExceptionInteCeptorImpl;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.commons.model.ObjectHolder;
import com.xqkj.commons.utils.Utils;
import com.xqkj.methodargs.FileUpLoader_doUploadFlie;

import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 7:56 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public abstract class AbstractFileUploadIntecepter extends BasicMethodWithCatchExceptionInteCeptorImpl {

    @Override
    public void afterException(ObjectHolder<Exception> exHolder, IntecepterArgsVO intecepterArgs){
        BasicFileUploadEvent exceptionEvent=convertToEvent(intecepterArgs, EventTypeContants.TYPE_RUN_EXCEPTION);
        if(exceptionEvent!=null){
            EventManager.publicEvent(exceptionEvent);
        }
    }

    @Override
    public InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs) {
        BasicFileUploadEvent beginEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_BEFORE_RUN);
        if(beginEvent!=null){
            EventManager.publicEvent(beginEvent);
        }
        return null;
    }

    @Override
    public InteceptReturnVO afterHandle(Object retValue, IntecepterArgsVO intecepterArgs) {
        BasicFileUploadEvent afterEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_AFTER_RUN,retValue);
        if(afterEvent!=null){
            EventManager.publicEvent(afterEvent);
        }
        return null;
    }

    private BasicFileUploadEvent convertToEvent(IntecepterArgsVO intecepterArgs, int eventType){
        return convertToEvent(intecepterArgs,eventType,null);
    }

    private BasicFileUploadEvent convertToEvent(IntecepterArgsVO intecepterArgs,int eventType,Object retVal){
        Map<String,Object> argsMap = intecepterArgs.getArgMap();
        if(argsMap==null || argsMap.isEmpty()){
            return null;
        }
        String showFileName= Utils.getAndCastMapValue(argsMap, FileUpLoader_doUploadFlie.showFileName);
        FileUpLoadExtInfoVO fileUpLoadExtInfoVO=Utils.getAndCastMapValue(argsMap,FileUpLoader_doUploadFlie.fileUpLoadExtInfoVO);
        //
        BasicFileUploadEvent writeEvent = new BasicFileUploadEvent(intecepterArgs.getTarget(), eventType,
                showFileName,fileUpLoadExtInfoVO,retVal);

        return writeEvent;
    }

}
