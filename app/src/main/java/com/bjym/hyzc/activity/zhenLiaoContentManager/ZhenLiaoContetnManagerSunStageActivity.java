package com.bjym.hyzc.activity.zhenLiaoContentManager;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.BaseActivity;
import com.bjym.hyzc.activity.executeNursingCare.NurseExecuteBean;
import com.bjym.hyzc.activity.executeNursingCare.NursingContentBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by fushaoqing on 2016/10/10.
 */

public class ZhenLiaoContetnManagerSunStageActivity extends BaseActivity {
    private ListView lv;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private List<NurseExecuteBean.RowsBean> sunStageItemList;
    private String patientsNo;
    private List<NursingContentBean.RowsBean> rowsSun;//所有未执行的护理内容集合
    private List<NursingContentBean.RowsBean> sunStageUnExecuteNursingCare=new ArrayList<>();//这是三级阶段对应的未执行的护理内容集合

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //点击条目跳转到NurseContent界面，传递此阶段的未执行和已执行的护理内容集合过去
            //如果被点击的阶段的stageCode相等于rowsSun集合的stageCode，就把这个stageCode对应的未执行的护理内容对象保存到集合中，传递给NurseContentActivity
            String stageCodeSun = sunStageItemList.get(position).StageCode;
            for (int i = 0; i <rowsSun.size() ; i++) {
                String stageCodeAll = rowsSun.get(i).StageCode;
                if (stageCodeSun.equals(stageCodeAll)){
                    sunStageUnExecuteNursingCare.add(rowsSun.get(i));
                }
            }
            Intent intent=new Intent(ZhenLiaoContetnManagerSunStageActivity.this,DoctorYiZhuActivity.class);
            intent.putExtra("patientsNo", patientsNo);
            intent.putExtra("sunStageUnExecuteNursingCare",(Serializable) sunStageUnExecuteNursingCare);
            intent.putExtra("stageCodeSun",stageCodeSun);
            startActivity(intent);
            sunStageUnExecuteNursingCare.clear();
        }
    }

    @Override
    public View setMainView() {
        View view=View.inflate(context, R.layout.activity_nursingcontentparentstage,null);
        lv =  (ListView) view.findViewById(R.id.lv);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        return view;
    }

    @Override
    public void InitData() {
        tv_titlebar_center.setText("选择三级路径阶段");
        bt_titlebar_left.setOnClickListener(this);
        bt_titlebar_right.setVisibility(View.GONE);
        Intent intent=getIntent();
        sunStageItemList = (List<NurseExecuteBean.RowsBean>) intent.getSerializableExtra("sunStageItemList");
        rowsSun = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("rowsSun");
        patientsNo = intent.getStringExtra("patientsNo");

        //三级阶段需要有点击事件，点击过后跳转到NurseCintent界面，

        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new MyOnItemClickListener());


    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return sunStageItemList.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_nursingcontent_childstage, null);
            } else {
                view = convertView;
            }
            TextView tv_NurseChildStage = (TextView) view.findViewById(R.id.tv_NurseChildStage);
            tv_NurseChildStage.setText(sunStageItemList.get(position).StageName);
            return view;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
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
