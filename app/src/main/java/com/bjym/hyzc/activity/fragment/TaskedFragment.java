package com.bjym.hyzc.activity.fragment;

import android.view.View;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/27.
 */
public class TaskedFragment extends BaseFragment{
    @Override
    public View setMainView() {

        View view=View.inflate(context, R.layout.fragment_tasked,null);
        return view;
    }

    @Override
    public void InitData() {
//        Bundle bundle = getArguments();
//
//        if (bundle!=null) {
//            String name = bundle.getString("name");
//            String patientsNo = bundle.getString("patientsNo");
//            if (name!=null && patientsNo!=null){
//                getNewCode();
//            }
//        }




    }

    private void getNewCode() {
        OkHttpUtils.get().url(MyConstant.NURSE_HISTORY).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                return response;
            }

            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(Object response, int i) {
                if (response instanceof Response) {
                    try {
                        String result = ((Response) response).body().string();
                        JSONObject jsonObjecet=new JSONObject(result);
                        String newCode = jsonObjecet.getString("newCode");
                        MyLog.i("newCode:::::",newCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
