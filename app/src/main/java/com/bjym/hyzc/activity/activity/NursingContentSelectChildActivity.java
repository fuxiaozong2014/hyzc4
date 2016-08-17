package com.bjym.hyzc.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
    private List<NursingContentBean.RowsBean> rowsSunItemLists;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("确定执行此护理内容？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NursingContentBean.RowsBean rowsBean = rows.get(position);
                    if (existingNursingContents == null) {
                        existingNursingContents = new ArrayList<>();
                    }
                    //把得到的集合传递给下一个现有页面
                    existingNursingContents.add(rowsBean);
                    MyLog.i("existingNursingContents", existingNursingContents.size() + "");

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

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_nursingcontentselect, null);
        tv_none_nurseContent = (TextView) view.findViewById(R.id.tv_none_nurseContent);
        tv_search = (TextView) view.findViewById(R.id.tv_search);

        lv = (ListView) view.findViewById(R.id.lv);

        return view;
    }

    @Override
    public void InitData() {
        Intent intent = getIntent();
        //TODO 得到shu数据
        rowsSunItemLists = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("rowsSunItemLists");
        MyLog.i("rowsSunItemLists",rowsSunItemLists.size()+"");
        lv.setOnItemClickListener(new MyOnItemClickListener());
        lv.setAdapter(new MyAdapter());
        tv_search.setOnClickListener(this);

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
        }
    }




    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return rowsSunItemLists.size();
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
            vh.tv_ContentName.setText(rowsSunItemLists.get(position).ContentName);
            vh.tv_ContentCode.setText(rowsSunItemLists.get(position).ContentCode);
            vh.tv_StageCode.setText(rowsSunItemLists.get(position).StageCode);
            vh.tv_ContentType.setText(rowsSunItemLists.get(position).ContentType);
            vh.tv_CPWType.setText(rowsSunItemLists.get(position).CPWType);
            vh.tv_OrderType.setText(rowsSunItemLists.get(position).OrderType);
            vh.tv_OrderCategory.setText(rowsSunItemLists.get(position).OrderCategory);
            vh.tv_CPWCode.setText(rowsSunItemLists.get(position).CPWCode);
            vh.tv_MedicalRecord.setText(rowsSunItemLists.get(position).MedicalRecord);
            vh.tv_ActivitiesType.setText(rowsSunItemLists.get(position).ActivitiesType);
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
