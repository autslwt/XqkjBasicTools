package com.xqkj.commons.export;

import com.xqkj.commons.export.model.ExcelRowInfoVO;
import com.xqkj.commons.export.model.PageInforModel;
import com.xqkj.commons.model.BasicPageQuery;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 2:00 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ExcelRowDataMaker<Q extends BasicPageQuery> {
    /**
     *
     * @param query
     * @return
     */
    PageInforModel<ExcelRowInfoVO> queryData(Q query);
}
