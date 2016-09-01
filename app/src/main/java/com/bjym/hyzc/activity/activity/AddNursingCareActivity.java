
package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bjym.hyzc.R;
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
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.actvity_addnursingcare, null);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        return view;
    }

    @Override
    public void InitData() {
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("新增护理内容");
        bt_titlebar_left.setOnClickListener(this);
        commitAddNursingCare();

    }

    private void commitAddNursingCare() {

            //TODO 提交内容的url没有得到

        OkHttpUtils.postString().mediaType(MediaType.parse("application/json")).url("http://192.168.0.168").content("")
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;

        }
    }
}

