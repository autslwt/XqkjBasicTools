package com.xqkj.commons.progress.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author liwentao
 *
 */
public class RunInforModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 某个进度的唯一标示
	 */
	private String key;
	/**
	 *
	 */
	private String showName;
	/**
	 * 总共需要处理的数量
	 */
	private Integer sumCount;
	/**
	 * 当前已经处理的数量
	 */
	private Integer currentHandleCount;
	/**
	 *
	 */
	private String parentType;
	/**
	 *
	 */
	private String type;
	/**
	 * 执行状态
	 */
	private int currentStatus;
	/**
	 * 描述信息
	 */
	private String desc;
	/**
	 * 文件下载地址
	 */
	private String downLoadUrl;
	/**
	 * 本地保存地址
	 */
	private String filePath;

	private String remarks;

	private Map<String,Object> extMap;

	public Map<String, Object> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, Object> extMap) {
		this.extMap = extMap;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public Integer getSumCount() {
		return sumCount;
	}

	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
	}

	public Integer getCurrentHandleCount() {
		return currentHandleCount;
	}

	public void setCurrentHandleCount(Integer currentHandleCount) {
		this.currentHandleCount = currentHandleCount;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
