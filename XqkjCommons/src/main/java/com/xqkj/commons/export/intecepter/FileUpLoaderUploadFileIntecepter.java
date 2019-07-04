package com.xqkj.commons.export.intecepter;

import com.xqkj.commons.constant.IntecepterManagerNames;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 7:45 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class FileUpLoaderUploadFileIntecepter extends AbstractFileUploadIntecepter {

    @Override
    public String getManagerName() {
        return IntecepterManagerNames.FileUpLoader_DoUploadFile;
    }
}
