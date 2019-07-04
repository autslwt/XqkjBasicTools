package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExportExcelRequestVO;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 4:50 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExportDataInitEvent extends BasicEvent{

    private ExportExcelRequestVO request;

    public ExportDataInitEvent(Object source,Integer eventType,ExportExcelRequestVO request){
        super(source,eventType);
        this.request=request;
    }


    public ExportExcelRequestVO getRequest() {
        return request;
    }

    public void setRequest(ExportExcelRequestVO request) {
        this.request = request;
    }
}
