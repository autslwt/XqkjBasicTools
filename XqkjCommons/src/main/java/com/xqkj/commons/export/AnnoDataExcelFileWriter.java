package com.xqkj.commons.export;

import com.xqkj.annotation.MethodInteceptAnno;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/26 4:20 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface AnnoDataExcelFileWriter extends ExcelFileWriter{

    /**
     *
     * @param outputStream
     * @param workbook
     * @param sheetName
     * @param query -- 切面中如果使用该参数的子类，需要先行判断是否是期待的子类；
     *              该参数一般只用来传递给PageListDateMaker使用，框架中应该使用基类
     * @param dateType
     * @param pageListDateMaker
     * @param excelWriteExtInfoVO
     * @param <T> PageListDateMaker 数据列表的数据类型
     * @param <Q> 查询参数的实际类型
     * @return
     */
    @MethodInteceptAnno(managerName= IntecepterManagerNames.AnnoDateExcelFileWriter_WriteExcelAnnoData,
            argsNamesHolderClass = "com.xqkj.methodargs.AnnoDataExcelFileWriter_writeExcelAnnoData")
    <T,Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> writeExcelAnnoData(OutputStream outputStream,
                                                      Workbook workbook, String sheetName,
                                                      Q query, Class<T> dateType,
                                                      PageListDateMaker<T,Q> pageListDateMaker,
                                                      ExcelWriteExtInfoVO excelWriteExtInfoVO);

    /**
     *
     * @param outputStream
     * @param excelExtName
     * @param query-- 切面中如果使用该参数的子类，需要先行判断是否是期待的子类；
     *              该参数一般只用来传递给PageListDateMaker使用，框架中应该使用基类
     * @param dateType
     * @param pageListDateMaker
     * @param excelWriteExtInfoVO
     * @param <T> PageListDateMaker 数据列表的数据类型
     * @param <Q> 查询参数的实际类型
     * @return
     */
    <T,Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> createAndWriteExcelAnnoData(OutputStream outputStream,
                                                               String excelExtName,
                                                               Q query, Class<T> dateType,
                                                               PageListDateMaker<T,Q> pageListDateMaker,
                                                               ExcelWriteExtInfoVO excelWriteExtInfoVO);

}
