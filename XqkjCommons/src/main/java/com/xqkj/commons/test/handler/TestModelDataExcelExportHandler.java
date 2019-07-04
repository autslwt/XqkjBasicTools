package com.xqkj.commons.test.handler;

import com.xqkj.commons.export.PageListDateMaker;
import com.xqkj.commons.export.handler.BasicAnnoDataExcelExportHandler;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.export.model.FileUpLoadExtInfoVO;
import com.xqkj.commons.export.model.PageInforModel;
import com.xqkj.commons.test.model.TestModel;
import com.xqkj.commons.test.model.TestPageQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 7:23 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class TestModelDataExcelExportHandler extends BasicAnnoDataExcelExportHandler<TestModel,TestPageQuery> {

    public static final String keyName="TestModelDataExcelExportHandler";

    private static final int pageSize=20;

    public static String fileName="handler-test-";

    @Override
    protected TestPageQuery getPageQueryByRequest(ExportExcelRequestVO requestVO) {
        TestPageQuery query=new TestPageQuery();
        query.setPageSize(pageSize);
        query.setPageNo(1);
        return query;
    }

    @Override
    protected Class<TestModel> getDataTypeClass() {
        return TestModel.class;
    }

    @Override
    protected ExcelWriteExtInfoVO getExcelWriteExtInfoVO(ExportExcelRequestVO requestVO) {
        ExcelWriteExtInfoVO excelWriteExtInfoVO = new ExcelWriteExtInfoVO();
        excelWriteExtInfoVO.setZipAble(false);
        excelWriteExtInfoVO.setExportType("test");
        return excelWriteExtInfoVO;
    }

    @Override
    protected FileUpLoadExtInfoVO getFileUpLoadExtInfoVO(ExportExcelRequestVO requestVO) {
        FileUpLoadExtInfoVO fileUpLoadExtInfoVO=new FileUpLoadExtInfoVO();
        fileUpLoadExtInfoVO.setUploadServiceUrl("http://sp.52shangou.com/sp/upload/new");
        fileUpLoadExtInfoVO.setResultUrlPath("key");
        return fileUpLoadExtInfoVO;
    }

    @Override
    protected PageListDateMaker<TestModel,TestPageQuery> getPageListMaker(ExportExcelRequestVO requestVO) {
        Map<String, Object> globalInfoMap = new HashMap<>();
        int sumCount = 223;
        globalInfoMap.put("currentCount", 0);
        return (PageListDateMaker) param -> {

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
        };
    }

    @Override
    public String handlerKey() {
        return keyName;
    }

//    @Override
//    protected FileUpLoader getCoustomerFileUpLoader(){
//        return (excelFile, showFileName, fileUpLoadExtInfoVO) -> {
//            System.out.println("doUploadFlie uid="+fileUpLoadExtInfoVO.getUid());
//            FileUpLoadResult fileUpLoadResult=new FileUpLoadResult();
//            fileUpLoadExtInfoVO.setUid(fileUpLoadExtInfoVO.getUid());
//            fileUpLoadExtInfoVO.setUploadServiceUrl("getCoustomerFileUpLoader/test.xlsx");
//            fileUpLoadResult.setFileUrl("downloadUrl/test.xlxs");
//            return HandleResult.success(fileUpLoadResult);
//        };
//    }

    @Override
    protected String getFilePath(ExportExcelRequestVO requestVO){
        return "/Users/lwt-mac/Documents/资料文档/exceltest";
    }

    @Override
    protected String getFileName(ExportExcelRequestVO requestVO){
        return fileName;
    }
}
