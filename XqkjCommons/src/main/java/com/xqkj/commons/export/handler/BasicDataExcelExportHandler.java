package com.xqkj.commons.export.handler;

import com.alibaba.fastjson.JSON;
import com.xqkj.commons.basic.BasicHandler;
import com.xqkj.commons.constant.ExcelFileContants;
import com.xqkj.commons.export.ExcelHeaderRowWriter;
import com.xqkj.commons.export.ExcelRowCellWriter;
import com.xqkj.commons.export.FileUpLoader;
import com.xqkj.commons.export.model.*;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.proxy.ExcelProxFactory;
import com.xqkj.commons.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * 该类的init方法加了@PostConstruct注解
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/30 12:50 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public abstract class BasicDataExcelExportHandler<Q extends BasicPageQuery>
        implements BasicHandler<HandleResult<String>,ExportExcelRequestVO>{

    /**
     * 这个一般都会重写,使用getFileUpLoadExtInfoVO(ExportExcelRequestVO requestVO)返回一个实例，init中会帮助进行一次注册替换
     */
    private FileUpLoader fileUpLoader=ExcelProxFactory.getDefFileUpLoader();

    private boolean customHasSetFileUpLoader=false;

    private String excelHeaderRowWriterName=null;

    private String excelRowCellwWriterName=null;

    private static Logger logger = LoggerFactory.getLogger(BasicDataExcelExportHandler.class);

    @PostConstruct
    public void init(){
        logger.info("BasicAnnoDataExcelExportHandler init:class="+this.getClass().getName());
        SimpleExcelExportHandlerDispatcher.addBasicHandler(this);
        resetFileUpLoaderOnce();
        registExcelHeaderRowWriterOnce();
        registExcelRowCellWriterOnce();
    }

    @Override
    public HandleResult<String> doHandler(ExportExcelRequestVO requestVO){
        try{
            String filePath=getFilePath(requestVO);
            String fileName=getFileName(requestVO);
            String extName=getExtName();
            ExcelWriteExtInfoVO excelWriteExtInfoVO = getExcelWriteExtInfoVO(requestVO);
            excelWriteExtInfoVO=excelWriteExtInfoVO==null?new ExcelWriteExtInfoVO():excelWriteExtInfoVO;
            resetExcelWriteExtInfoVO(excelWriteExtInfoVO,requestVO);
            HandleResult<FileMakeResult> fileMakeResult=doMakeFile(requestVO,filePath,fileName,extName,
                    getPageQueryByRequest(requestVO),excelWriteExtInfoVO);
            if(fileMakeResult.isSuccess() && requestVO.isNeedUpload()){
                Assert.isNotNull(fileMakeResult.getEntity(),"文件生成结果异常：fileMakeResult.getEntity 不能为空");
                File file=fileMakeResult.getEntity().getFile();
                Assert.isNotNull(file,"文件生成结果异常：file 不能为空");
                //
                FileUpLoadExtInfoVO fileUpLoadExtInfoVO=getFileUpLoadExtInfoVO(requestVO);
                fileUpLoadExtInfoVO=fileUpLoadExtInfoVO==null?new FileUpLoadExtInfoVO():fileUpLoadExtInfoVO;
                fileUpLoadExtInfoVO.setUid(requestVO.getUid());
                HandleResult<FileUpLoadResult> uploadResult=fileUpLoader.doUploadFlie(file,fileName+"."+extName,fileUpLoadExtInfoVO);
                if(!uploadResult.isSuccess()){
                    logger.error("文件上传失败:requestVO="+ JSON.toJSONString(requestVO)
                            +";result="+JSON.toJSONString(uploadResult));
                }
            }
            return HandleResult.success(requestVO.getUid());
        }catch (Exception ex){
            logger.error("文件倒出处理发生异常：exception="+ex.getMessage(),ex);
            return HandleResult.failed(ex.getMessage());
        }
    }

    /**
     * 设置文件生成路径
     * @param requestVO
     * @return
     */
    protected String getFilePath(ExportExcelRequestVO requestVO){
        return null;
    }
    /**
     * 设置生成的文件的名字
     * @param requestVO
     * @return
     */
    protected String getFileName(ExportExcelRequestVO requestVO){
        return null;
    }
    /**
     * 获取文件的扩展名
     * @return
     */
    protected String getExtName(){
        return ExcelFileContants.EXCEL_2007_EXTNAME;
    }

    protected abstract Q getPageQueryByRequest(ExportExcelRequestVO requestVO);

    protected abstract ExcelWriteExtInfoVO getExcelWriteExtInfoVO(ExportExcelRequestVO requestVO);

    protected abstract FileUpLoadExtInfoVO getFileUpLoadExtInfoVO(ExportExcelRequestVO requestVO);

    protected abstract HandleResult<FileMakeResult> doMakeFile(ExportExcelRequestVO requestVO,
                                                               String filePath,String fileName,
                                                               String extName, Q query,
                                                               ExcelWriteExtInfoVO excelWriteExtInfoVO);

    public FileUpLoader getFileUpLoader() {
        return fileUpLoader;
    }

    public void setFileUpLoader(FileUpLoader fileUpLoader) {
        this.fileUpLoader=fileUpLoader;
    }

    /**
     * 返回定制的文件上传器--一般需要定制
     * @return
     */
    protected FileUpLoader getCoustomerFileUpLoader(){
        return null;
    }

    /**
     * 定制表头写处理器--一般不用定制
     * @return
     */
    protected ExcelHeaderRowWriter getCoustomerExcelHeaderRowWriter(){
        return null;
    }

    /**
     * 定制单元格写处理器--一般不用定制
     * @return
     */
    protected ExcelRowCellWriter getCoustomerExcelRowCellWriter(){
        return null;
    }

    private void resetFileUpLoaderOnce() {
        FileUpLoader customerFileUploader = getCoustomerFileUpLoader();
        if(!customHasSetFileUpLoader && customerFileUploader!=null){
            synchronized (BasicDataExcelExportHandler.class){
                if(!customHasSetFileUpLoader){
                    this.fileUpLoader=ExcelProxFactory.registToProxObject(fileUpLoader.getClass().getName(),
                            FileUpLoader.class,customerFileUploader);
                    customHasSetFileUpLoader=true;
                }
            }
        }
    }

    private void registExcelHeaderRowWriterOnce() {
        ExcelHeaderRowWriter excelHeaderRowWriter = getCoustomerExcelHeaderRowWriter();
        if(excelHeaderRowWriterName==null && excelHeaderRowWriter!=null){
            synchronized (BasicDataExcelExportHandler.class){
                if(excelHeaderRowWriterName==null){
                    String registName=excelHeaderRowWriter.getClass().getName();
                    ExcelProxFactory.registToNormalObject(registName,
                            ExcelHeaderRowWriter.class,excelHeaderRowWriter);
                    excelHeaderRowWriterName=registName;
                }
            }
        }
    }

    private void registExcelRowCellWriterOnce() {
        ExcelRowCellWriter excelRowCellWriter = getCoustomerExcelRowCellWriter();
        if(excelRowCellwWriterName==null && excelRowCellWriter!=null){
            synchronized (BasicDataExcelExportHandler.class){
                if(excelRowCellwWriterName==null){
                    String registName=excelRowCellWriter.getClass().getName();
                    ExcelProxFactory.registToNormalObject(registName,
                            ExcelRowCellWriter.class,excelRowCellWriter);
                    excelRowCellwWriterName=registName;
                }
            }
        }
    }

    private void resetExcelWriteExtInfoVO(ExcelWriteExtInfoVO excelWriteExtInfoVO,ExportExcelRequestVO requestVO){
        excelWriteExtInfoVO=excelWriteExtInfoVO==null?new ExcelWriteExtInfoVO():excelWriteExtInfoVO;
        excelWriteExtInfoVO.setUid(requestVO.getUid());
        excelWriteExtInfoVO.setParentType(requestVO.getParentType());
        excelWriteExtInfoVO.setExportType(requestVO.getExportType());
        if(requestVO.getFileAlisName()!=null){
            excelWriteExtInfoVO.setFileAlisName(requestVO.getFileAlisName());
        }
        if(excelHeaderRowWriterName!=null){
            excelWriteExtInfoVO.setExcelHeaderRowWriterName(excelHeaderRowWriterName);
        }
        if(excelRowCellwWriterName!=null){
            excelWriteExtInfoVO.setExcelRowCellWriterName(excelRowCellwWriterName);
        }
    }

}
