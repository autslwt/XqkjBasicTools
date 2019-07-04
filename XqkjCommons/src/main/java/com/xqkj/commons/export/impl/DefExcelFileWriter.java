package com.xqkj.commons.export.impl;

import com.xqkj.commons.basic.ComponentHolder;
import com.xqkj.commons.export.*;
import com.xqkj.commons.export.model.*;
import com.xqkj.commons.export.utils.ExcelFileUtils;
import com.xqkj.commons.helper.DefExcelWriterComponentHolder;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.proxy.ProxyObjAware;
import com.xqkj.commons.utils.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/26 11:20 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefExcelFileWriter extends ProxyObjAware<ExcelFileWriter> implements ExcelFileWriter {

    /**
     * 表头写组件和单元个写组件的持有类--默认的实现使用ExcelProxFactory作为生成工厂
     */
    private ComponentHolder componentHolder = new DefExcelWriterComponentHolder();

    @Override
    public <Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> writeExcelData(OutputStream outputStream,
                                                  Workbook workbook, String sheetName,
                                                  Q query, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                  ExcelRowDataMaker<Q> excelRowDataMaker,
                                                  ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        //
        ExcelWriteResult excelWriteResult = new ExcelWriteResult();
        //
        resetQueryInfo(query);
        Sheet sheet = workbook.createSheet(sheetName);
        HandleResult<ExcelWriteResult> headerWriteResult = getProxyObjOrThis().writeHeaderRowData(outputStream, sheet, excelHeaderInfoVO, excelWriteExtInfoVO);
        if (!headerWriteResult.isSuccess()) {
            return headerWriteResult;
        }
        Assert.isNotNull(headerWriteResult.getEntity(), "表头数据返回异常，写入结果不存在");
        int beginRowIndex = headerWriteResult.getEntity().getNextRowIndex();
        List<ExcelRowInfoVO> initRowDateList = excelWriteExtInfoVO.getInitRowDateList();
        while (true) {
            PageInforModel<ExcelRowInfoVO> pageInforModel = excelRowDataMaker.queryData(query);
            pageInforModel.setPageSize(query.getPageSize());
            pageInforModel.setCurrentPage(query.getPageNo());
            query.setPageNo(query.getPageNo() + 1);
            if (excelWriteExtInfoVO.getDateSumAmount() == null) {
                excelWriteExtInfoVO.setDateSumAmount(pageInforModel.getSumCount());
            }
            //
            List<ExcelRowInfoVO> bodyRowDateList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(initRowDateList)) {
                bodyRowDateList.addAll(initRowDateList);
                initRowDateList = null;
            }
            List<ExcelRowInfoVO> queryedDateList = pageInforModel.getDate();
            if (CollectionUtils.isNotEmpty(queryedDateList)) {
                bodyRowDateList.addAll(queryedDateList);
            }
            if (CollectionUtils.isEmpty(bodyRowDateList)) {
                break;
            }
            //
            ExcelBodyDateListInfoVO excelBodyDateListInfoVO = new ExcelBodyDateListInfoVO();
            excelBodyDateListInfoVO.setExcelRowInfoVOList(bodyRowDateList);
            excelBodyDateListInfoVO.setBeginRowIndex(beginRowIndex);
            //
            HandleResult<ExcelWriteResult> writeBodyListResult = getProxyObjOrThis().writeBodyRowListData(outputStream,
                    sheet, excelBodyDateListInfoVO, excelWriteExtInfoVO);
            if (!writeBodyListResult.isSuccess()) {
                return writeBodyListResult;
            }
            Assert.isNotNull(writeBodyListResult.getEntity(), "写入表体数据异常，写入结果不存在");
            beginRowIndex = writeBodyListResult.getEntity().getNextRowIndex();
            List<ExcelRowInfoVO> notWriteDateList = writeBodyListResult.getEntity().getNotWriteDateList();
            //
            excelWriteResult.setNextRowIndex(beginRowIndex);
            excelWriteResult.setNotWriteDateList(notWriteDateList);
            //
            if (CollectionUtils.isNotEmpty(notWriteDateList)) {
                break;
            }
            //
            if (!pageInforModel.isHasNextPage()) {
                break;
            }
        }
        //
        return HandleResult.success(excelWriteResult);
    }

    @Override
    public <Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> createAndWriteExcelData(OutputStream outputStream, String excelExtName,
                                                           Q query, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                           ExcelRowDataMaker<Q> excelRowDataMaker,
                                                           ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        Workbook workbook = ExcelFileUtils.createWorkbook(excelExtName);
        return getProxyObjOrThis().writeExcelData(outputStream, workbook, "sheet01", query,
                excelHeaderInfoVO, excelRowDataMaker, excelWriteExtInfoVO);
    }

    @Override
    public HandleResult<ExcelWriteResult> writeHeaderRowData(OutputStream outputStream,
                                                             Sheet sheet, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                             ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        //支持动态拆解表头的写实现
        ExcelHeaderRowWriter excelHeaderRowWriter = componentHolder.getComponentByName(
                excelWriteExtInfoVO.getExcelHeaderRowWriterName(), ExcelHeaderRowWriter.class);
        HandleResult<ExcelWriteResult> handleResult = excelHeaderRowWriter.doWriteHeaderRowDate(outputStream, sheet,
                excelHeaderInfoVO, excelWriteExtInfoVO);
        //
        return handleResult;
    }

    @Override
    public HandleResult<ExcelWriteResult> writeBodyRowListData(OutputStream outputStream,
                                                               Sheet sheet, ExcelBodyDateListInfoVO excelBodyDateListInfoVO,
                                                               ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        //
        ExcelWriteResult excelWriteResult = new ExcelWriteResult();
        int currentRowIndx = excelBodyDateListInfoVO.getBeginRowIndex();
        List<ExcelRowInfoVO> dataList = excelBodyDateListInfoVO.getExcelRowInfoVOList();
        Integer maxRow = excelWriteExtInfoVO.getExcelMaxRow() == null ? Integer.MAX_VALUE : excelWriteExtInfoVO.getExcelMaxRow();
        if (CollectionUtils.isNotEmpty(dataList)) {
            int index = 0;
            int length = dataList.size();
            for (; index < length; index++) {
                Row row = sheet.createRow(currentRowIndx++);
                HandleResult<ExcelWriteResult> writeRowResult = getProxyObjOrThis().writeBodyRowDate(outputStream, row, dataList.get(index), excelWriteExtInfoVO);
                if (!writeRowResult.isSuccess()) {
                    return writeRowResult;
                }
                if (currentRowIndx >= maxRow) {
                    break;
                }
            }
            if (index < length) {
                excelWriteResult.setNotWriteDateList(dataList.subList(index + 1, length));
            }
        }
        //
        excelWriteResult.setNextRowIndex(currentRowIndx);
        //
        return HandleResult.success(excelWriteResult);
    }

    @Override
    public HandleResult<ExcelWriteResult> writeBodyRowDate(OutputStream outputStream,
                                                           Row row, ExcelRowInfoVO excelRowInfoVO,
                                                           ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        //
        ExcelWriteResult excelWriteResult = new ExcelWriteResult();
        List<ExcelRowCellInfoVO> excelRowCellInfoVOList = excelRowInfoVO.getCellInfoVOList();
        if (CollectionUtils.isNotEmpty(excelRowCellInfoVOList)) {
            int cellIndex = 0;
            for (ExcelRowCellInfoVO excelRowCellInfoVO : excelRowCellInfoVOList) {
                Cell cell = row.createCell(cellIndex++);
                HandleResult<ExcelWriteResult> writeCellResult = getProxyObjOrThis().writeRowCellData(outputStream, cell, excelRowCellInfoVO, excelWriteExtInfoVO);
                if (!writeCellResult.isSuccess()) {
                    return writeCellResult;
                }
                List<CellRangeAddress> mergeInfoVOList = excelRowInfoVO.getMergeInfoVOList();
                if (CollectionUtils.isNotEmpty(mergeInfoVOList)) {
                    mergeInfoVOList.forEach(excelMergeInfoVO -> row.getSheet().addMergedRegion(excelMergeInfoVO));
                }
            }
            //
            if (excelWriteExtInfoVO != null) {
                excelWriteExtInfoVO.incrHasHandledAmount(1);
            }
        }
        //
        return HandleResult.success(excelWriteResult);
    }

    @Override
    public HandleResult<ExcelWriteResult> writeRowCellData(OutputStream outputStream,
                                                           Cell cell, ExcelRowCellInfoVO excelRowCellInfoVO,
                                                           ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        //支持动态拆解单元格写实现
        ExcelRowCellWriter excelRowCellWriter = componentHolder.getComponentByName(
                excelWriteExtInfoVO.getExcelRowCellWriterName(), ExcelRowCellWriter.class);
        HandleResult<ExcelWriteResult> handleResult = excelRowCellWriter.doWriteRowCellDate(outputStream, cell,
                excelRowCellInfoVO, excelWriteExtInfoVO);
        //
        return handleResult;
    }

    @Override
    public void setComponentHolder(ComponentHolder componentHolder) {
        this.componentHolder = componentHolder;
    }

    private void resetQueryInfo(BasicPageQuery query) {
        int pageNo = query.getPageNo() == null ? 1 : query.getPageNo();
        int pageSize = query.getPageSize() == null ? 500 : query.getPageSize();
        query.setPageNo(pageNo);
        query.setPageSize(pageSize);
    }

}
