package com.bjym.hyzc.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.NurseContentUnexecuteBean;
import com.bjym.hyzc.activity.bean.NursingContentBean;
import com.bjym.hyzc.activity.utils.MyConstant;
import com.bjym.hyzc.activity.utils.MyLog;
import com.bjym.hyzc.activity.utils.MyToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fushaoqing on 2016/8/9.
 */
public class NursingContentSelectParentActivity extends BaseActivity {
    private ListView lv;
    private String cpwCode;
    private List<NursingContentBean.RowsBean> rows;
    private List<NursingContentBean.RowsBean> existingNursingContents = null;
    private TextView tv_none_nurseContent;
    private TextView tv_search;
    private List<NursingContentBean.RowsBean> parentItemLists;

    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private SharedPreferences SpPationteMsgConfig;
    private SharedPreferences sp;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("确定执行此护理内容？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NursingContentBean.RowsBean rowsBean = parentItemLists.get(position);
                    /*if (existingNursingContents == null) {
                        existingNursingContents = new ArrayList<>();
                    }
                    //把得到的集合传递给下一个现有页面
                    existingNursingContents.add(rowsBean);
                    MyLog.i("existingNursingContents", existingNursingContents.size() + "");*/
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

        //TODO 确定提交的内容
        String activitiesType = rowsBean.ActivitiesType;
        String contentCode = rowsBean.ContentCode;
        String contentName = rowsBean.ContentName;
        String contentType = rowsBean.ContentType;
        String cpwCode = rowsBean.CPWCode;
        String cpwType = rowsBean.CPWType;
        String medicalRecord = rowsBean.MedicalRecord;
        String orderCategory = rowsBean.OrderCategory;
        String orderType = rowsBean.OrderType;
        String stageCode = rowsBean.StageCode;

        //得到用户信息，用于提交
        String userCode = sp.getString("userCode", "");
        String realName = sp.getString("realName", "");

        //得到患者信息，用于提交
        String patientsNo = SpPationteMsgConfig.getString("patientsNo", "");

        String name = SpPationteMsgConfig.getString("Name", "");

        String deptCode = SpPationteMsgConfig.getString("deptCode", "");
        String deptName = SpPationteMsgConfig.getString("deptName", "");
        //得到护理真正内容
        NurseContentUnexecuteBean nurseContentUnexecuteBean=new NurseContentUnexecuteBean();
        nurseContentUnexecuteBean.ActivitiesType=activitiesType;
        nurseContentUnexecuteBean.ContentCode=contentCode;
        nurseContentUnexecuteBean.ContentName=contentName;
        nurseContentUnexecuteBean.ContentType=contentType;
        nurseContentUnexecuteBean.CPWCode=cpwCode;
        nurseContentUnexecuteBean.CPWType=cpwType;
        nurseContentUnexecuteBean.MedicalRecord=medicalRecord;
        nurseContentUnexecuteBean.OrderCategory=orderCategory;
        nurseContentUnexecuteBean.OrderType=orderType;
        nurseContentUnexecuteBean.StageCode=stageCode;

        //执行科室名及编号 ==患者所属科室名及编号
        nurseContentUnexecuteBean.DeptCode=deptCode;
        nurseContentUnexecuteBean.DeptName=deptName;
        //患者编号及姓名
        nurseContentUnexecuteBean.PatientsNo=patientsNo;
        nurseContentUnexecuteBean.Name=name;
        //执行人员姓名==用户真实姓名
        nurseContentUnexecuteBean.ExecutionStaff=realName;
        //分类，执行事件，预定时间==开始时间
        nurseContentUnexecuteBean.Type="";
        nurseContentUnexecuteBean.ExecutionTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        nurseContentUnexecuteBean.BeginDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

        String nurseContentUnexecuteBeanJson = new Gson().toJson(nurseContentUnexecuteBean);

        MyLog.i("nurseContentUnexecuteBeanJson",nurseContentUnexecuteBeanJson);

        MyLog.i("MyConstant.BASE_URL+MyConstant.NURSE_UNEXECUTECONTENT_COMIT::", MyConstant.BASE_URL+ MyConstant.NURSE_UNEXECUTECONTENT_COMIT);

        OkHttpUtils.postString().url(MyConstant.BASE_URL+ MyConstant.NURSE_UNEXECUTECONTENT_COMIT).content(nurseContentUnexecuteBeanJson).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(NursingContentSelectParentActivity.this,"提交失败"+e.toString());
                MyLog.i("nurseContentUnexecuteBeanJson::::",e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(NursingContentSelectParentActivity.this,"提交成功");
            }
        });


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
        SpPationteMsgConfig=getSharedPreferences("PationteMsgConfig",MODE_PRIVATE);
        sp=getSharedPreferences("MyselfConfig",MODE_PRIVATE);

        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("维护患者护理内容");

        Intent intent = getIntent();
        parentItemLists = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("SunItemLists");

        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new MyOnItemClickListener());
        tv_search.setOnClickListener(this);
        bt_titlebar_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
               /* Intent existingNursingContentIntent = new Intent(this, ExistingNursingContentActivity.class);
                existingNursingContentIntent.putExtra("existingNursingContents", (Serializable) existingNursingContents);
                startActivity(existingNursingContentIntent);*/
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
            return parentItemLists.size();
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
            vh.tv_ContentName.setText(parentItemLists.get(position).ContentName);
            vh.tv_ContentType.setText(parentItemLists.get(position).ContentType);
            vh.tv_OrderType.setText(parentItemLists.get(position).OrderType);
            vh.tv_OrderCategory.setText(parentItemLists.get(position).OrderCategory);
            vh.tv_MedicalRecord.setText(parentItemLists.get(position).MedicalRecord);
            vh.tv_ActivitiesType.setText(parentItemLists.get(position).ActivitiesType);
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
