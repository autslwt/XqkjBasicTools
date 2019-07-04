package com.xqkj.commons.progress.impl;

import com.xqkj.commons.event.BasicFileUploadEvent;
import com.xqkj.commons.export.model.FileUpLoadExtInfoVO;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.utils.EventListenerUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 6:39 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicFileUploadEventListener extends AbstractMonitorSingleTypeEventListener<BasicFileUploadEvent>{
    @Override
    protected Class getEventClass() {
        return BasicFileUploadEvent.class;
    }

    @Override
    public void handledEvent(BasicFileUploadEvent event) {
        FileUpLoadExtInfoVO fileUpLoadExtInfoVO= event.getFileUpLoadExtInfoVO();
        if(fileUpLoadExtInfoVO!=null && StringUtils.isNotBlank(fileUpLoadExtInfoVO.getUid())){
            RunInforModel runInforModel=runInforDao.getByKey(fileUpLoadExtInfoVO.getUid());
            if(runInforModel==null){
                return;
            }
            runInforModel = EventListenerUtils.resetRunStatus(runInforModel,event);
            runInforDao.saveRunInforModel(runInforModel);
        }
    }
}
