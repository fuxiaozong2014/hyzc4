package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/3.
 */
 public class NurseExecuteActivity extends BaseActivity{


    private String cpwCode;

    @Override
    public View setMainView() {
        View view=View.inflate(this, R.layout.activity_nursexecute,null);
        return view;
    }

    @Override
    public void InitData() {
        getNurseStageData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_OK:
                cpwCode = data.getStringExtra("cpwCode");
        }
    }

    private void getNurseStageData() {
        OkHttpUtils.get().url(MyConstant.NURSE_STAGE+cpwCode).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String stringJson = response.body().string();
                MyLog.i("stringJson",stringJson);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(Object o, int i) {

            }
        });

    }
}
