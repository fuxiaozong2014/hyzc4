package com.bjym.hyzc.activity.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.MyPationteActivity;
import com.bjym.hyzc.activity.bean.Myself;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/6/28.
 */
public class AccenterFragment extends BaseFragment {

    private LinearLayout ll_myPationte;
    private TextView tv_account;
    private List<Myself> myselefLists;


    @Override
    public View setMainView() {
            View view = View.inflate(getContext(), R.layout.fragment_accenter, null);
            ll_myPationte = (LinearLayout) view.findViewById(R.id.ll_myPationte);
            tv_account = (TextView) view.findViewById(R.id.tv_account);
            return view;
    }

    @Override
    public void InitData() {
        getNetData();
        ll_myPationte.setOnClickListener(this);
    }

    private void getNetData() {
        OkHttpUtils.get().url(MyConstant.MYMSG_URL).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                String json = response.body().string();
                parseJson(json);
                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(AccenterFragment.this.getActivity(), "请求失败");
            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(AccenterFragment.this.getActivity(), "请求成功");

            }
        });
    }

    private void parseJson(String json) {
        String realName = null;
        myselefLists = new Gson().fromJson(json, new TypeToken<List<Myself>>() {
        }.getType());
        for (Myself myself : myselefLists) {
            realName = myself.RealName;
            tv_account.setText("用户名：" + realName);
            //the second change test
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_myPationte:
                startActivity(new Intent(context, MyPationteActivity.class));
                break;
        }
    }
}
