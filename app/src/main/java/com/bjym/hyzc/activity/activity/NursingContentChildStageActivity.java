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
import com.bjym.hyzc.activity.bean.NursingContentBean;
import com.bjym.hyzc.activity.utils.MyLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/8/17.
 */
public class NursingContentChildStageActivity extends BaseActivity {
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private ListView lv;
    private List<NurseExecuteBean.RowsBean> childItemLists;
    private List<NursingContentBean.RowsBean> rowsSun;
    private List<NursingContentBean.RowsBean> rowsSunItemLists=new ArrayList<>();


    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //遍历childItemLists得到相对应子阶段的stagecode
            //遍历rowsSun得到所有条目的stagecode
            //比对，如果两者相等，就把这类对象保存到对应集合，把集合传递到展示界面
            String stageCodeChild = childItemLists.get(position).StageCode;
            for (int i = 0; i <rowsSun.size() ; i++) {
                String stageCodeSun = rowsSun.get(i).StageCode;
                if (stageCodeChild.equals(stageCodeSun)){
                    rowsSunItemLists.add(rowsSun.get(i));
                }
            }
            Intent intent=new Intent(NursingContentChildStageActivity.this,NursingContentSelectChildActivity.class);
            intent.putExtra("rowsSunItemLists",(Serializable) rowsSunItemLists);
            startActivity(intent);
            rowsSunItemLists.clear();
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
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("选择子路径阶段");
        bt_titlebar_left.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            childItemLists = (List<NurseExecuteBean.RowsBean>) intent.getSerializableExtra("childItemLists");
            MyLog.i("childItemLists.size();:::::", childItemLists.size() + "");
            rowsSun = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("rowsSun");
        }

        lv.setAdapter(new MyAdapter());


        lv.setOnItemClickListener(new MyOnItemClickListener());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return childItemLists.size();
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
            tv_NurseChildStage.setText(childItemLists.get(position).StageName);
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
