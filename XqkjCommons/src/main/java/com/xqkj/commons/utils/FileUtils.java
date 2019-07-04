package com.xqkj.commons.utils;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/25 11:12 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class FileUtils {

    private static String BOUNDARY = "----------ThIs_Is_tHe_bouNdaRY_$";

    public static String uploadFile(String serviceUrl, String fileName, File file) throws Exception{
        return uploadFile(serviceUrl,BOUNDARY,fileName,file);
    }

    public static String uploadFile(String serviceUrl,String boundary, String fileName, File file) throws Exception {
        OkHttpClient client = new OkHttpClient();
        if(boundary==null){
            boundary=BOUNDARY;
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"+boundary), file))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + UUID.randomUUID())
                .url(serviceUrl)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }

        return response.body().string();
    }

    public static void main(String[] args) {
        String path="/Users/lwt-mac/Documents/资料文档/exceltest";
        File file=new File(path+"/handler-test-01.xlsx");
        try {
            String result=uploadFile("http://sp.52shangou.com/sp/upload/new","handler-test-01.xlsx",file);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
