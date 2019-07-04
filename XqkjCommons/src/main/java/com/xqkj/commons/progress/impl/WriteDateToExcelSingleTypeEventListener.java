package com.xqkj.commons.progress.impl;

import com.xqkj.commons.event.WriteDateToExcelEvent;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.utils.EventListenerUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 2:51 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteDateToExcelSingleTypeEventListener
        extends AbstractMonitorSingleTypeEventListener<WriteDateToExcelEvent> {

    @Override
    public void handledEvent(WriteDateToExcelEvent event) {
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        if(StringUtils.isNotBlank(excelWriteExtInfoVO.getUid())){
            RunInforModel runInforModel=runInforDao.getByKey(excelWriteExtInfoVO.getUid());
            runInforModel = EventListenerUtils.resetRunStatus(runInforModel,event);
            runInforDao.saveRunInforModel(runInforModel);
        }
    }

    @Override
    protected Class getEventClass() {
        return WriteDateToExcelEvent.class;
    }
}
