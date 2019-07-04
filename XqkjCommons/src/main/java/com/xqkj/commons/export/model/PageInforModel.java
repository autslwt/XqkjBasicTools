package com.xqkj.commons.export.model;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author liwentao
 *
 * @param <T>
 */
public class PageInforModel<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int currentPage;
	/**
	 * 
	 */
	private int pageSize;
	/**
	 *
	 */
	private Integer sumCount;
	/**
	 * 
	 */
	private Integer sumPage;
	/**
	 *
	 */
	private boolean hasNextPage;
	/**
	 *
	 */
	private List<T> date;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		resetHasNextPage();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		resetSumPage();
	}

	public Integer getSumPage() {
		return sumPage;
	}

	public void setSumPage(Integer sumPage) {
		this.sumPage = sumPage;
		resetHasNextPage();
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public List<T> getDate() {
		return date;
	}

	public void setDate(List<T> date) {
		this.date = date;
	}

	public Integer getSumCount() {
		return sumCount;
	}

	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
		resetSumPage();
	}

	private void resetHasNextPage(){
		if(sumPage!=null){
			hasNextPage=currentPage<sumPage;
		}
	}

	private void resetSumPage(){
		if(pageSize!=0 && sumCount!=null){
			sumPage=sumCount/pageSize+(sumCount%pageSize==0?0:1);
			setSumPage(sumPage);
		}
	}

}
