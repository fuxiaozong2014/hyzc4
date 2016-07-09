//package com.bjym.hyzc.activity.net;
//
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Hashtable;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author Wisn
// * @time 2016/6/25 18:31
// * 网络请求工具类
// */
//
//public class HttpHelper {
//    // baseurl
//    public static final String BASE_URL = "http://127.0.0.1:8090";
//    private static HttpHelper  httpHelper;
//    private final OkHttpClient client = new OkHttpClient();
//
//    private  HttpHelper() {
//        /**
//         * 设置网络请求的超时时间
//         */
//        client.setConnectTimeout(5, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 单例用于获取一个网络请求helper
//     * @return
//     */
//    public  static  HttpHelper  getInstance(){
//        synchronized (HttpHelper.class){
//            if(httpHelper==null){
//                httpHelper=new HttpHelper();
//            }
//            return httpHelper;
//        }
//    }
//
//    /**
//     * 同步的get请求
//     *
//     * @return
//     */
//    public String getSync(String url) {
//        // restful  风格
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful()) { // 200
//                String json = response.body().string();
//                return json;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    /**
//     * 异步的get请求
//     * @param callback
//     */
//    public void getAync(String url,Callback callback) {
//        // restful  风格
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(callback );
//    }
//
//    /**
//     * Post请求的方式
//     * @param params
//     * @param callback
//     */
//    public  void postAync(String  url,Hashtable<String,String> params, Callback callback){
//        Request request = new Request.Builder()
//                .url(url)
//                .post(ParamsUtils.getRequestBody(params))
//                .build();
//        client.newCall(request).enqueue(callback );
//    }
//
//    /**
//     * post文件上传
//     * @param url
//     * @param params
//     * @param files  文件数组
//     * @param callback
//     */
//    public  void  postAsynFile(String url ,Hashtable<String,String> params,File[]  files, String[] nameFile,Callback callback ){
//        Request request = new Request.Builder()
//                .url(url)
//                .post(ParamsUtils.getRequestBodyFile(params,files,nameFile))
//                .build();
//        client.newCall(request).enqueue(callback);
//    }
//
//}
