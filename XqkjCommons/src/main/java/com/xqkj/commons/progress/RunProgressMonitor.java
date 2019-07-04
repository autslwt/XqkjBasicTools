package com.xqkj.commons.progress;

import com.xqkj.commons.event.SingleTypeEventListenter;
import com.xqkj.commons.progress.model.RunInforModel;


/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/27 11:12 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface RunProgressMonitor {
    /**
     * 启动监控
     */
    void start();

    /**
     * 停止一次监控
     */
    void stop();

    /**
     * 停止所有监控
     */
    void stopAll();

    /**
     *
     * @param runInforDao
     */
    void setRunInforDao(RunInforDao runInforDao);

    /**
     *
     * @return
     */
    RunInforDao getRunInforDao();

    /**
     *
     * @param key
     * @return
     */
    RunInforModel getRunInforByKey(String key);


    /**
     *
     * @return
     */
    public int getDetLogCount();

    /**
     *
     * @param detLogCount
     */
    public void setDetLogCount(int detLogCount);
}
