package com.xqkj.commons.progress;


import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.model.RunInforQuery;

import java.util.List;

public interface RunInforDao {
	/**
	 * 
	 * @author liwentao
	 * @param key
	 * @return
	 * @date 2017年11月13日
	 */
	RunInforModel getByKey(String key);

	/**
	 *
	 * @param query
	 * @return
	 */
	List<RunInforModel> queryList(RunInforQuery query);

	/**
	 * 
	 * @author liwentao
	 * @param model
	 * @date 2017年11月13日
	 */
	void saveRunInforModel(RunInforModel model);

	/**
	 * 
	 * @author liwentao
	 * @param model
	 * @return
	 * @date 2017年11月13日
	 */
	boolean updateRunInforModel(RunInforModel model);

	/**
	 * 
	 * @author liwentao
	 * @param key
	 * @return
	 * @date 2017年11月13日
	 */
	boolean deleteByKey(String key);

}
