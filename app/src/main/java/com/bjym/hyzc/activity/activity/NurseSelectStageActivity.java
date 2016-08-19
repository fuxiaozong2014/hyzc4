package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseExecuteBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/3.
 */
public class NurseSelectStageActivity extends BaseActivity {

    private List<String> nurseStages=new ArrayList<>();
    private String cpwCode;
    private ListView lv;
    private List<NurseExecuteBean.RowsBean> rows;
    private NurseExecuteBean nurseExecuteBean;
    private String patientsNo;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //click this item jump to NurseUnxecuteYiZhuActivity with StageCode

            if(rows.get(position).StageCode==null){
                MyToast.showToast(NurseSelectStageActivity.this,"此患者还没有配置路径");
            }else {
                Intent intent=new Intent(context,NurseUnxecuteYiZhuActivity.class);
                intent.putExtra("StageCode",rows.get(position).StageCode);
                intent.putExtra("patientsNo",patientsNo);
                startActivity(intent);
            }

          //  MyToast.showToast(NurseSelectStageActivity.this,"wobeidianleffff");
        }
    }

    @Override
    public View setMainView() {
        View view = View.inflate(this, R.layout.activity_nurseselectstage, null);
        lv = (ListView) view.findViewById(R.id.lv);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("选择路径阶段");
        Intent intent = getIntent();
        cpwCode = intent.getStringExtra("cpwCode");
        patientsNo = intent.getStringExtra("patientsNo");
        MyLog.i("NurseExcode", cpwCode);

        getNurseStageData();

        lv.setOnItemClickListener(new MyOnItemClickListener());
        bt_titlebar_left.setOnClickListener(this);

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
                nurseStages.add(stageName);
            }

        }

        //得到数据之后设置适配器
        lv.setAdapter(new MyAdapter());
    }


    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return nurseStages.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_nursexecuteyizhu, null);

            } else {
                view = convertView;
            }
            TextView tv_NurseStage = (TextView) view.findViewById(R.id.tv_NurseStage);
            tv_NurseStage.setText(nurseStages.get(position));
            return view;
        }
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
