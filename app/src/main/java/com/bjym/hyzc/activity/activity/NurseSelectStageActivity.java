package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
 * Created by fushaoqing on 2016/8/3.
 */
public class NurseSelectStageActivity extends BaseActivity {

    private List<String> nurseExecuteStage=new ArrayList<>();
    private String cpwCode;
    private ListView lv;
    private List<NurseExecuteBean.RowsBean> rows;
    private NurseExecuteBean nurseExecuteBean;
    private String patientsNo;


    class MyOnItemClickListener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //click this item jump to NurseUnxecuteYiZhuActivitye with StageCode
                Intent intent=new Intent(context,NurseUnxecuteYiZhuActivitye.class);
                intent.putExtra("StageCode",rows.get(position).StageCode);
                intent.putExtra("patientsNo",patientsNo);
                startActivity(intent);
          //  MyToast.showToast(NurseSelectStageActivity.this,"wobeidianleffff");
        }
    }

    @Override
    public View setMainView() {
        View view = View.inflate(this, R.layout.activity_nurseselectstage, null);
        lv = (ListView) view.findViewById(R.id.lv);
        return view;
    }

    @Override
    public void InitData() {
        Intent intent = getIntent();
        cpwCode = intent.getStringExtra("cpwCode");
        patientsNo = intent.getStringExtra("patientsNo");
        MyLog.i("NurseExcode", cpwCode);

        getNurseStageData();

        lv.setOnItemClickListener(new MyOnItemClickListener());


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
                nurseExecuteStage.add(stageName);
            }

            String stageCode = rows.get(i).StageCode;
           // MyLog.i("stageCode",stageCode);
        }

        //得到数据之后设置适配器
        lv.setAdapter(new MyAdapter());
    }


    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return nurseExecuteStage.size();
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
            TextView tv_NurseExecuteYiZhu = (TextView) view.findViewById(R.id.tv_NurseExecuteYiZhu);
             tv_NurseExecuteYiZhu.setText(nurseExecuteStage.get(position));
            return view;
        }
    }



}
