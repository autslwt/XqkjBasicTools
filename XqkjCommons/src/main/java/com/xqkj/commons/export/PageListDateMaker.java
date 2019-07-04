package com.xqkj.commons.export;

import com.xqkj.commons.export.model.PageInforModel;
import com.xqkj.commons.model.BasicPageQuery;

public interface PageListDateMaker<T,Q extends BasicPageQuery> {
	/**
	 * 不能返回null
	 * @author liwentao
	 * @param query 查询条件
	 * @return 分页数据
	 * @date 2017年11月13日
	 */
	 PageInforModel<T> createPageInforDate(Q query);
}
