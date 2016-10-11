package com.bjym.hyzc.activity.ExecuteNursingCare;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.activity.BaseActivity;

import java.util.List;

/**
 * Created by fushaoqing on 2016/8/9.
 */
public class ExistingNursingContentActivity extends BaseActivity {
    private ListView lv;
    private List<NursingContentBean.RowsBean> existingNursingContents;
    private TextView tv_none_nurseContent;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_existingnursingcontent, null);
        lv = (ListView) view.findViewById(R.id.lv);
        tv_none_nurseContent = (TextView) view.findViewById(R.id.tv_none_nurseContent);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);
        return view;
    }

    @Override
    public void InitData() {
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("已执行的护理内容");
        bt_titlebar_left.setOnClickListener(this);

        existingNursingContents = (List<NursingContentBean.RowsBean>) getIntent().getSerializableExtra("existingNursingContents");

        if (existingNursingContents!=null) {
            lv.setAdapter(new MyAdapter());
        }else{
            lv.setVisibility(View.GONE);
            tv_none_nurseContent.setVisibility(View.VISIBLE);
            return;
        }

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return existingNursingContents.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder vh = null;
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_list_existingnursingcontent, null);
                vh = new ViewHolder(view);
                view.setTag(vh);
            } else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }
            vh.tv_ContentName.setText(existingNursingContents.get(position).ContentName);
            vh.tv_ContentCode.setText(existingNursingContents.get(position).ContentCode);
            vh.tv_StageCode.setText(existingNursingContents.get(position).StageCode);
            vh.tv_ContentType.setText(existingNursingContents.get(position).ContentType);
            vh.tv_CPWType.setText(existingNursingContents.get(position).CPWType);
            vh.tv_OrderType.setText(existingNursingContents.get(position).OrderType);
            vh.tv_OrderCategory.setText(existingNursingContents.get(position).OrderCategory);
            vh.tv_CPWCode.setText(existingNursingContents.get(position).CPWCode);
            vh.tv_MedicalRecord.setText(existingNursingContents.get(position).MedicalRecord);
            vh.tv_ActivitiesType.setText(existingNursingContents.get(position).ActivitiesType);
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
