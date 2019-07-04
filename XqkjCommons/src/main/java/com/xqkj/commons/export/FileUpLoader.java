package com.xqkj.commons.export;

import com.xqkj.annotation.MethodInteceptAnno;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.export.model.FileUpLoadExtInfoVO;
import com.xqkj.commons.export.model.FileUpLoadResult;
import com.xqkj.commons.model.HandleResult;

import java.io.File;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 11:03 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface FileUpLoader {

    @MethodInteceptAnno(managerName= IntecepterManagerNames.FileUpLoader_DoUploadFile,
            argsNamesHolderClass = "com.xqkj.methodargs.FileUpLoader_doUploadFlie")
    HandleResult<FileUpLoadResult> doUploadFlie(File excelFile, String showFileName,
                                                FileUpLoadExtInfoVO fileUpLoadExtInfoVO) throws Exception;

}
