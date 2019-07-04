package com.xqkj.commons.progress.impl;

import com.xqkj.commons.event.WriteDateToRowEvent;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.utils.EventListenerUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/28 3:16 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteDateToRowSingleTypeEventListener
        extends AbstractMonitorSingleTypeEventListener<WriteDateToRowEvent> {

    private int detLogCount=100;

    @Override
    public void handledEvent(WriteDateToRowEvent event) {
        if(detLogCount==0){
            return;
        }
        ExcelWriteExtInfoVO excelWriteExtInfoVO= event.getExcelWriteExtInfoVO();
        Integer hasHanded=excelWriteExtInfoVO.getHasHandledAmount();
        if(StringUtils.isNotBlank(excelWriteExtInfoVO.getUid()) && hasHanded!=null
                && (hasHanded%detLogCount==0 || hasHanded.equals(excelWriteExtInfoVO.getDateSumAmount()))){
            System.out.println("now handle:"+hasHanded);
            RunInforModel runInforModel=runInforDao.getByKey(excelWriteExtInfoVO.getUid());
            if(runInforModel==null){
                return;
            }
            runInforModel.setSumCount(excelWriteExtInfoVO.getDateSumAmount());
            runInforModel.setCurrentHandleCount(excelWriteExtInfoVO.getHasHandledAmount());
            runInforModel = EventListenerUtils.resetRunStatus(runInforModel,event);
            runInforDao.saveRunInforModel(runInforModel);
        }
    }

    @Override
    protected Class getEventClass() {
        return WriteDateToRowEvent.class;
    }

    public int getDetLogCount() {
        return detLogCount;
    }

    public void setDetLogCount(int detLogCount) {
        this.detLogCount = detLogCount;
    }
}
