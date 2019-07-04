package com.xqkj.commons.export.impl;

import com.xqkj.commons.basic.FunctionRunExcutor;
import com.xqkj.commons.constant.EventTypeContants;
import com.xqkj.commons.event.EventManager;
import com.xqkj.commons.event.ExportDataInitEvent;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.utils.Assert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * 默认的异步执行器
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 1:45 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefExcelExportFunctionAsyRunExcutor implements FunctionRunExcutor<ExportExcelRequestVO,String> {

    private static BlockingQueue<Runnable> runBlockingQueue=new ArrayBlockingQueue<>(20);

    private static ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(5,5,0, TimeUnit.MILLISECONDS,
            runBlockingQueue);

    private static Logger logger = LoggerFactory.getLogger(DefExcelExportFunctionAsyRunExcutor.class);


    /**
     * 该方法会返回一个uid，使用uid可以获取倒出进度信息--前提是启用了进度监控
     * runFunction
     * @param request
     * @param runFunction
     * @return
     */
    @Override
    public HandleResult<String> excute(ExportExcelRequestVO request,
                                       Function<ExportExcelRequestVO,HandleResult<String>> runFunction) {
        if(StringUtils.isBlank(request.getUid())){
            request.setUid(UUID.randomUUID().toString().replace("-",""));
        }
        Assert.isNotNull(request, "request 不能为空");
        try{
            threadPoolExecutor.execute(()->{
                if(request!=null){
                    runFunction.apply(request);
                }
            });
            //这里发送一个事件，进度监视器响应这个事件，记录初始化进度
            ExportDataInitEvent exportDataInitEvent=new ExportDataInitEvent(this,
                    EventTypeContants.TYPE_RUN_DATA_INIT, request);
            EventManager.publicEvent(exportDataInitEvent);
        }catch (RejectedExecutionException ex){
            //
            return HandleResult.failed("已经达到了最大运行数量");
        }
        return HandleResult.success(request.getUid());
    }


}
