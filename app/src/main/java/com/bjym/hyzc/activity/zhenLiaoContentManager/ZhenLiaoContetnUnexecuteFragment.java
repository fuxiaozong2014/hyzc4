package com.bjym.hyzc.activity.zhenLiaoContentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.executeNursingCare.NurseContentUnexecuteBean;
import com.bjym.hyzc.activity.executeNursingCare.NursingContentBean;
import com.bjym.hyzc.activity.fragment.BaseFragment;
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
 * Created by fushaoqing on 2016/9/7.
 */
public class ZhenLiaoContetnUnexecuteFragment extends BaseFragment {
    private ListView lv;
    private TextView tv_search;
    private TextView tv_none_nurseContent;

    private List<NursingContentBean.RowsBean> parentStageUnExecuteNursingCare;
    private List<NursingContentBean.RowsBean> childStageUnExecuteNursingCare;
    private List<NursingContentBean.RowsBean> currentItemLists;
    private SharedPreferences SpPationteMsgConfig;
    private SharedPreferences sp;
    private List<NursingContentBean.RowsBean> sunStageUnExecuteNursingCare;
    private boolean isHasExecuted;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("确定执行此诊疗内容？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NursingContentBean.RowsBean rowsBean = currentItemLists.get(position);
                    postUnexecuContent(rowsBean);
                    dialog.dismiss();
                    view.findViewById(R.id.iv_checked).setVisibility(View.VISIBLE);
                    sp.edit().putBoolean("isHasExecuted", false).commit();

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
        NurseContentUnexecuteBean nurseContentUnexecuteBean = new NurseContentUnexecuteBean();
        nurseContentUnexecuteBean.ActivitiesType = activitiesType;
        nurseContentUnexecuteBean.ContentCode = contentCode;
        nurseContentUnexecuteBean.ContentName = contentName;
        nurseContentUnexecuteBean.ContentType = contentType;
        nurseContentUnexecuteBean.CPWCode = cpwCode;
        nurseContentUnexecuteBean.CPWType = cpwType;
        nurseContentUnexecuteBean.MedicalRecord = medicalRecord;
        nurseContentUnexecuteBean.OrderCategory = orderCategory;
        nurseContentUnexecuteBean.OrderType = orderType;
        nurseContentUnexecuteBean.StageCode = stageCode;

        //执行科室名及编号 ==患者所属科室名及编号
        nurseContentUnexecuteBean.DeptCode = deptCode;
        nurseContentUnexecuteBean.DeptName = deptName;
        //患者编号及姓名
        nurseContentUnexecuteBean.PatientsNo = patientsNo;
        nurseContentUnexecuteBean.Name = name;
        //执行人员姓名==用户真实姓名
        nurseContentUnexecuteBean.ExecutionStaff = realName;
        //分类，执行事件，预定时间==开始时间
        nurseContentUnexecuteBean.Type = "";
        nurseContentUnexecuteBean.ExecutionTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        nurseContentUnexecuteBean.BeginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

        String nurseContentUnexecuteBeanJson = new Gson().toJson(nurseContentUnexecuteBean);

       /* MyLog.i("nurseContentUnexecuteBeanJson",nurseContentUnexecuteBeanJson);

        MyLog.i("MyConstant.BASE_URL+MyConstant.NURSE_UNEXECUTECONTENT_COMIT::", MyConstant.BASE_URL+ MyConstant.NURSE_UNEXECUTECONTENT_COMIT);*/

        OkHttpUtils.postString().url(MyConstant.BASE_URL + MyConstant.NURSE_UNEXECUTECONTENT_COMIT).content(nurseContentUnexecuteBeanJson).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int i) throws Exception {

                return null;
            }

            @Override
            public void onError(Call call, Exception e, int i) {
                MyToast.showToast(ZhenLiaoContetnUnexecuteFragment.this.getActivity(), "执行失败" + e.toString());
                MyLog.i("nurseContentUnexecuteBeanJson::::", "执行失败" + e.toString());
            }

            @Override
            public void onResponse(Object o, int i) {
                MyToast.showToast(ZhenLiaoContetnUnexecuteFragment.this.getActivity(), "执行成功");
            }
        });


    }

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.fragment_nurseunexecutecontent, null);
        tv_none_nurseContent = (TextView) view.findViewById(R.id.tv_none_nurseContent);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        lv = (ListView) view.findViewById(R.id.lv);
        return view;
    }

    @Override
    public void InitData() {
        SpPationteMsgConfig = ZhenLiaoContetnUnexecuteFragment.this.getActivity().getSharedPreferences("PationteMsgConfig", context.MODE_PRIVATE);
        sp = ZhenLiaoContetnUnexecuteFragment.this.getActivity().getSharedPreferences("MyselfConfig", context.MODE_PRIVATE);

        Intent intent = ZhenLiaoContetnUnexecuteFragment.this.getActivity().getIntent();
        /*
        * parentStageUnExecuteNursingCare是只有一级路径阶段，直接传递过来对应的护理内容集合
        * */
        parentStageUnExecuteNursingCare = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("SunItemLists");
        /*
        * childStageUnExecuteNursingCare是有二级阶段的传递过来的，直传递过来对应的护理内容集合
        * */
        childStageUnExecuteNursingCare = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("rowsSunItemLists");
        /*
        * sunStageUnExecuteNursingCare是有三级路径阶段传递过来的未执行护理内容的集合
        * */
        sunStageUnExecuteNursingCare = (List<NursingContentBean.RowsBean>) intent.getSerializableExtra("sunStageUnExecuteNursingCare");
        if (parentStageUnExecuteNursingCare == null && sunStageUnExecuteNursingCare == null) {
            currentItemLists = childStageUnExecuteNursingCare;
        }
        if (parentStageUnExecuteNursingCare == null && childStageUnExecuteNursingCare == null) {
            currentItemLists = sunStageUnExecuteNursingCare;
        }
        if (sunStageUnExecuteNursingCare == null && childStageUnExecuteNursingCare == null) {
            currentItemLists = parentStageUnExecuteNursingCare;
        }
        if (currentItemLists.size() == 0) {
            lv.setVisibility(View.GONE);
            tv_none_nurseContent.setVisibility(View.VISIBLE);
        }
        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new MyOnItemClickListener());
        tv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                break;
           /* case R.id.bt_titlebar_left:
                finish();
                break;*/
            default:
                break;
        }
    }


    public class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return currentItemLists.size();
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
            vh.tv_ContentName.setText(currentItemLists.get(position).ContentName);
            vh.tv_ContentType.setText(currentItemLists.get(position).ContentType);
            vh.tv_OrderType.setText(currentItemLists.get(position).OrderType);
            vh.tv_OrderCategory.setText(currentItemLists.get(position).OrderCategory);
            vh.tv_MedicalRecord.setText(currentItemLists.get(position).MedicalRecord);
            vh.tv_ActivitiesType.setText(currentItemLists.get(position).ActivitiesType);
            String flag = currentItemLists.get(position).Flag;
            MyLog.i("flag的值",flag);
            if (!flag.equals("")){
                vh.iv_checked.setVisibility(View.VISIBLE);
            }
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
            public ImageView iv_checked;

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
                this.iv_checked = (ImageView) rootView.findViewById(R.id.iv_checked);

            }

        }
    }
}
