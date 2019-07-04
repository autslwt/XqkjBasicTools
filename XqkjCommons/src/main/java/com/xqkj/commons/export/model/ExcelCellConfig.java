package com.xqkj.commons.export.model;

import java.io.Serializable;

public class ExcelCellConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cellCode;

	private String header;
	
	private String format;

	private int index;

	private int columWidth;

	private Object value;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getColumWidth() {
		return columWidth;
	}

	public void setColumWidth(int columWidth) {
		this.columWidth = columWidth;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCellCode() {
		return cellCode;
	}

	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}
}
