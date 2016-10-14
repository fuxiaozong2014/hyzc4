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
import com.bjym.hyzc.activity.zhenLiaoContentManager.ZhenLiaoContetnManageParentStageActivity;
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
public class DoctorTaskActivity extends BaseActivity {

    private static final int REQUST_CODE_PATIONTEMSG = 1;
    private static final int REQUST_CODE_SCANBAR = 0;
    private TextView tv_search;
    private ImageView iv_search;
    private String codeinformation;
    private LinearLayout ll_addYizhu;
    private LinearLayout ll_ZhenliaoContentManager;
    private String name;
    private String patientsNo;
    private String cpwCode;
    private String deptName;
    private String deptCode;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private LinearLayout ll_yiZhuManager;
    private SharedPreferences sp;

    private String transName;//传递给surveyActivity的调查人名字，用于传递过去，调查结果的提交
    private String transPationteNo;//传递给surveyActivity的调查人编号，用于传递过去，调查结果的提交
    private String transCpwCode;//传递给surveyActivity的调查人路径编码，用于传递过去，调查结果的提交
    private String transDeptName;
    private String transDeptCode;
    @Override
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_doctortask, null);
        ll_yiZhuManager = (LinearLayout) view.findViewById(R.id.ll_YiZhuManager);

        tv_search = (TextView) view.findViewById(R.id.tv_search);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        ll_addYizhu = (LinearLayout) view.findViewById(R.id.ll_addYizhu);
        ll_ZhenliaoContentManager = (LinearLayout) view.findViewById(R.id.ll_ZhenliaoContentManager);


        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {
        sp = getSharedPreferences("PationteMsgConfig", MODE_PRIVATE);
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("我的任务");
        tv_search.setOnClickListener(this);
        iv_search.setOnClickListener(this);
      /*  tv_nurseHistory.setOnClickListener(this);
        tv_nurseStage.setOnClickListener(this);
       // tv_nursingContent_executed.setOnClickListener(this);
//        tv_addNursingCare.setOnClickListener(this);
        tv_nursingContentStage.setOnClickListener(this);*/
        bt_titlebar_left.setOnClickListener(this);
        ll_yiZhuManager.setOnClickListener(this);
        ll_addYizhu.setOnClickListener(this);
        ll_ZhenliaoContentManager.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent();
                intent.setClass(DoctorTaskActivity.this, MyPationteActivity.class);
                startActivityForResult(intent, REQUST_CODE_PATIONTEMSG);
                break;
            case R.id.ll_YiZhuManager:
               /* Intent YiZhuManagerActivityIntent = new Intent(this, DoctorYiZhuManagerActivity.class);
                if (transCpwCode == null) {
                    MyToast.showToast(this, "请选择患者");
                    return;
                }
                if (transCpwCode.equals("")) {
                    MyToast.showToast(this, "此患者还没有配置路径");
                    return;
                } else {
                    YiZhuManagerActivityIntent.putExtra("cpwCode", transCpwCode);
                    YiZhuManagerActivityIntent.putExtra("patientsNo", transCpwCode);
                    startActivity(YiZhuManagerActivityIntent);
                }*/
                MyToast.showToast(DoctorTaskActivity.this, "此功能正在研发中，敬请期待！！！");
                break;
            case R.id.ll_addYizhu:
                MyToast.showToast(DoctorTaskActivity.this, "此功能正在研发中，敬请期待！！！");
                break;
            case R.id.ll_ZhenliaoContentManager://诊疗内容管理和执行护理的接口一致，只是在未执行界面，“执行护理”提交时接口不同
                Intent tv_nursingContentStageIntent = new Intent(this, ZhenLiaoContetnManageParentStageActivity.class);
                // Intent tv_nursingContentStageIntent = new Intent(this, NursingContentParentStageActivity2.class);
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
            case R.id.bt_titlebar_left:
                finish();
                break;
            case R.id.iv_search:
                Intent openCameraIntent = new Intent(DoctorTaskActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            default:
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUST_CODE_PATIONTEMSG:
                if (data!= null) {
                    name = data.getStringExtra("Name");
                    patientsNo = data.getStringExtra("patientsNo");
                    cpwCode = data.getStringExtra("cpwCode");
                    deptCode = data.getStringExtra("deptCode");
                    deptName = data.getStringExtra("deptName");

                    transCpwCode=cpwCode;
                    transName=name;
                    transPationteNo=patientsNo;
                    transDeptName=deptName;
                    transDeptCode=deptCode;

                    sp.edit().putString("patientsNo", transPationteNo).commit();
                    sp.edit().putString("name", transName).commit();
                    sp.edit().putString("deptCode", transDeptCode).commit();
                    sp.edit().putString("deptName", transDeptName).commit();
                    MyLog.i("cpwCode", transCpwCode);
                    tv_search.setText("姓名：" + transName + "   编号：" + transPationteNo);
                }


                break;
            case REQUST_CODE_SCANBAR:
                if (resultCode == -1) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        // 结果
                        codeinformation = bundle.getString("result");
                        MyToast.showToast(DoctorTaskActivity.this, codeinformation);
                        getPationteName();

                    } else {
                        MyToast.showToast(DoctorTaskActivity.this, "没有结果");
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
                MyToast.showToast(DoctorTaskActivity.this, "请检查网络设置或稍后再试");

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
            transName=null;
            MyToast.showToast(DoctorTaskActivity.this,"该患者不存在");
        }
    }
}
