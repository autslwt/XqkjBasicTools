package com.xqkj.commons.export.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xqkj.commons.export.FileUpLoader;
import com.xqkj.commons.export.model.FileUpLoadExtInfoVO;
import com.xqkj.commons.export.model.FileUpLoadResult;
import com.xqkj.commons.model.HandleResult;
import com.xqkj.commons.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 8:10 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefFileUploader implements FileUpLoader{
    @Override
    public HandleResult<FileUpLoadResult> doUploadFlie(File excelFile, String showFileName,
                                                       FileUpLoadExtInfoVO fileUpLoadExtInfoVO) throws Exception{
        String resultJson=FileUtils.uploadFile(fileUpLoadExtInfoVO.getUploadServiceUrl(),
                fileUpLoadExtInfoVO.getBoundary(), showFileName,excelFile);

        if(StringUtils.isNotBlank(resultJson)){
            if(StringUtils.isNotBlank(fileUpLoadExtInfoVO.getResultUrlPath())){
                return tryUseJsonPath(resultJson,fileUpLoadExtInfoVO.getResultUrlPath(),fileUpLoadExtInfoVO.getResultMsgPath());
            }
            return tryUseDefResult(resultJson);
        }
        return HandleResult.failed("上传文件失败");
    }

    private HandleResult<FileUpLoadResult> tryUseJsonPath(String resultJson,String urlJsonPath,String msgJsonPath){
        JSONObject jsonObject = JSON.parseObject(resultJson);
        if(jsonObject!=null){
            FileUpLoadResult fileUpLoadResult=new FileUpLoadResult();
            String url=getJsonObjVal(urlJsonPath,jsonObject);
            String msg=getJsonObjVal(msgJsonPath,jsonObject);
            if(StringUtils.isNotBlank(url)){
                fileUpLoadResult.setFileUrl(url);
                return HandleResult.success(fileUpLoadResult);
            }
            return HandleResult.failed(msg);
        }
        return HandleResult.failed("文件上传失败");
    }

    private HandleResult<FileUpLoadResult> tryUseDefResult(String resultJson){
        DefFileUploaderResult defFileUpLoadResult=JSON.parseObject(resultJson, DefFileUploaderResult.class);
        if(defFileUpLoadResult.getCode()!=null && defFileUpLoadResult.getCode()==0){
            FileUpLoadResult fileUpLoadResult=new FileUpLoadResult();
            fileUpLoadResult.setFileUrl(defFileUpLoadResult.getKey());
            return HandleResult.success(fileUpLoadResult);
        }
        return HandleResult.failed(defFileUpLoadResult.getCode(),defFileUpLoadResult.getDesc());
    }

    private String getJsonObjVal(String jsonPath,JSONObject jsonObject){
        if(StringUtils.isBlank(jsonPath)||jsonObject==null){
            return null;
        }
        String[] pathArr=jsonPath.split("\\.");
        int pathLength=pathArr.length;
        if(pathLength==1){
            return jsonObject.getString(jsonPath);
        }
        //
        JSONObject pathJsonObject = jsonObject;
        String value=null;
        for(int i=0;i<pathLength;i++){
            if(i==(pathLength-1)){
                value=pathJsonObject.getString(pathArr[i]);
                break;
            }
            pathJsonObject=pathJsonObject.getJSONObject(pathArr[i]);
            if(pathJsonObject==null){
                break;
            }
        }
        return value;
    }

    static class DefFileUploaderResult{

        private Integer code;

        private String desc;

        private String key;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

}
