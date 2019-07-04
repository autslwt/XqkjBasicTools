package com.xqkj.commons.export.handler;

import com.xqkj.commons.export.ExcelFileMaker;
import com.xqkj.commons.export.ExcelRowDataMaker;
import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExportExcelRequestVO;
import com.xqkj.commons.export.model.FileMakeResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.proxy.ExcelProxFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类的init方法加了@PostConstruct注解
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 12:50 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public abstract class BasicNormalDataExcelExportHandler<Q extends BasicPageQuery> extends BasicDataExcelExportHandler<Q>{

    private ExcelFileMaker excelFileMaker = ExcelProxFactory.getDefExcelFileMaker();

    private static Logger logger = LoggerFactory.getLogger(BasicNormalDataExcelExportHandler.class);

    @Override
    protected HandleResult<FileMakeResult> doMakeFile(ExportExcelRequestVO requestVO,String filePath,
                                                      String fileName,String extName, Q query,
                                                      ExcelWriteExtInfoVO excelWriteExtInfoVO){
        ExcelHeaderInfoVO excelHeaderInfoVO=getExcelHeaderInfoVO(requestVO);
        ExcelRowDataMaker<Q> excelRowDataMaker=getExcelRowDataMaker(requestVO);
        return excelFileMaker.makeAndWriteExcelFile(filePath,fileName,extName,query,excelHeaderInfoVO,
                excelRowDataMaker,excelWriteExtInfoVO);
    }

    protected abstract ExcelHeaderInfoVO getExcelHeaderInfoVO(ExportExcelRequestVO requestVO);

    protected abstract ExcelRowDataMaker<Q> getExcelRowDataMaker(ExportExcelRequestVO requestVO);

    public ExcelFileMaker getExcelFileMaker() {
        return excelFileMaker;
    }

    public void setExcelFileMaker(ExcelFileMaker excelFileMaker) {
        this.excelFileMaker = excelFileMaker;
    }
}
