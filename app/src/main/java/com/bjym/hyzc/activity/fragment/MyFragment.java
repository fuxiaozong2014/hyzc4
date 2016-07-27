package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.MyPationteActivity;
import com.bjym.hyzc.activity.activity.MyTaskActivity;
import com.bjym.hyzc.activity.bean.Pationte;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class MyFragment extends BaseFragment {

    private LinearLayout ll_myPationte;
    private LinearLayout ll_myTask;
    private TextView tv_account;
    private TextView tv_myPationte;
    private TextView tv_keshi;

    private List<Pationte> pationtes;


    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_my, null);
        ll_myPationte = (LinearLayout) view.findViewById(R.id.ll_myPationte);
        ll_myTask = (LinearLayout) view.findViewById(R.id.ll_myTask);

        tv_account = (TextView) view.findViewById(R.id.tv_account);
        tv_keshi = (TextView) view.findViewById(R.id.tv_keshi);
        tv_myPationte = (TextView) view.findViewById(R.id.tv_myPationte);
        return view;
    }

    @Override
    public void InitData() {

        /*
        * 得到mainActivity中传过来的用户信息
        * */
        Bundle bundle = getArguments();
        String departmentCode = bundle.getString("departmentCode");
        String userCode = bundle.getString("userCode");

        /*
        * 把用户信息赋值给textview
        * */
        tv_account.setText("用户名：" + userCode);
        tv_keshi.setText("科室名：" + departmentCode);

        ll_myPationte.setOnClickListener(this);
        ll_myTask.setOnClickListener(this);
        getPationteData();

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_myPationte:
                startActivity(new Intent(context, MyPationteActivity.class));
                break;
            /*
            * 选择我的病人，跳转到任务界面 TODO
            * */
            case R.id.ll_myTask:
                startActivity(new Intent(context, MyTaskActivity.class));
                break;
        }
    }

    private void getPationteData() {
        OkHttpUtils.get().url(MyConstant.MYPATIONTE_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String jsonStr = response.body().string();
                parseJson(jsonStr);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(MyFragment.this.getActivity(), "服务器正忙，请稍后重试");
            }

            @Override
            public void onResponse(Object o, int i) {
                tv_myPationte.setText("我的病人 ( " + pationtes.size() + " )");

            }
        });
    }

    private void parseJson(String jsonStr) throws JSONException {

        pationtes = new Gson().fromJson(jsonStr, new TypeToken<List<Pationte>>() {
        }.getType());
    }

}