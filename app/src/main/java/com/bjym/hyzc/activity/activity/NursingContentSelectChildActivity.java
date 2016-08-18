package com.bjym.hyzc.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NursingContentBean;
import com.bjym.hyzc.activity.utils.MyLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushaoqing on 2016/8/9.
 */
public class NursingContentSelectChildActivity extends BaseActivity {
    private ListView lv;
    private String cpwCode;
    private List<NursingContentBean.RowsBean> rows;
    private List<NursingContentBean.RowsBean> existingNursingContents = null;
    private TextView tv_none_nurseContent;
    private TextView tv_search;
    private List<NursingContentBean.RowsBean> sunItemLists;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("确定执行此护理内容？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NursingContentBean.RowsBean rowsBean = sunItemLists.get(position);
                    if (existingNursingContents == null) {
                        existingNursingContents = new ArrayList<>();
                    }
                    //把得到的集合传递给下一个现有页面
                    existingNursingContents.add(rowsBean);
                    MyLog.i("existingNursingContents", existingNursingContents.size() + "");
                    postUnexecuContent(rowsBean);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            builder.show();

        }
    }

    private void postUnexecuContent(NursingContentBean.RowsBean rowsBean) {


    }

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursingcontentselect, null);
        tv_none_nurseContent = (TextView) view.findViewById(R.id.tv_none_nurseContent);
        tv_search = (TextView) view.findViewById(R.id.tv_search);

        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        lv = (ListView) view.findViewById(R.id.lv);

        return view;
    }

    @Override
    public void InitData() {

        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("维护患者护理内容");

        Intent intent = getIntent();
        sunItemLists = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("rowsSunItemLists");
        MyLog.i("sunItemLists",sunItemLists.size()+"");

        lv.setOnItemClickListener(new MyOnItemClickListener());
        lv.setAdapter(new MyAdapter());
        tv_search.setOnClickListener(this);
        bt_titlebar_left.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                Intent existingNursingContentIntent = new Intent(this, ExistingNursingContentActivity.class);
                existingNursingContentIntent.putExtra("existingNursingContents", (Serializable) existingNursingContents);
                startActivity(existingNursingContentIntent);
                break;
            case R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }




    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return sunItemLists.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder vh = null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_nursecontent_select, null);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }
            vh.tv_ContentName.setText(sunItemLists.get(position).ContentName);
            vh.tv_ContentType.setText(sunItemLists.get(position).ContentType);
            vh.tv_OrderType.setText(sunItemLists.get(position).OrderType);
            vh.tv_OrderCategory.setText(sunItemLists.get(position).OrderCategory);
            vh.tv_MedicalRecord.setText(sunItemLists.get(position).MedicalRecord);
            vh.tv_ActivitiesType.setText(sunItemLists.get(position).ActivitiesType);
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


        public class ViewHolder {
            public View rootView;
            public TextView tv_ContentName;
            public TextView tv_ContentCode;
            public TextView tv_StageCode;
            public TextView tv_ContentType;
            public TextView tv_CPWType;
            public TextView tv_OrderType;
            public TextView tv_OrderCategory;
            public TextView tv_CPWCode;
            public TextView tv_ActivitiesType;
            public TextView tv_MedicalRecord;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_ContentName = (TextView) rootView.findViewById(R.id.tv_ContentName);
                this.tv_ContentCode = (TextView) rootView.findViewById(R.id.tv_ContentCode);
                this.tv_StageCode = (TextView) rootView.findViewById(R.id.tv_StageCode);
                this.tv_ContentType = (TextView) rootView.findViewById(R.id.tv_ContentType);
                this.tv_CPWType = (TextView) rootView.findViewById(R.id.tv_CPWType);
                this.tv_OrderType = (TextView) rootView.findViewById(R.id.tv_OrderType);
                this.tv_OrderCategory = (TextView) rootView.findViewById(R.id.tv_OrderCategory);
                this.tv_CPWCode = (TextView) rootView.findViewById(R.id.tv_CPWCode);
                this.tv_ActivitiesType = (TextView) rootView.findViewById(R.id.tv_ActivitiesType);
                this.tv_MedicalRecord = (TextView) rootView.findViewById(R.id.tv_MedicalRecord);
            }

        }
    }
}
