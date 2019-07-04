package com.xqkj.commons.export.model;

import java.util.Map;
import java.util.UUID;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 12:52 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExportExcelRequestVO {

    private Object pramter;

    private String handerKey;

    private String fileAlisName;

    private String parentType;

    private String exportType;

    private String uid= UUID.randomUUID().toString().replace("-","");

    private boolean needUpload=true;

    private Map<String,Object> extMap;

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public Object getPramter() {
        return pramter;
    }

    public void setPramter(Object pramter) {
        this.pramter = pramter;
    }

    public String getHanderKey() {
        return handerKey;
    }

    public void setHanderKey(String handerKey) {
        this.handerKey = handerKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public boolean isNeedUpload() {
        return needUpload;
    }

    public void setNeedUpload(boolean needUpload) {
        this.needUpload = needUpload;
    }

    public String getFileAlisName() {
        return fileAlisName;
    }

    public void setFileAlisName(String fileAlisName) {
        this.fileAlisName = fileAlisName;
    }
}
