package com.bjym.hyzc.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.MyTaskActivity;
import com.bjym.hyzc.activity.activity.PationtNameListActivity;
import com.bjym.hyzc.activity.bean.PationteBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
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
    private SharedPreferences sp;

    private List<PationteBean> pationtes;

    @Override
    public View setMainView() {
        View view = View.inflate(getContext(), R.layout.fragment_my, null);
        ll_myPationte = (LinearLayout) view.findViewById(R.id.ll_myPationte);
        ll_myTask = (LinearLayout) view.findViewById(R.id.ll_myTask);
        sp=getActivity().getSharedPreferences("MyselfConfig", Context.MODE_PRIVATE);


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
        if (bundle !=null) {
            String departmentCode = bundle.getString("departmentCode");
            String userCode = bundle.getString("userCode");
            String realName = bundle.getString("realName");
            int userType = bundle.getInt("userType");
            MyLog.i("userType:::",userType+"");
            //保存起来方便以后使用
            sp.edit().putString("departmentCode",departmentCode).commit();
            sp.edit().putString("userCode",userCode).commit();
            sp.edit().putString("realName",realName).commit();
            sp.edit().putInt("userType",userType);

         /*
         * 把用户信息赋值给textview
         * */
            tv_account.setText("用户名：" + userCode);
            tv_keshi.setText("科室名：" + departmentCode);
        }else{
            MyToast.showToast(MyFragment.this.getActivity(),"您还没有登录！");
            return;
        }

        ll_myPationte.setOnClickListener(this);
        ll_myTask.setOnClickListener(this);
        getPationteData();

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_myPationte:
                startActivity(new Intent(context, PationtNameListActivity.class));
                break;
            /*
            * 选择我的病人，跳转到任务界面 TODO
            * */
            case R.id.ll_myTask:

                startActivity(new Intent(context, MyTaskActivity.class));
                break;
            default:
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

        pationtes = new Gson().fromJson(jsonStr, new TypeToken<List<PationteBean>>() {
        }.getType());
    }

}
