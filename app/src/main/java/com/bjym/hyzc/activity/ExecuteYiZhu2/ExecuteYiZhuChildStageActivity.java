package com.bjym.hyzc.activity.ExecuteYiZhu2;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.ExecuteNursingCare.NurseExecuteBean;
import com.bjym.hyzc.activity.ExecuteNursingCare.NursingContentBean;
import com.bjym.hyzc.activity.activity.BaseActivity;
import com.bjym.hyzc.activity.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/10/11.
 */

public class ExecuteYiZhuChildStageActivity extends BaseActivity {
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private ListView lv;
    private List<NurseExecuteBean.RowsBean> childAndSunStageLists;
    private List<NurseExecuteBean.RowsBean> childStageLists;
    private List<NursingContentBean.RowsBean> childStageListsItemLists=new ArrayList<>();
    private List<NurseExecuteBean.RowsBean> sunStageItemList=new ArrayList<>();
    private String patientsNo;
    private List<NurseExecuteBean.RowsBean> rows;
    /*class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //childAndSunStageLists得到相对应子阶段的stagecode
            //遍历childStageLists得到所有条目的stagecode
            //比对，如果两者相等，就把这类对象保存到对应集合，把集合传递到展示界面
            String stageCodeChild = childAndSunStageLists.get(position).StageCode;
            MyLog.i("被点击的阶段的stageCode",stageCodeChild);
            // MyLog.i("-----------------------stageCodeChild",stageCodeChild);
            if (childStageLists !=null) {
                for (int i = 0; i <childStageLists.size() ; i++) {
                    String stageCodeSun = childStageLists.get(i).StageCode;
                    // MyLog.i("-----------------------stageCodeSun",stageCodeSun);
                    if (stageCodeChild.equals(stageCodeSun)){
                        childStageListsItemLists.add(childStageLists.get(i));
                    }
                }
            }
            for (int i = 0; i <rows.size() ; i++) {
                String parentCode = rows.get(i).ParentCode;
                if (parentCode.equals(stageCodeChild)){
                    sunStageItemList.add(rows.get(i));
                }
            }
            for (int i = 0; i <sunStageItemList.size() ; i++) {
                MyLog.i("三级数据阶段对象",sunStageItemList.get(i).StageCode+":"+sunStageItemList.get(i).StageName);
            }

            if (sunStageItemList!=null&&sunStageItemList.size()>0){
                Intent intentNursingContentSunStage=new Intent(ExecuteYiZhuChildStageActivity.this,NursingContentSunStageActivity.class);
                intentNursingContentSunStage.putExtra("sunStageItemList",(Serializable)sunStageItemList);
                intentNursingContentSunStage.putExtra("childStageLists",(Serializable)childStageLists);
                intentNursingContentSunStage.putExtra("patientsNo",patientsNo);
                startActivity(intentNursingContentSunStage);
                sunStageItemList.clear();
            }else {
                Intent intent=new Intent(ExecuteYiZhuChildStageActivity.this,NurseContentActivity.class);
                intent.putExtra("childStageListsItemLists",(Serializable) childStageListsItemLists);
                intent.putExtra("stageCodeChild",stageCodeChild);
                intent.putExtra("patientsNo",patientsNo);
                startActivity(intent);
                childStageListsItemLists.clear();
            }
        }
    }*/
    @Override
    public View setMainView() {

        View view = View.inflate(context, R.layout.activity_nursingcontentchildstage, null);
        lv = (ListView) view.findViewById(R.id.lv);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        return view;
    }

    @Override
    public void InitData() {

        //这是顶部状态栏的设置
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("选择二级路径阶段");
        bt_titlebar_left.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            childAndSunStageLists = (List<NurseExecuteBean.RowsBean>) intent.getSerializableExtra("childAndSunStageLists");
            MyLog.i("childAndSunStageLists.size();:::::", childAndSunStageLists.size() + "");
            childStageLists = (List<NurseExecuteBean.RowsBean>) intent.getSerializableExtra("childStageLists");
            patientsNo = intent.getStringExtra("patientsNo");
            rows = (List<NurseExecuteBean.RowsBean>) intent.getSerializableExtra("rows");

        }

        lv.setAdapter(new MyAdapter());
       // lv.setOnItemClickListener(new MyOnItemClickListener());

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return childStageLists.size();
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
            tv_NurseChildStage.setText(childStageLists.get(position).StageName);
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
