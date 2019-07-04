package com.xqkj.commons.export.impl;

import com.xqkj.commons.export.AnnoDataExcelFileWriter;
import com.xqkj.commons.export.utils.ExcelFileUtils;
import com.xqkj.commons.export.PageListDateMaker;
import com.xqkj.commons.export.model.*;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.utils.ExcelInfoAnnoUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/26 11:10 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefAnnoDataExcelFileWriter extends DefExcelFileWriter implements AnnoDataExcelFileWriter {

    @Override
    public <T, Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> writeExcelAnnoData(OutputStream outputStream, Workbook workbook,
                                                      String sheetName,
                                                      Q query, Class<T> dateType,
                                                      PageListDateMaker<T, Q> pageListDateMaker,
                                                      ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        ExcelHeaderInfoVO excelHeaderInfoVO = readExcelHeaderInfoVO(dateType);
        return getProxyObjOrThis().writeExcelData(outputStream, workbook, sheetName, query,
                excelHeaderInfoVO, (param) -> {
                    PageInforModel pageInforModel = pageListDateMaker.createPageInforDate(param);
                    return convertToExcelRowInfoVOPageModel(pageInforModel);
                }, excelWriteExtInfoVO);
    }

    @Override
    public <T, Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> createAndWriteExcelAnnoData(OutputStream outputStream, String excelExtName,
                                                               Q query, Class<T> dateType,
                                                               PageListDateMaker<T, Q> pageListDateMaker,
                                                               ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        Workbook workbook = ExcelFileUtils.createWorkbook(excelExtName);
        AnnoDataExcelFileWriter annoDataExcelFileWriterProxy = (AnnoDataExcelFileWriter) getProxyObjOrThis();
        return annoDataExcelFileWriterProxy.writeExcelAnnoData(outputStream, workbook, "sheet01", query,
                dateType, pageListDateMaker, excelWriteExtInfoVO);
    }


    protected ExcelHeaderInfoVO readExcelHeaderInfoVO(Class dateType) {
        List<ExcelCellConfig> annoInfoList = ExcelInfoAnnoUtils.readClassExcelCellInforAnno(dateType);
        if (CollectionUtils.isNotEmpty(annoInfoList)) {
            List<ExcelRowCellInfoVO> cellInfoVOList = new ArrayList<>();
            ExcelRowInfoVO headerRowInfo = new ExcelRowInfoVO();
            headerRowInfo.setCurrentRowIndex(0);
            headerRowInfo.setCellInfoVOList(cellInfoVOList);
            ExcelHeaderInfoVO excelHeaderInfoVO = new ExcelHeaderInfoVO();
            excelHeaderInfoVO.setHeaderRowInfo(headerRowInfo);
            //
            for (ExcelCellConfig excelCellConfig : annoInfoList) {
                ExcelRowCellInfoVO excelRowCellInfoVO = convertToExcelRowCellInfoVO(excelCellConfig, true);
                cellInfoVOList.add(excelRowCellInfoVO);
            }
            return excelHeaderInfoVO;
        }
        return null;
    }

    protected PageInforModel<ExcelRowInfoVO> convertToExcelRowInfoVOPageModel(PageInforModel datePageInfo) {
        if (datePageInfo == null) {
            return null;
        }
        PageInforModel<ExcelRowInfoVO> pageInforModel = new PageInforModel<>();
        pageInforModel.setPageSize(datePageInfo.getPageSize());
        pageInforModel.setCurrentPage(datePageInfo.getCurrentPage());
        pageInforModel.setSumCount(datePageInfo.getSumCount());
        pageInforModel.setSumPage(datePageInfo.getSumPage());
        //
        try {
            List dataList = datePageInfo.getDate();
            List<ExcelRowInfoVO> rowDataList = new ArrayList<>();
            pageInforModel.setDate(rowDataList);
            if (CollectionUtils.isNotEmpty(dataList)) {
                for (Object dataObj : dataList) {
                    ExcelRowInfoVO excelRowInfoVO = new ExcelRowInfoVO();
                    rowDataList.add(excelRowInfoVO);
                    List<ExcelRowCellInfoVO> cellInfoVOList = new ArrayList<>();
                    excelRowInfoVO.setCellInfoVOList(cellInfoVOList);
                    List<ExcelCellConfig> cellInfoList = ExcelInfoAnnoUtils.readDataExcelCellInforAnno(dataObj);
                    if (CollectionUtils.isNotEmpty(cellInfoList)) {
                        for (ExcelCellConfig excelCellConfig : cellInfoList) {
                            ExcelRowCellInfoVO excelRowCellInfoVO = convertToExcelRowCellInfoVO(excelCellConfig, false);
                            cellInfoVOList.add(excelRowCellInfoVO);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return pageInforModel;
    }

    private ExcelRowCellInfoVO convertToExcelRowCellInfoVO(ExcelCellConfig excelCellConfig,
                                                           boolean isHeaderData) {
        ExcelRowCellInfoVO excelRowCellInfoVO = new ExcelRowCellInfoVO();
        //
        excelRowCellInfoVO.setCellWidth(excelCellConfig.getColumWidth());
        excelRowCellInfoVO.setValueFormat(excelCellConfig.getFormat());
        excelRowCellInfoVO.setValue(excelCellConfig.getValue());
        excelRowCellInfoVO.setCellCode(excelCellConfig.getCellCode());
        if (isHeaderData) {
            excelRowCellInfoVO.setValue(excelCellConfig.getHeader());
        }
        return excelRowCellInfoVO;
    }
}
