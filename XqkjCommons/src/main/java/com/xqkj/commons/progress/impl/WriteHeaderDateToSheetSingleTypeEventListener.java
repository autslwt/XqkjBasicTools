package com.xqkj.commons.progress.impl;

import com.xqkj.commons.event.WriteHeaderDateToSheetEvent;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.utils.EventListenerUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 3:15 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteHeaderDateToSheetSingleTypeEventListener
        extends AbstractMonitorSingleTypeEventListener<WriteHeaderDateToSheetEvent> {

    @Override
    public void handledEvent(WriteHeaderDateToSheetEvent event) {
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        if(StringUtils.isNotBlank(excelWriteExtInfoVO.getUid())){
            RunInforModel runInforModel=runInforDao.getByKey(excelWriteExtInfoVO.getUid());
            if(runInforModel==null){
                return;
            }
            runInforModel = EventListenerUtils.resetRunStatus(runInforModel,event);
            runInforDao.saveRunInforModel(runInforModel);
        }
    }

    @Override
    protected Class getEventClass() {
        return WriteHeaderDateToSheetEvent.class;
    }
}
