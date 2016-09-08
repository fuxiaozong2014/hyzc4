package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.PationteBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.bjym.hyzc.activity.zxing.code.CaptureActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/7/27.
 */
public class MyTaskActivity extends BaseActivity {

    private static final int REQUST_CODE_PATIONTEMSG = 1;
    private static final int REQUST_CODE_SCANBAR =0 ;
    private TextView tv_search;
    private LinearLayout ll_nurseHistory;
    private LinearLayout ll_nurseStage;
    private LinearLayout ll_addNursingCare;
    private TextView tv_nursingContent_executed;
    private LinearLayout ll_nursingContentStage;
    private String name;
    private String patientsNo;
    private String cpwCode;
    private String deptName;
    private String deptCode;
    private ImageView iv_search;
    private String codeinformation;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private LinearLayout ll_yiZhuManager;
    private SharedPreferences SpPationteMsgConfig;
    private String transName;//传递给surveyActivity的调查人名字，用于传递过去，调查结果的提交
    private String transPationteNo;//传递给surveyActivity的调查人编号，用于传递过去，调查结果的提交
    private String transCpwCode;//传递给surveyActivity的调查人编号，用于传递过去，调查结果的提交
    private String transDeptName;
    private String transDeptCode;



    @Override
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_mytask, null);
        ll_nurseHistory = (LinearLayout) view.findViewById(R.id.ll_nurseHistory);
        ll_nurseStage = (LinearLayout) view.findViewById(R.id.ll_nurseStage);
        ll_addNursingCare = (LinearLayout) view.findViewById(R.id.ll_addNursingCare);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        //tv_nursingContent_executed = (TextView) view.findViewById(R.id.tv_nursingContent_executed);
        //  ll_yiZhuManager = (LinearLayout) view.findViewById(R.id.ll_YiZhuManager);

        ll_nursingContentStage = (LinearLayout) view.findViewById(R.id.ll_nursingContentStage);
        tv_search = (TextView) view.findViewById(R.id.tv_search);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {


        SpPationteMsgConfig = getSharedPreferences("PationteMsgConfig", MODE_PRIVATE);
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("我的任务");
        tv_search.setOnClickListener(this);
        ll_nurseHistory.setOnClickListener(this);
        ll_nurseStage.setOnClickListener(this);
        // tv_nursingContent_executed.setOnClickListener(this);
        ll_addNursingCare.setOnClickListener(this);
        ll_nursingContentStage.setOnClickListener(this);
        bt_titlebar_left.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        //  ll_yiZhuManager.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent();
                intent.setClass(MyTaskActivity.this, MyPationteActivity.class);
                startActivityForResult(intent, REQUST_CODE_PATIONTEMSG);
                break;
            case R.id.ll_nurseHistory:
                Intent NurseHistoryActivityIntent = new Intent(this, NurseHistoryActivity.class);
                if (transName == null || transPationteNo == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                } else {
                    NurseHistoryActivityIntent.putExtra("Name", transName);
                    NurseHistoryActivityIntent.putExtra("patientsNo", transPationteNo);
                    startActivity(NurseHistoryActivityIntent);
                }
                break;
            case R.id.ll_nurseStage:
                Intent NurseExecuteActivityIntent = new Intent(this, NurseSelectStageActivity.class);
                /*if (name == null || patientsNo == null || cpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                }
                if(cpwCode.equals("")){
                    MyToast.showToast(this, "此患者还没有配置路径");
                    return;
                }else {
                    NurseExecuteActivityIntent.putExtra("cpwCode", cpwCode);
                    NurseExecuteActivityIntent.putExtra("patientsNo", patientsNo);
                    startActivity(NurseExecuteActivityIntent);
                }*/
                // Intent tv_nursingContentStageIntent = new Intent(this, NursingContentParentStageActivity.class);
                if (transCpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                }
                if (transCpwCode.equals("")) {
                    MyToast.showToast(this, "此患者还没有配置路径");
                    return;
                } else {
                    NurseExecuteActivityIntent.putExtra("cpwCode", transCpwCode);
                    NurseExecuteActivityIntent.putExtra("patientsNo", transPationteNo);
                    startActivity(NurseExecuteActivityIntent);
                }
                break;
            case R.id.ll_addNursingCare:
                startActivity(new Intent(this, AddNursingCareActivity.class));
                MyToast.showToast(MyTaskActivity.this, "此功能正在完善中...");
                break;

            case R.id.ll_nursingContentStage:
                Intent tv_nursingContentStageIntent = new Intent(this, NursingContentParentStageActivity.class);
                if (transCpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                }
                if (transCpwCode.equals("")) {
                    MyToast.showToast(this, "此患者还没有配置路径");
                    return;
                } else {
                    tv_nursingContentStageIntent.putExtra("cpwCode", transCpwCode);
                    tv_nursingContentStageIntent.putExtra("patientsNo", transPationteNo);
                    startActivity(tv_nursingContentStageIntent);
                }
                break;
           /* case R.id.tv_nursingContent_executed:
                startActivity(new Intent(this,ExistingNursingContentActivity.class));
                break;*/
            case R.id.bt_titlebar_left:
                finish();
                break;
            case R.id.iv_search:
                Intent openCameraIntent = new Intent(MyTaskActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
          /*  case R.id.ll_YiZhuManager:
                Intent YiZhuManagerActivityIntent = new Intent(this, DoctorYiZhuManagerActivity.class);
                if (name == null || patientsNo == null || cpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                } else {
                    YiZhuManagerActivityIntent.putExtra("cpwCode", cpwCode);
                    YiZhuManagerActivityIntent.putExtra("patientsNo", patientsNo);
                    startActivity(YiZhuManagerActivityIntent);
                }
                break;*/
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUST_CODE_PATIONTEMSG:
                if (data!=null){
                    name = data.getStringExtra("Name");
                    patientsNo = data.getStringExtra("patientsNo");
                    deptCode = data.getStringExtra("deptCode");
                    deptName = data.getStringExtra("deptName");
                    cpwCode=data.getStringExtra("cpwCode");

                    transCpwCode=cpwCode;
                    transName=name;
                    transPationteNo=patientsNo;
                    transDeptName=deptName;
                    transDeptCode=deptCode;



                    SpPationteMsgConfig.edit().putString("patientsNo", transPationteNo).commit();
                    SpPationteMsgConfig.edit().putString("Name", transName).commit();
                    SpPationteMsgConfig.edit().putString("deptCode", transDeptCode).commit();
                    SpPationteMsgConfig.edit().putString("deptName", transDeptName).commit();


                    MyLog.i("cpwCode", transCpwCode);
                    tv_search.setText("姓名：" + transName + "    患者编号：" + transPationteNo);
                }


                break;
            case REQUST_CODE_SCANBAR:
                if (resultCode == -1) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        // 结果
                        codeinformation = bundle.getString("result");
                        MyToast.showToast(MyTaskActivity.this, codeinformation);
                        getPationteName();
                    } else {
                        MyToast.showToast(MyTaskActivity.this, "没有结果");
                    }
                }
                break;
            default:
                break;
        }
    }

    private void getPationteName() {
        OkHttpUtils.get().url(MyConstant.BASE_URL + MyConstant.PATIONTE_SINGLE + codeinformation).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                // dismissWaitingDialog();
                //  String result = response.body().string();


                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(MyTaskActivity.this, "请检查网络设置或稍后再试");

            }

            @Override
            public void onResponse(Object o, int i) {
                parseSinglePationteJson((String) o);
            }
        });
    }

    private void parseSinglePationteJson(String o) {
        ArrayList<PationteBean> pationteSingle = new Gson().fromJson(o, new TypeToken<List<PationteBean>>() {
        }.getType());
        if (pationteSingle.size() > 0) {
            for (int i = 0; i < pationteSingle.size(); i++) {
                String name = pationteSingle.get(i).Name;
                String cpwCode = pationteSingle.get(i).CPWCode;
                String deptCode = pationteSingle.get(i).DeptCode;
                String deptName = pationteSingle.get(i).DeptName;

                transName=name;
                transPationteNo=codeinformation;
                transCpwCode=cpwCode;
                transDeptCode=deptCode;
                transDeptName=deptName;
                tv_search.setText("姓名: " + transName + "  编号: " + codeinformation);
                MyLog.i("codeinformation::", codeinformation);
            }
        } else {
            tv_search.setText(codeinformation);
        }
    }
}
