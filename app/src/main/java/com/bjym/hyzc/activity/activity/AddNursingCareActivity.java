package com.bjym.hyzc.activity.activity;

import android.view.View;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/8.
 */
public class AddNursingCareActivity extends BaseActivity {
    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.actvity_addnursingcare, null);
        return view;
    }

    @Override
    public void InitData() {

        commitAddNursingCare();

    }

    private void commitAddNursingCare() {


        OkHttpUtils.postString().mediaType(MediaType.parse("application/json")).url(MyConstant.ADD_NURSINGCARE_COMIT).content("")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                MyLog.i("回调response.body().toString();", response.body().string());
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(AddNursingCareActivity.this, "commitAddNursingCare" + e.toString());

                MyLog.i("NueseHistory", "提交错误" + e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {

                MyToast.showToast(AddNursingCareActivity.this, "请求成功" + "commitAddNursingCare");
                MyLog.i("请求成功", "commitAddNursingCare" + o);

            }
        });
    }
}
