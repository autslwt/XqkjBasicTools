package com.xqkj.commons.export.model;

import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 11:04 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class FileUpLoadExtInfoVO {

    private String uid;

    private String uploadServiceUrl;

    private String boundary;

    private String resultUrlPath;

    private String resultCodePath;

    private String resultMsgPath;

    private Map<String,Object> extMap;

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUploadServiceUrl() {
        return uploadServiceUrl;
    }

    public void setUploadServiceUrl(String uploadServiceUrl) {
        this.uploadServiceUrl = uploadServiceUrl;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public String getResultUrlPath() {
        return resultUrlPath;
    }

    public void setResultUrlPath(String resultUrlPath) {
        this.resultUrlPath = resultUrlPath;
    }

    public String getResultCodePath() {
        return resultCodePath;
    }

    public void setResultCodePath(String resultCodePath) {
        this.resultCodePath = resultCodePath;
    }

    public String getResultMsgPath() {
        return resultMsgPath;
    }

    public void setResultMsgPath(String resultMsgPath) {
        this.resultMsgPath = resultMsgPath;
    }
}
