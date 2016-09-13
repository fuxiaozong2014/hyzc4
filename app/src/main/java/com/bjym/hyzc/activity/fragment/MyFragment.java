package com.bjym.hyzc.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.MyTaskActivity;
import com.bjym.hyzc.activity.activity.MyTaskDoctorActivity;
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
    private LinearLayout ll_myTaskDOctor;
    private TextView tv_account;
    private TextView tv_myPationte;
    private TextView tv_keshi;


    private List<PationteBean> pationtes;
    private int userType;
    private SharedPreferences sp;
    private ImageView iv_icon;
    private String qq;


    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.fragment_my, null);
        ll_myPationte = (LinearLayout) view.findViewById(R.id.ll_myPationte);
        ll_myTask = (LinearLayout) view.findViewById(R.id.ll_myTask);
        ll_myTaskDOctor = (LinearLayout) view.findViewById(R.id.ll_myTaskDOctor);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

        tv_account = (TextView) view.findViewById(R.id.tv_account);
        tv_keshi = (TextView) view.findViewById(R.id.tv_keshi);
        tv_myPationte = (TextView) view.findViewById(R.id.tv_myPationte);

        return view;
    }

    @Override
    public void InitData() {
        sp=getActivity().getSharedPreferences("MyselfConfig", Context.MODE_PRIVATE);
      /*  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());*/
        if (sp!=null) {
            String departmentCode = sp.getString("departmentCode","");
            String userCode = sp.getString("userCode","");
            String realName = sp.getString("realName","");
            userType = sp.getInt("userType",0);
            qq = sp.getString("qq","");
            MyLog.i("userType:::", userType +"");

         /*
         * 把用户信息赋值给textview
         * */
            tv_account.setText("用户名：" + userCode);
            tv_keshi.setText("科室名：" + departmentCode);
        }else{
            MyToast.showToast(MyFragment.this.getActivity(),"您还没有登录！");
            return;
        }
        if(Integer.parseInt(qq)==4||Integer.parseInt(qq)==5){//护理人员
            ll_myTaskDOctor.setVisibility(View.GONE);
        } else if(Integer.parseInt(qq)==3){//医生
            ll_myTask.setVisibility(View.GONE);
        }

        ll_myPationte.setOnClickListener(this);
        ll_myTask.setOnClickListener(this);
        ll_myTaskDOctor.setOnClickListener(this);
        iv_icon.setOnClickListener(this);
        getPationteData();

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            /*
            * 跳转到我的病人界面
            * */
            case R.id.ll_myPationte:
                startActivity(new Intent(context, PationtNameListActivity.class));
                break;
            /*
            * 选择我的病人，跳转到任务界面
            * */
            case R.id.ll_myTask:
                startActivity(new Intent(context, MyTaskActivity.class));
                break;
            /*
            * 选择我的病人，跳转到任务界面
            * */
            case R.id.ll_myTaskDOctor:
                startActivity(new Intent(context, MyTaskDoctorActivity.class));
                break;
             /*
            * 点击图标打开本地相册，选择头像，显示在客户端，并且上传至服务器 TODO
            * */
            case R.id.iv_icon:

                break;
            default:
                break;
        }
    }

    private void getPationteData() {
        OkHttpUtils.get().url(MyConstant.BASE_URL+ MyConstant.MYPATIONTE_URL).build().execute(new Callback() {
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
