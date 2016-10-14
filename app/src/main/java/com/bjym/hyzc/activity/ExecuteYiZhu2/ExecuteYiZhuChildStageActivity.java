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
import com.bjym.hyzc.activity.executeNursingCare.NurseExecuteBean;
import com.bjym.hyzc.activity.ExecuteYiZhu.NurseYiZhuActivity;
import com.bjym.hyzc.activity.activity.BaseActivity;
import com.bjym.hyzc.activity.utils.MyLog;

import java.io.Serializable;
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
    private List<NurseExecuteBean.RowsBean> childAndSunStageLists;//二级和三级阶段数据对象的集合
    private List<NurseExecuteBean.RowsBean> childStageLists;//二级阶段数据对象的集合
    private List<NurseExecuteBean.RowsBean> sunStageItemList=new ArrayList<>();
    private String patientsNo;
    private List<NurseExecuteBean.RowsBean> allStageLists;//所有的路径阶段集合
    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //childAndSunStageLists得到点击的子阶段的stagecode
            //遍历childStageLists得到所有条目的stagecode
            //比对，如果两者相等，就把这类对象保存到对应集合，把集合传递到展示界面
            String stageCodeChild = childStageLists.get(position).StageCode;//当前被点击的二级数据阶段的stageCode
            MyLog.i("被点击的阶段的stageCode",stageCodeChild);
            if (childAndSunStageLists !=null) {//遍历二三级数据阶段集合，取出所有的parentCode,与二级数据的stageCode相等则为三级数据
                for (int i = 0; i <childAndSunStageLists.size() ; i++) {
                    String parentCode = childAndSunStageLists.get(i).ParentCode;
                    // MyLog.i("-----------------------stageCodeSun",stageCodeSun);
                    if (stageCodeChild.equals(parentCode)){
                        sunStageItemList.add(childAndSunStageLists.get(i));
                    }
                }
            }

            for (int i = 0; i <sunStageItemList.size() ; i++) {
                MyLog.i("三级数据阶段对象",sunStageItemList.get(i).StageCode+":"+sunStageItemList.get(i).StageName);
            }

            if (sunStageItemList!=null&&sunStageItemList.size()>0){//有三级数据阶段的情况下
                Intent intentExecuteYiZhuSunStage=new Intent(context,ExecuteYiZhuSunStageActivity.class);
                intentExecuteYiZhuSunStage.putExtra("sunStageItemList",(Serializable)sunStageItemList);//三级数据对应的集合
                intentExecuteYiZhuSunStage.putExtra("childAndSunStageLists",(Serializable)childAndSunStageLists);//二三级阶段数据对象的集合
                intentExecuteYiZhuSunStage.putExtra("patientsNo",patientsNo);
                startActivity(intentExecuteYiZhuSunStage);
                sunStageItemList.clear();
            }else {//没有三级数据的情况下，传递stageCode和pationteNo到NurseYiZhu
                Intent intent=new Intent(context, NurseYiZhuActivity.class);
                intent.putExtra("patientsNo",patientsNo);
                intent.putExtra("stageCodeChild",stageCodeChild);
                startActivity(intent);
            }
        }
    }
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
            allStageLists = (List<NurseExecuteBean.RowsBean>) intent.getSerializableExtra("allStageLists");//所有路径阶段对象所在的集合

        }

        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new MyOnItemClickListener());

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
