package com.xqkj.commons.export.handler;

import com.alibaba.fastjson.JSON;
import com.xqkj.commons.basic.FunctionRunExcutor;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.proxy.ExcelProxFactory;
import com.xqkj.commons.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 12:46 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class SimpleExcelExportHandlerDispatcher {

    private static Map<String, BasicDataExcelExportHandler> exportExcelMap = new ConcurrentHashMap<>();

    private static FunctionRunExcutor<ExportExcelRequestVO, String> defFunctionRunExcutor = ExcelProxFactory.getDefExcelExportAsyExcuter();

    private static Logger logger = LoggerFactory.getLogger(SimpleExcelExportHandlerDispatcher.class);


    public static HandleResult<String> dispatchToHandler(ExportExcelRequestVO request) {
        long start = System.currentTimeMillis();
        String handerKey = request.getHanderKey();
        Assert.isNotNull(handerKey, "request.handerKey不能为空");
        BasicDataExcelExportHandler basicHandler = exportExcelMap.get(handerKey);
        Assert.isNotNull(basicHandler, "没有找到 typeKey 对应的处理器！");
        HandleResult<String> handleResult = basicHandler.doHandler(request);
        logger.info("request={},handler={},result={},costTime={}",
                JSON.toJSONString(request), basicHandler.getClass().getName(), JSON.toJSONString(handleResult),
                System.currentTimeMillis() - start);
        return handleResult;
    }

    public static void addBasicHandler(BasicDataExcelExportHandler handler) {
        exportExcelMap.put(handler.handlerKey(), handler);
    }

    public static HandleResult<String> dispatchToHandlerInExcutor(ExportExcelRequestVO request) {
        return dispatchToHandlerInExcutor(request, null);
    }

    /**
     * @param request
     * @param functionRunExcutor 为了让执行可以经过检查后再以给定的方式执行，比如异步执行
     * @return
     */
    public static HandleResult<String> dispatchToHandlerInExcutor(ExportExcelRequestVO request,
                                                                  FunctionRunExcutor<ExportExcelRequestVO, String> functionRunExcutor) {
        FunctionRunExcutor<ExportExcelRequestVO, String> realExcutor = functionRunExcutor;
        if (realExcutor == null) {
            realExcutor = defFunctionRunExcutor;
        }
        return realExcutor.excute(request, param -> SimpleExcelExportHandlerDispatcher.dispatchToHandler(param));
    }

}
