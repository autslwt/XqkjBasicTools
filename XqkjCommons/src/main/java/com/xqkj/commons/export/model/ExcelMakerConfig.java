package com.xqkj.commons.export.model;

import com.xqkj.commons.progress.RunInforDao;
import com.xqkj.commons.progress.model.RunInforModel;

import java.io.Serializable;

/**
 * @ Author     ：lwt-mac
 * @ Date       ：2019/4/11 5:41 PM
 * @ Description：
 * @ Modified By：
 * @Version: 1.000
 */
public class ExcelMakerConfig implements Serializable {

    private RunInforModel progress;

    private RunInforDao runInforDao;

    public ExcelMakerConfig(){

    }

    public RunInforModel getProgress() {
        return progress;
    }

    public void setProgress(RunInforModel progress) {
        this.progress = progress;
    }

    public RunInforDao getRunInforDao() {
        return runInforDao;
    }

    public void setRunInforDao(RunInforDao runInforDao) {
        this.runInforDao = runInforDao;
    }
}
