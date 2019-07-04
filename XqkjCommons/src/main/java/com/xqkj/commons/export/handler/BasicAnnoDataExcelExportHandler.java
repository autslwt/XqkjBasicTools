package com.xqkj.commons.export.handler;

import com.xqkj.commons.export.AnnoDataExcelFileMaker;
import com.xqkj.commons.export.PageListDateMaker;
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
public abstract class BasicAnnoDataExcelExportHandler<T,Q extends BasicPageQuery> extends BasicDataExcelExportHandler<Q>{

    private AnnoDataExcelFileMaker annoDataExcelFileMaker = ExcelProxFactory.getDefAnnoDataExcelFileMaker();

    private static Logger logger = LoggerFactory.getLogger(BasicAnnoDataExcelExportHandler.class);

    @Override
    protected HandleResult<FileMakeResult> doMakeFile(ExportExcelRequestVO requestVO,String filePath,String fileName,
                                                      String extName, Q query,
                                                      ExcelWriteExtInfoVO excelWriteExtInfoVO){
        Class dataType=getDataTypeClass();
        PageListDateMaker<T,Q> pageListDateMaker=getPageListMaker(requestVO);
        return annoDataExcelFileMaker.makeAndWriteAnnoExcelFile(filePath,fileName,extName,query,dataType,
                pageListDateMaker,excelWriteExtInfoVO);
    }

    protected abstract Class<T> getDataTypeClass();

    protected abstract PageListDateMaker<T,Q> getPageListMaker(ExportExcelRequestVO requestVO);

    public AnnoDataExcelFileMaker getAnnoDataExcelFileMaker() {
        return annoDataExcelFileMaker;
    }

    public void setAnnoDataExcelFileMaker(AnnoDataExcelFileMaker annoDataExcelFileMaker) {
        this.annoDataExcelFileMaker = annoDataExcelFileMaker;
    }

}
