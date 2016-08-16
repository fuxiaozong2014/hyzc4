package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseExecuteBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/16.
 */
public class NursingContentStageActivity extends BaseActivity{

    private String cpwCode;
    private List<String> nurseExecuteStages=new ArrayList<>();
    private List<NurseExecuteBean.RowsBean> rows;
    private NurseExecuteBean nurseExecuteBean;
    private LinearLayout ll_parent;

    @Override
    public View setMainView() {
        View view=View.inflate(context, R.layout.activity_nursingcontentstage,null);
        ll_parent = (LinearLayout)view.findViewById(R.id.ll_parent);
        return view;
    }

    @Override
    public void InitData() {
        Intent intent = getIntent();
        if (intent!=null){
            cpwCode = intent.getStringExtra("cpwCode");
            getNurseStageData();
        }

    }

    private void getNurseStageData() {

        MyLog.i("NURSE_STAGE+cpwCode:::::::::", MyConstant.NURSE_SELECT_STAGE + cpwCode);
        OkHttpUtils.get().url(MyConstant.NURSE_SELECT_STAGE + cpwCode).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyLog.i("请求失败", e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {
                parseJson((String) o);
            }
        });
    }



    private void parseJson(String o) {
        Gson gson = new Gson();
        nurseExecuteBean = gson.fromJson(o, NurseExecuteBean.class);
        rows = nurseExecuteBean.getRows();
        for (int i = 0; i <rows.size() ; i++) {
            String stageName = rows.get(i).StageName;

            String parentCode = rows.get(i).ParentCode;
            if (parentCode.equals("0")){
                nurseExecuteStages.add(stageName);
            }
        }

    }
}
