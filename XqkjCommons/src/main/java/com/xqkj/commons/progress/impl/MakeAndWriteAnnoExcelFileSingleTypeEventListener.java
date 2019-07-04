package com.xqkj.commons.progress.impl;

import com.xqkj.commons.event.MakeAndWriteAnnoExcelFileEvent;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.utils.EventListenerUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 2:44 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class MakeAndWriteAnnoExcelFileSingleTypeEventListener
        extends AbstractMonitorSingleTypeEventListener<MakeAndWriteAnnoExcelFileEvent> {

    @Override
    public void handledEvent(MakeAndWriteAnnoExcelFileEvent event) {
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        if(StringUtils.isNotBlank(excelWriteExtInfoVO.getUid())){
            RunInforModel runInforModel=runInforDao.getByKey(excelWriteExtInfoVO.getUid());
            runInforModel = EventListenerUtils.resetRunStatus(runInforModel,event);
            runInforDao.saveRunInforModel(runInforModel);
        }
    }

    @Override
    protected Class getEventClass() {
        return MakeAndWriteAnnoExcelFileEvent.class;
    }
}
