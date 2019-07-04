package com.xqkj.commons.progress.impl;

import com.xqkj.commons.constant.EventTypeContants;
import com.xqkj.commons.constant.RunInfoStatusEnum;
import com.xqkj.commons.event.ExportDataInitEvent;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.utils.EventListenerUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 4:54 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExportDataInitEventListener
        extends AbstractMonitorSingleTypeEventListener<ExportDataInitEvent>{
    @Override
    protected Class getEventClass() {
        return ExportDataInitEvent.class;
    }

    @Override
    public void handledEvent(ExportDataInitEvent event) {
        if(!event.getEventType().equals(EventTypeContants.TYPE_RUN_DATA_INIT)){
            return;
        }
        ExportExcelRequestVO requestVO= event.getRequest();
        if(StringUtils.isNotBlank(requestVO.getUid())){
            RunInforModel runInforModel=runInforDao.getByKey(requestVO.getUid());
            runInforModel=runInforModel==null?new RunInforModel():runInforModel;
            runInforModel.setType(requestVO.getExportType());
            runInforModel.setKey(requestVO.getUid());
            runInforModel.setCurrentStatus(RunInfoStatusEnum.DATE_INIT.getCode());
            runInforModel.setDesc(RunInfoStatusEnum.DATE_INIT.getDesc());
            runInforModel.setShowName(requestVO.getFileAlisName());
            runInforDao.saveRunInforModel(runInforModel);
        }
    }
}
