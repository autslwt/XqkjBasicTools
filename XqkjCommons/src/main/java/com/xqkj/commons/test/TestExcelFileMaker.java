package com.xqkj.commons.test;

import com.alibaba.fastjson.JSON;
import com.xqkj.commons.export.AnnoDataExcelFileMaker;
import com.xqkj.commons.export.PageListDateMaker;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.PageInforModel;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.progress.RunProgressMonitor;
import com.xqkj.commons.progress.impl.DefExportExcelRunProgressMonitor;
import com.xqkj.commons.progress.model.RunInforModel;
import com.xqkj.commons.progress.model.RunInforQuery;
import com.xqkj.commons.proxy.ExcelProxFactory;
import com.xqkj.commons.test.model.TestModel;
import com.xqkj.commons.test.model.TestPageQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/27 5:49 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class TestExcelFileMaker {

    public static void main(String[] args) {
//        DefWriteBodyRowDateIntecepter defWriteBodyRowDateIntecepter=new DefWriteBodyRowDateIntecepter();
//        DefWriteBodyRowDateIntecepter1 defWriteBodyRowDateIntecepter1=new DefWriteBodyRowDateIntecepter1();
//        ProxyMethodIntecepterChainManagerContainer.addIntecepterToManager(IntecepterManagerNames.ExcelFileWriter_WriteBodyRowData,
//                defWriteBodyRowDateIntecepter);
//        ProxyMethodIntecepterChainManagerContainer.addIntecepterToManager(IntecepterManagerNames.ExcelFileWriter_WriteBodyRowData,
//                defWriteBodyRowDateIntecepter1);
        //WriteDateToCellSingleTypeEventListenter writeDateToCellEventListenter=new WriteDateToCellSingleTypeEventListenter();
        //EventManager.registEventListenter(WriteDateToCellEvent.class, writeDateToCellEventListenter);
        RunProgressMonitor runProgressMonitor = new DefExportExcelRunProgressMonitor();
        runProgressMonitor.setRunInforDao(new RunInfoDaoImpl());
        runProgressMonitor.start();

        AnnoDataExcelFileMaker excelFileMaker = ExcelProxFactory.getDefAnnoDataExcelFileMaker();
        long startTime=System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            long loopStartTime=System.currentTimeMillis();
            doExport(excelFileMaker, runProgressMonitor,i);
            System.out.println("loop-"+i+"run time"+(System.currentTimeMillis()-loopStartTime));
        }
        System.out.println("sum run time"+(System.currentTimeMillis()-startTime));
        //
        RunInforQuery query=new RunInforQuery();
        query.setType("test");
        List<RunInforModel> modelList=runProgressMonitor.getRunInforDao().queryList(query);
        System.out.println("run model list="+JSON.toJSONString(modelList));
        runProgressMonitor.stop();
        //
        System.out.println("TestExcelFileMaker run over!");
    }

    private static void doExport(AnnoDataExcelFileMaker annoDataExcelFileMaker, RunProgressMonitor runProgressMonitor,
                                 int loopTime) {
        Map<String, Object> globalInfoMap = new HashMap<>();
        int sumCount = 223;
        int pageSize = 20;
        globalInfoMap.put("currentCount", 0);
        TestPageQuery basicPageQuery = new TestPageQuery();
        basicPageQuery.setPageNo(1);
        basicPageQuery.setPageSize(pageSize);
        ExcelWriteExtInfoVO excelWriteExtInfoVO = new ExcelWriteExtInfoVO();
        excelWriteExtInfoVO.setZipAble(false);
        excelWriteExtInfoVO.setExportType("test");
        //excelWriteExtInfoVO.setExcelMaxRow(64);
        annoDataExcelFileMaker.makeAndWriteAnnoExcelFile("/Users/lwt-mac/Documents/资料文档/exceltest",
                "测试excel-do-" + loopTime, null, basicPageQuery,
                TestModel.class, (PageListDateMaker) param -> {
                    int currentCount = (Integer) globalInfoMap.get("currentCount");
                    List<TestModel> modelList = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        TestModel testModel = new TestModel();
                        testModel.setId(currentCount);
                        testModel.setName("测试-" + currentCount);
                        testModel.setRemarks("remarker-" + currentCount);
                        modelList.add(testModel);
                        currentCount++;
                        if (currentCount > sumCount) {
                            break;
                        }
                    }
                    globalInfoMap.put("currentCount", currentCount);
                    PageInforModel<TestModel> pageInforModel = new PageInforModel<>();
                    pageInforModel.setPageSize(pageSize);
                    pageInforModel.setCurrentPage(param.getPageNo());
                    pageInforModel.setDate(modelList);
                    pageInforModel.setSumCount(sumCount);
                    //
                    return pageInforModel;
                }
                , excelWriteExtInfoVO);
        RunInforModel runInforModel=runProgressMonitor.getRunInforByKey(excelWriteExtInfoVO.getUid());
        System.out.println();
        System.out.println("runProgressMonitor.getRunInforByKey="+ JSON.toJSONString(runInforModel));
    }
}
