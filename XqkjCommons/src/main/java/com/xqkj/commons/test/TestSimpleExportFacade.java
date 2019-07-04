package com.xqkj.commons.test;

import com.alibaba.fastjson.JSON;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.export.SimpleExcelExportFacade;
import com.xqkj.commons.export.impl.DefSimpleExcelExportFacade;
import com.xqkj.commons.export.impl.SingletonDefSimpleExcelExportFacade;
import com.xqkj.commons.export.model.ExcelRowCellInfoVO;
import com.xqkj.commons.export.model.ExcelRowInfoVO;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.export.model.SimpleExcelExportConfigVO;
import com.xqkj.commons.intecepter.ProxyMethodIntecepter;
import com.xqkj.commons.intecepter.utils.ProxyMethodIntecepterChainManagerContainer;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.commons.progress.RunInforDao;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.model.RunInforQuery;
import com.xqkj.commons.proxy.ExcelProxFactory;
import com.xqkj.commons.test.handler.TestModelDataExcelExportHandler;
import com.xqkj.commons.test.interceptor.ExcelFileWriterWriteBodyRowDataIntecepter;
import com.xqkj.commons.test.interceptor.ExcelFileWriterWriteHeaderRowDataIntecepter;
import com.xqkj.methodargs.ExcelFileWriter_writeBodyRowDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 7:22 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class TestSimpleExportFacade {
    public static void main(String[] args) {
        //
        ExcelProxFactory.loadSelf();
        //添加切面的方法-01--直接实现ProxyMethodIntecepter接口，然后显示添加
        ProxyMethodIntecepterChainManagerContainer.addIntecepterToManager(
                IntecepterManagerNames.ExcelFileWriter_WriteHeaderRowData,
                new ExcelFileWriterWriteHeaderRowDataIntecepter()
        );
        //添加切面的方法-02--继承基础类BasicMethodIntecepterImpl，然后调用init方法隐式添加
        new ExcelFileWriterWriteBodyRowDataIntecepter().init();
        //1.准备文件导出处理器--真正的文件导出动作执行者；不同的导出需求，声明不同的处理器。
        TestModelDataExcelExportHandler testModelDataExcelExportHandler=new TestModelDataExcelExportHandler();
        testModelDataExcelExportHandler.init();
        //2。获取导出请求的分发门面接口，他可以将请求分发到上面的处理器--分发规则为 使用请求的handerKey 查找 处理器对应的handerKey
        SimpleExcelExportFacade simpleExcelExportFacade= SingletonDefSimpleExcelExportFacade.getInstance();
        //SimpleExcelExportFacade simpleExcelExportFacade=new DefSimpleExcelExportFacade();
        //3。配置并初始化分发门面信息
        RunInforDao runInfoDao=new RunInfoDaoImpl();
        SimpleExcelExportConfigVO configVO=new SimpleExcelExportConfigVO();
        configVO.setCustomerRunInforDao(runInfoDao);
        simpleExcelExportFacade.init(configVO);
        SingletonDefSimpleExcelExportFacade.getInstance().getRunProgressMonitor();
        //4。开始处理请求
        for(int i=0;i<2;i++){
            TestModelDataExcelExportHandler.fileName+=""+i;
            //构建请求
            ExportExcelRequestVO requestVO=new ExportExcelRequestVO();
            requestVO.setExportType("test");
            requestVO.setHanderKey(TestModelDataExcelExportHandler.keyName);
            requestVO.setFileAlisName("文件名称-showName-"+i);
            //执行倒出
            HandleResult<String> handleResult=simpleExcelExportFacade.synExportExcel(requestVO);
            //HandleResult<String> handleResult=simpleExcelExportFacade.asyExportExcel(requestVO);
            System.out.println("TestSimpleExportFacade handleResult="+ JSON.toJSONString(handleResult));
        }
        //5。门面信息初始化完成后，可以在任何时刻查询导出信息
        RunInforQuery query=new RunInforQuery();
        query.setType("test");
        //simpleExcelExportFacade.queryRunInfoList(query);
        List<RunInforModel> modelList = SingletonDefSimpleExcelExportFacade.listRunInfoModel(query);
        System.out.println("modelList="+JSON.toJSONString(modelList));
        //
        System.out.println("TestSimpleExportFacade runOk");
    }
}
