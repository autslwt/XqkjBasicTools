package com.xqkj.commons.export;

import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.export.model.SimpleExcelExportConfigVO;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.progress.RunProgressMonitor;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.model.RunInforQuery;

import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 3:40 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface SimpleExcelExportFacade {

    /**
     *
     * @param config
     */
    void init(SimpleExcelExportConfigVO config);

    /**
     *
     * @param query
     * @return
     */
    List<RunInforModel> queryRunInfoList(RunInforQuery query);

    /**
     *
     * @param key
     * @return
     */
    RunInforModel getRunInfoByKey(String key);

    /**
     *
     * @return
     */
    RunProgressMonitor getRunProgressMonitor();

    /**
     * 异步倒出数据
     * @param requestVO
     * @return
     */
    HandleResult<String> asyExportExcel(ExportExcelRequestVO requestVO);

    /**
     * 同步倒出数据
     * @param requestVO
     * @return
     */
    HandleResult<String> synExportExcel(ExportExcelRequestVO requestVO);

}
