package com.bjym.hyzc.activity.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bjym.hyzc.R;

/**
 * @author Wisn
 * @time 2016/6/25 18:36
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Context context;
    //private Callback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        this.context = this;
        setContentView(setMainView());
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
        InitData();
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
//        httpHelper.getAync(url, new MyCallBack(url, tag));
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
//        httpHelper.postAync(url, params, new MyCallBack(url, tag));
//    }
//
//    /**
//     * 上传文件 的post请求
//     *
//     * @param url      网络请求的url
//     * @param params   表单参数
//     * @param files    文件数组
//     * @param nameFile 文件名对应的数据
//     * @param tag      标志
//     */
//    public void postFile(String url, Hashtable<String, String> params, File[] files, String[] nameFile, final String tag) {
//        HttpHelper httpHelper = HttpHelper.getInstance();
//        onNetStart(url, tag);
//        httpHelper.postAsynFile(url, params, files, nameFile, new MyCallBack(url, tag));
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
//    public abstract void onSuccessM(String result, String tag) throws JSONException;
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
//     *
//     * @param url 请求的url
//     * @param tag 请求的标志
//     */
//    public abstract void onNetFinish(String url, String tag);

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
//    private class MyCallBack implements Callback {
//        private String url;
//        String requesttemp = "";
//        public String tag;
//
//        public MyCallBack(String url, String tag) {
//            this.tag = tag;
//            this.url = url;
//        }
//
//        /**
//         * 请求失败
//         *
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
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        onFailureM("request error", tag);
//                    }
//                });
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            } finally {
//                runOnUiThread(new Runnable() {
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
//         *
//         * @param response
//         * @throws IOException
//         */
//        @Override
//        public void onResponse(final Response response) throws IOException {
//            try {
//                final String string = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            if (response.isSuccessful()) {
//                                onSuccessM(string, tag);
//                            } else {
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
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        onNetFinish(url, tag);
//                    }
//                });
//            }
//
//        }
//    }

    ;
}
