package com.xqkj.commons.progress.model;

import com.xqkj.commons.model.BasicPageQuery;

import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 6:11 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class RunInforQuery extends BasicPageQuery{

    private String type;

    private String parentType;

    private Map<String,Object> extMap;

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }
}
