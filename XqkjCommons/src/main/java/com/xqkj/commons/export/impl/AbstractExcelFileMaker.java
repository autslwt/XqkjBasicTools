package com.xqkj.commons.export.impl;

import com.xqkj.commons.constant.ExcelFileContants;
import com.xqkj.commons.export.ExcelRowDataMaker;
import com.xqkj.commons.export.utils.ExcelFileUtils;
import com.xqkj.commons.export.PageListDateMaker;
import com.xqkj.commons.utils.ZipFileUtils;
import com.xqkj.commons.export.model.*;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.proxy.ProxyObjAware;
import com.xqkj.commons.utils.IOUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 以文件方式导出excel文件的模版累，子类应该关心查询数据的方式
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 11:24 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public abstract class AbstractExcelFileMaker<T,P extends BasicWriteExcelDateParam> extends ProxyObjAware<T> {

    protected File createFile(String filePath, String fileName, String extName) {
        if (StringUtils.isBlank(extName)) {
            extName = ExcelFileContants.EXCEL_2007_EXTNAME;
        }
        return ExcelFileUtils.createFile(filePath, fileName, extName);
    }

    protected HandleResult<FileMakeResult> executeWrite(P param,
                                                        AbstractExcelFileMaker.WriteExcelDate writeExcelDate) {
        File file = createFile(param.filePath, param.fileName, param.extName);
        String simpleFlieName = ExcelFileUtils.getSimpleFileName(file);
        int fileIndex = 1;
        List<File> fileList = new ArrayList<>();
        ExcelWriteExtInfoVO excelWriteExtInfoVO = param.excelWriteExtInfoVO == null ? new ExcelWriteExtInfoVO() : param.excelWriteExtInfoVO;
        List<ExcelRowInfoVO> initRowList;

        do {
            String tempName = simpleFlieName + "-" + fileIndex++;
            File tmpFile = createFile(param.filePath, tempName, param.extName);
            OutputStream outputStream = null;
            Workbook workbook;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
                workbook = ExcelFileUtils.createWorkbook(param.extName);
                HandleResult<ExcelWriteResult> handleResult = writeExcelDate.doWrite(param, outputStream, workbook);
                if (!handleResult.isSuccess()) {
                    return HandleResult.failed(handleResult.getCode(), handleResult.getMsg());
                }
                //
                initRowList = handleResult.getEntity().getNotWriteDateList();
                excelWriteExtInfoVO.setInitRowDateList(initRowList);
                //
                outputStream.flush();
                workbook.write(outputStream);
                fileList.add(tmpFile);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                IOUtil.closeIOStream(outputStream);
            }
        } while (excelWriteExtInfoVO.isZipAble() && CollectionUtils.isNotEmpty(initRowList));
        //
        if (fileList.size() == 1) {
            fileList.get(0).renameTo(file);
        } else {
            file = ExcelFileUtils.createFile(param.filePath, simpleFlieName, "zip");
            ZipFileUtils.zipFileList(fileList, file, true);
        }
        //
        FileMakeResult fileMakeResult = new FileMakeResult();
        fileMakeResult.setFile(file);
        return HandleResult.success(fileMakeResult);
    }

    public static class WriteExcelDateParam extends BasicWriteExcelDateParam {

        //
        public ExcelHeaderInfoVO excelHeaderInfoVO;
        public ExcelRowDataMaker excelRowDataMaker;

        //
        public WriteExcelDateParam(String filePath, String fileName, String extName,
                                   BasicPageQuery query, ExcelHeaderInfoVO excelHeaderInfoVO,
                                   ExcelRowDataMaker excelRowDataMaker,
                                   ExcelWriteExtInfoVO excelWriteExtInfoVO) {
            super(filePath, fileName, extName, query, excelWriteExtInfoVO);
            this.excelHeaderInfoVO = excelHeaderInfoVO;
            this.excelRowDataMaker = excelRowDataMaker;
        }
    }

    public static class WriteAnnoExcelDateParam extends BasicWriteExcelDateParam {

        //
        public Class dateType;
        public PageListDateMaker pageListDateMaker;

        //
        public WriteAnnoExcelDateParam(String filePath, String fileName, String extName,
                                       BasicPageQuery query, Class dateType,
                                       PageListDateMaker pageListDateMaker,
                                       ExcelWriteExtInfoVO excelWriteExtInfoVO) {
            super(filePath, fileName, extName, query, excelWriteExtInfoVO);
            this.dateType = dateType;
            this.pageListDateMaker = pageListDateMaker;
        }

    }

    protected interface WriteExcelDate<P extends BasicWriteExcelDateParam> {
        HandleResult<ExcelWriteResult> doWrite(P param,
                                               OutputStream outputStream,
                                               Workbook workbook);
    }
}
