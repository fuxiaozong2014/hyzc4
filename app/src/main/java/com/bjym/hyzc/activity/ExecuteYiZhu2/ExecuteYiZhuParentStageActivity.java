package com.bjym.hyzc.activity.ExecuteYiZhu2;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.ExecuteNursingCare.NurseExecuteBean;
import com.bjym.hyzc.activity.activity.BaseActivity;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/10/11.
 */

public class ExecuteYiZhuParentStageActivity extends BaseActivity {
    private String cpwCode;
    private ListView lv;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private String patientsNo;

    private List<String> parentStageNames = new ArrayList<>();
    /*
    * 一级径阶段集合
    * */
    private List<NurseExecuteBean.RowsBean> parentStageLists = new ArrayList<>();
    /*
    * 二级和三级路径阶段集合
    * */
    private List<NurseExecuteBean.RowsBean> childAndSunStageLists = new ArrayList<>();
    /*
    * 二级径阶段集合
    * */
    private List<NurseExecuteBean.RowsBean> childStageLists = new ArrayList<>();

    private List<NurseExecuteBean.RowsBean> rows;//所有路径阶段对象所在的集合


    private NurseExecuteBean nurseExecuteBean;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {


        private String stageCode;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //遍历父集合拿到stageCode
            //遍历子集合拿到parentcode
            //如果stageCode.equals（parentcode） ，拿到这类子集合
            //这个对象集合传递到子界面listview，展示
            stageCode = parentStageLists.get(position).StageCode;//拿到被点击的父亲的stageCode
            MyLog.i("parentStageLists:stageCode", this.stageCode);
            //点击过后判断有没有二级数据，有的话就传递，没有的就请求数据了

            for (int i = 0; i < childAndSunStageLists.size(); i++) {
                String parentCode = childAndSunStageLists.get(i).ParentCode;
                if (parentCode.equals(stageCode)) {//说明有二级数据，
                    childStageLists.add(childAndSunStageLists.get(i));//二级阶段数据存放的集合
                }
            }

            if (childStageLists!=null&&childStageLists.size()>0){//说明有二级数据
                Intent intent = new Intent(context, ExecuteYiZhuChildStageActivity.class);
                //传递孙数据给子阶段
                intent.putExtra("rows", (Serializable) rows);
                intent.putExtra("childAndSunStageLists", (Serializable) childAndSunStageLists);
                intent.putExtra("childStageLists", (Serializable) childStageLists);
                intent.putExtra("patientsNo", patientsNo);
                startActivity(intent);
            }else {//根据pationteNo和StageCode请求已执行和未执行的医嘱，把得到的数据传递给NurseYiZhuAcitivity


            }


        }
    }

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursingcontentparentstage, null);
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
        tv_titlebar_center.setText("选择一级路径阶段");
        bt_titlebar_left.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            cpwCode = intent.getStringExtra("cpwCode");
            patientsNo = intent.getStringExtra("patientsNo");
            getNurseStageData();

        }
    }

    /*
   * 得到路径阶段信息
   * */
    private void getNurseStageData() {

        MyLog.i("得到所有路径阶段的接口：", MyConstant.NURSE_SELECT_STAGE + cpwCode);
        OkHttpUtils.get().url(MyConstant.BASE_URL + MyConstant.NURSE_SELECT_STAGE + cpwCode).build().execute(new Callback() {
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
        //  MyLog.i("得到所有的路径阶段",o);
        Gson gson = new Gson();
        nurseExecuteBean = gson.fromJson(o, NurseExecuteBean.class);
        rows = nurseExecuteBean.getRows();
        for (int i = 0; i < rows.size(); i++) {
            String stageName = rows.get(i).StageName;
            String stageCode = rows.get(i).StageCode;
            String parentCode = rows.get(i).ParentCode;

            /*
            * 如果parentCode为0，拿到阶段名称添加到
            * */
            if (parentCode.equals("0")) {
                parentStageNames.add(stageName);//添加的是所有的一级阶段名称

            } else {
                childAndSunStageLists.add(rows.get(i));//添加的是剩余的二三级路径阶段名称
            }
        }

        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new MyOnItemClickListener());
    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return parentStageNames.size();
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
            tv_NurseStage.setText(parentStageNames.get(position));
            return view;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }

}
