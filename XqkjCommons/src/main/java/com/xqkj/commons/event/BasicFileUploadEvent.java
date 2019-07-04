package com.xqkj.commons.event;

import com.xqkj.commons.export.model.FileUpLoadExtInfoVO;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 6:04 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicFileUploadEvent extends BasicEvent{

    private String showFileName;

    private FileUpLoadExtInfoVO fileUpLoadExtInfoVO;

    private Object retValue;

    public BasicFileUploadEvent(){

    }

    public BasicFileUploadEvent(Object source,Integer eventType,String showFileName,
                                FileUpLoadExtInfoVO fileUpLoadExtInfoVO,
                                Object retValue){
        super(source,eventType);
        this.showFileName=showFileName;
        this.fileUpLoadExtInfoVO=fileUpLoadExtInfoVO;
        this.retValue=retValue;
    }

    public String getShowFileName() {
        return showFileName;
    }

    public void setShowFileName(String showFileName) {
        this.showFileName = showFileName;
    }

    public FileUpLoadExtInfoVO getFileUpLoadExtInfoVO() {
        return fileUpLoadExtInfoVO;
    }

    public void setFileUpLoadExtInfoVO(FileUpLoadExtInfoVO fileUpLoadExtInfoVO) {
        this.fileUpLoadExtInfoVO = fileUpLoadExtInfoVO;
    }

    public Object getRetValue() {
        return retValue;
    }

    public void setRetValue(Object retValue) {
        this.retValue = retValue;
    }
}
