//package com.bjym.hyzc.activity.net;
//
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.Headers;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.MultipartBuilder;
//import com.squareup.okhttp.RequestBody;
//
//import java.io.File;
//import java.net.FileNameMap;
//import java.net.URLConnection;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * @author Wisn
// * @time 2016/6/25 23:33
// */
//
//public class ParamsUtils {
//    /**
//     * 用于生成post提交的请求体
//     * @param data
//     * @return
//     */
//    public static RequestBody getRequestBody(Hashtable<String,String> data){
//        Iterator<Map.Entry<String, String>> formdata = data.entrySet().iterator();
//        FormEncodingBuilder formbuilder = new FormEncodingBuilder();
//        while(formdata.hasNext()){
//            Map.Entry<String, String> nextdata = formdata.next();
//            formbuilder.add(nextdata.getKey(),nextdata.getValue());
//        }
//        return formbuilder.build();
//    }
//
//    /**
//     *
//     * @param data  表单数据
//     * @param files  文件数组
//     * @param nameFile  文件数组对应的表单名(key)
//     * @return
//     */
//    public static RequestBody getRequestBodyFile(Hashtable<String,String> data, File[]  files, String[] nameFile){
//        Iterator<Map.Entry<String, String>> formdata = data.entrySet().iterator();
//        MultipartBuilder builder = new MultipartBuilder()
//                .type(MultipartBuilder.FORM);
//        while(formdata.hasNext()){
//            Map.Entry<String, String> nextdata = formdata.next();
//            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + nextdata.getKey() + "\""),
//                    RequestBody.create(null, nextdata.getValue()));
//        }
//        if (files != null)
//        {
//            RequestBody fileBody = null;
//            for (int i = 0; i < files.length; i++)
//            {
//                File file = files[i];
//                String fileName = file.getName();
//                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
//                //TODO 根据文件名设置contentType
//                builder.addPart(Headers.of("Content-Disposition",
//                        "form-data; name=\"" + nameFile[i] + "\"; filename=\"" + fileName + "\""),
//                        fileBody);
//            }
//        }
//
//        return builder.build();
//    }
//    private static  String guessMimeType(String path)
//    {
//        FileNameMap fileNameMap = URLConnection.getFileNameMap();
//        String contentTypeFor = fileNameMap.getContentTypeFor(path);
//        if (contentTypeFor == null)
//        {
//            contentTypeFor = "application/octet-stream";
//        }
//        return contentTypeFor;
//    }
//
//}
