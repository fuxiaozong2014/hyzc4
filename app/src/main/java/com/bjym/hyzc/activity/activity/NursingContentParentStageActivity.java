package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseExecuteBean;
import com.bjym.hyzc.activity.bean.NursingContentBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/16.
 */
public class NursingContentParentStageActivity extends BaseActivity {

    private String cpwCode;
    private List<String> nurseStages = new ArrayList<>();
    private List<NurseExecuteBean.RowsBean> rows;
    private List<NursingContentBean.RowsBean> rowsSun;

    private NurseExecuteBean nurseExecuteBean;
    private LinearLayout ll_parent;
    private List<NurseExecuteBean.RowsBean> parentLists = new ArrayList<>();
    private List<NurseExecuteBean.RowsBean> childLists = new ArrayList<>();
    private List<NurseExecuteBean.RowsBean> childItemLists = new ArrayList<>();
    private List<NursingContentBean.RowsBean> SunItemLists=new ArrayList<>();

    private ListView lv;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {


        private String stageCode;
        private String parentCode;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //遍历父集合拿到stageCode
            //遍历子集合拿到parentcode
            //如果stageCode.equals（parentcode） ，拿到这类子集合
            //这个对象集合传递到子界面listview，展示
            stageCode = parentLists.get(position).StageCode;//拿到被点击的父亲的stageCode
            MyLog.i("parentLists:stageCode", stageCode);


            MyLog.i("childItemLists",childItemLists.toString()+childItemLists.size());

            if (childLists.size()!=0){
                for (int i = 0; i < childLists.size(); i++) { //遍历拿到每个孩子的parentCode，拿到一个比对，如果是，就添加到集合里
                    parentCode = childLists.get(i).ParentCode;
                    if (stageCode.equals(parentCode)) {
                        childItemLists.add(childLists.get(i));
                    }
                }
                Intent intent = new Intent(context, NursingContentChildStageActivity.class);
                intent.putExtra("childItemLists", (Serializable) childItemLists);
                //传递孙数据给子阶段
                intent.putExtra("rowsSun",(Serializable)rowsSun);
                startActivity(intent);
            }else {
                for (int j = 0; j <rowsSun.size() ; j++) {
                    String stageCodeSun = rowsSun.get(j).StageCode;
                    MyLog.i("stageCodeSun：：：", stageCode);
                    if (stageCodeSun.equals(stageCode)){
                        SunItemLists.add(rowsSun.get(j));
                    }
                }
                if(SunItemLists.size()==0){
                    MyToast.showToast(NursingContentParentStageActivity.this,"此阶段暂无护理内容");
                    return;
                }else{
                    MyLog.i("SunItemLists",SunItemLists.toString()+SunItemLists.size());
                    Intent intent = new Intent(context, NursingContentSelectParentActivity.class);
                    intent.putExtra("SunItemLists", (Serializable) SunItemLists);
                    startActivity(intent);
                    // MyToast.showToast(NursingContentParentStageActivity.this,"没有子条目");
                }

            }

            childItemLists.clear();
            SunItemLists.clear();
           //stageCode 和孙数据的stageCode相比，如果相等，就添加到孙集合中，开启NursingContentSelectActivityji界面

        }
    }

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursingcontentparentstage, null);
        ll_parent = (LinearLayout) view.findViewById(R.id.ll_parent);
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
        tv_titlebar_center.setText("选择父路径阶段");

        Intent intent = getIntent();
        if (intent != null) {
            cpwCode = intent.getStringExtra("cpwCode");
            getNurseStageData();
            //TODO 得到孙数据集合 每个对象的stageCode
            getNueseSunData();
        }

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
        for (int i = 0; i < rows.size(); i++) {
            String stageName = rows.get(i).StageName;
            String parentCode = rows.get(i).ParentCode;

            if (parentCode.equals("0")) {
                nurseStages.add(stageName);
                parentLists.add(rows.get(i));
            } else {
                childLists.add(rows.get(i));
            }
        }

        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new MyOnItemClickListener());
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
        switch (v.getId()) {
            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }

    private void getNueseSunData() {
        OkHttpUtils.get().url(MyConstant.NURSING_CONTENT_SELECT + cpwCode).build().execute(new Callback() {

            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                // MyToast.showToast(NursingContentSelectParentActivity.this, "请求成功" + "getNursingContentData");

                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NursingContentParentStageActivity.this, "请求失败");
            }

            @Override
            public void onResponse(Object o, int in) {
                parseMyselfJson((String) o);
            }
        });
    }

    private void parseMyselfJson(String o) {
        NursingContentBean nursingContentBean = new Gson().fromJson(o, NursingContentBean.class);
        rowsSun = nursingContentBean.getRows();
        MyLog.i("rowsSun", rowsSun.size() + "");
    }

}
