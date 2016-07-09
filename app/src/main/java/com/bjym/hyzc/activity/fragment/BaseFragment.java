package com.bjym.hyzc.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * @author Wisn
 * @time 2016/6/25 18:36
 *
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public Context context;
   //private Callback callback;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getActivity();
        if(view==null){
            view = setMainView();
        }
        InitData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewParent parent = view.getParent();
        if(parent!=null){
            if(parent instanceof  ViewGroup){
                ((ViewGroup)parent).removeView(view);
            }
        }

    }

    /**
     * 子类实现选择监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

//    /**
//     * get 请求
//     *
//     * @param url 请求的url 拼接的url
//     * @param tag 请求使用的标志，用于区分不同的请求
//     */
//    public void get(String url, final String tag) {
//        HttpHelper httpHelper = HttpHelper.getInstance();
//        onNetStart(url, tag);
//        httpHelper.getAync(url,new MyCallBack(url,tag));
//    }
//
//    /**
//     * Post方式的
//     *
//     * @param url    请求的url
//     * @param params 请求的参数
//     * @param tag    请求的标记
//     */
//    public void post(String url, Hashtable<String, String> params, final String tag) {
//        HttpHelper httpHelper = HttpHelper.getInstance();
//        onNetStart(url, tag);
//        httpHelper.postAync(url,params, new MyCallBack(url,tag));
//    }
//
//    /**
//     * 上传文件 的post请求
//     * @param url  网络请求的url
//     * @param params  表单参数
//     * @param files 文件数组
//     * @param nameFile  文件名对应的数据
//     * @param tag  标志
//     */
//    public void postFile(String url, Hashtable<String, String> params, File[]  files, String[] nameFile, final String tag) {
//        HttpHelper httpHelper = HttpHelper.getInstance();
//        onNetStart(url, tag);
//        httpHelper.postAsynFile(url,params, files,nameFile,new MyCallBack(url,tag));
//    }
//
//    /**
//     * 请求失败
//     *
//     * @param result
//     * @param tag
//     */
//    public abstract void onFailureM(String result, String tag);
//
//    /**
//     * 请求成功
//     *
//     * @param result
//     * @throws IOException
//     */
//    public abstract void onSuccessM(String result, String tag);
//
//    /**
//     * 网络请求开始的时候调用
//     *
//     * @param url
//     * @param tag
//     */
//    public abstract void onNetStart(String url, String tag);
//
//    /**
//     * 请求完成后必须要调用的方法，不管请求是否成功
//     * @param url  请求的url
//     * @param tag   请求的标志
//     */
//    public  abstract   void onNetFinish(String  url,String tag);

    /**
     * 用于指定View和View的初始化
     *
     * @return
     */
    public abstract View setMainView();

    /**
     * 加载数据
     */
    public abstract void InitData();

//    /**
//     * 网络回调封装
//     */
//    private  class  MyCallBack implements Callback {
//        private  String url;
//        String requesttemp = "";
//        public String tag;
//        public  MyCallBack(String url,String tag){
//            this.tag=tag;
//            this.url=url;
//        }
//        /**
//         * 请求失败
//         * @param request
//         * @param e
//         */
//        @Override
//        public void onFailure(final Request request, IOException e) {
//            try {
//               /* if (request != null) {
//                    requesttemp = request.body().toString();
//                } else {
//                    requesttemp = "request error";
//                }*/
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        onFailureM("request error", tag);
//                    }
//                });
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            } finally {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        onNetFinish(url, tag);
//                    }
//                });
//            }
//
//
//        }
//
//        /**
//         * 请求成功
//         * @param response
//         * @throws IOException
//         */
//        @Override
//        public void onResponse(final Response response) throws IOException {
//            try {
//                final String string = response.body().string();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            if(response.isSuccessful()){
//                                onSuccessM(string, tag);
//                            }else{
//                                onFailureM(requesttemp, tag);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        onNetFinish(url, tag);
//                    }
//                });
//            }
//
//        }
//    };
}
