package com.bjym.hyzc.activity.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bjym.hyzc.R;
import com.bjym.hyzc.activity.bean.PationteBean;
import com.bjym.hyzc.activity.utils.MyToast;

/**
 * Created by fushaoqing on 2016/8/16.
 */
public class PationteBestDetailMsgActivity extends BaseActivity {

    private ViewHolder vh;
    private View view;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private PationteBean pationteBean;

    @Override
    public View setMainView() {
        view = View.inflate(context, R.layout.activity_pationtebestdetailmsg, null);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        vh = new ViewHolder(view);
        view.setTag(vh);

        return view;
    }

    @Override
    public void InitData() {
        bt_titlebar_left.setVisibility(View.VISIBLE);
        bt_titlebar_right.setVisibility(View.GONE);
        tv_titlebar_center.setText("患者详情");
        bt_titlebar_left.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent !=null) {
            pationteBean = (PationteBean) intent.getSerializableExtra("pationteBean");
        }else{
            MyToast.showToast(PationteBestDetailMsgActivity.this,"暂无患者详细信息");
            return;
        }
        ViewHolder vh = (ViewHolder) view.getTag();

        vh.myPationte_name.setText(pationteBean.Name);
        vh.myPationte_sex.setText(pationteBean.Sex);
        vh.myPationte_PYM.setText(pationteBean.PYM);
        vh.myPationte_IDNo.setText(pationteBean.IDNo);
        vh.myPationte_phone.setText(pationteBean.Mobile);
        vh.myPationte_number.setText(pationteBean.PatientsNo);
        vh.myPationte_bednumber.setText(pationteBean.BedNo);
        vh.myPationte_CardNo.setText(pationteBean.CardNo);
        vh.myPationte_date.setText(pationteBean.InDate);
        vh.myPationte_DeptName.setText(pationteBean.DeptName);
        vh.myPationte_DeptCode.setText(pationteBean.DeptCode);
        vh.myPationte_NuseCellCode.setText(pationteBean.NuseCellCode);
        vh.myPationte_NuseCellName.setText(pationteBean.NuseCellName);
        vh.myPationte_InDocCode.setText(pationteBean.InDocCode);
        vh.myPationte_InDocName.setText(pationteBean.InDocName);
        vh.myPationte_ChargeDocCode.setText(pationteBean.ChargeDocCode);
        vh.myPationte_ChargeDocName.setText(pationteBean.ChargeDocName);
        vh.myPationte_ChifeDocCode.setText(pationteBean.ChifeDocCode);
        vh.myPationte_ChifeDocName.setText(pationteBean.ChifeDocName);
        vh.myPationte_DutyNuseName.setText(pationteBean.DutyNuseName);
        vh.myPationte_DutyNuseCode.setText(pationteBean.DutyNuseCode);
        vh.myPationte_CPWName.setText(pationteBean.CPWName);
        vh.myPationte_DiagnosisName.setText(pationteBean.DiagnosisName);
        vh.myPationte_CPWCode.setText(pationteBean.CPWCode);
        vh.myPationte_Source.setText(pationteBean.Source);
        vh.myPationte_BeginDays.setText(pationteBean.BeginDays);
        vh.myPationte_BeginTime.setText(pationteBean.BeginTime);
        vh.myPationte_PatientState.setText(pationteBean.PatientState);
        vh.myPationte_CPState.setText(pationteBean.CPState);
        vh.myPationte_StandardCosts.setText(pationteBean.StandardCosts);
        vh.myPationte_Remark.setText(pationteBean.Remark);


    }

    public  class ViewHolder {
        public View rootView;
        public TextView myPationte_name;
        public TextView myPationte_sex;
        public TextView myPationte_PYM;
        public TextView myPationte_IDNo;
        public TextView myPationte_phone;
        public TextView myPationte_number;
        public TextView myPationte_bednumber;
        public TextView myPationte_CardNo;
        public TextView myPationte_date;
        public TextView myPationte_DeptName;
        public TextView myPationte_DeptCode;
        public TextView myPationte_NuseCellCode;
        public TextView myPationte_NuseCellName;
        public TextView myPationte_InDocCode;
        public TextView myPationte_InDocName;
        public TextView myPationte_ChargeDocCode;
        public TextView myPationte_ChargeDocName;
        public TextView myPationte_ChifeDocCode;
        public TextView myPationte_ChifeDocName;
        public TextView myPationte_DutyNuseName;
        public TextView myPationte_DutyNuseCode;
        public TextView myPationte_CPWName;
        public TextView myPationte_DiagnosisName;
        public TextView myPationte_CPWCode;
        public TextView myPationte_Source;
        public TextView myPationte_BeginDays;
        public TextView myPationte_BeginTime;
        public TextView myPationte_PatientState;
        public TextView myPationte_CPState;
        public TextView myPationte_StandardCosts;
        public TextView myPationte_Remark;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.myPationte_name = (TextView) rootView.findViewById(R.id.myPationte_name);
            this.myPationte_sex = (TextView) rootView.findViewById(R.id.myPationte_sex);
            this.myPationte_PYM = (TextView) rootView.findViewById(R.id.myPationte_PYM);
            this.myPationte_IDNo = (TextView) rootView.findViewById(R.id.myPationte_IDNo);
            this.myPationte_phone = (TextView) rootView.findViewById(R.id.myPationte_phone);
            this.myPationte_number = (TextView) rootView.findViewById(R.id.myPationte_number);
            this.myPationte_bednumber = (TextView) rootView.findViewById(R.id.myPationte_bednumber);
            this.myPationte_CardNo = (TextView) rootView.findViewById(R.id.myPationte_CardNo);
            this.myPationte_date = (TextView) rootView.findViewById(R.id.myPationte_date);
            this.myPationte_DeptName = (TextView) rootView.findViewById(R.id.myPationte_DeptName);
            this.myPationte_DeptCode = (TextView) rootView.findViewById(R.id.myPationte_DeptCode);
            this.myPationte_NuseCellCode = (TextView) rootView.findViewById(R.id.myPationte_NuseCellCode);
            this.myPationte_NuseCellName = (TextView) rootView.findViewById(R.id.myPationte_NuseCellName);
            this.myPationte_InDocCode = (TextView) rootView.findViewById(R.id.myPationte_InDocCode);
            this.myPationte_InDocName = (TextView) rootView.findViewById(R.id.myPationte_InDocName);
            this.myPationte_ChargeDocCode = (TextView) rootView.findViewById(R.id.myPationte_ChargeDocName);
            this.myPationte_ChargeDocName = (TextView) rootView.findViewById(R.id.myPationte_ChargeDocCode);
            this.myPationte_ChifeDocCode = (TextView) rootView.findViewById(R.id.myPationte_ChifeDocName);
            this.myPationte_ChifeDocName = (TextView) rootView.findViewById(R.id.myPationte_ChifeDocCode);
            this.myPationte_DutyNuseName = (TextView) rootView.findViewById(R.id.myPationte_DutyNuseName);
            this.myPationte_DutyNuseCode = (TextView) rootView.findViewById(R.id.myPationte_DutyNuseCode);
            this.myPationte_CPWName = (TextView) rootView.findViewById(R.id.myPationte_CPWName);
            this.myPationte_DiagnosisName = (TextView) rootView.findViewById(R.id.myPationte_DiagnosisName);
            this.myPationte_CPWCode = (TextView) rootView.findViewById(R.id.myPationte_CPWCode);
            this.myPationte_Source = (TextView) rootView.findViewById(R.id.myPationte_Source);
            this.myPationte_BeginDays = (TextView) rootView.findViewById(R.id.myPationte_BeginDays);
            this.myPationte_BeginTime = (TextView) rootView.findViewById(R.id.myPationte_BeginTime);
            this.myPationte_PatientState = (TextView) rootView.findViewById(R.id.myPationte_PatientState);
            this.myPationte_CPState = (TextView) rootView.findViewById(R.id.myPationte_CPState);
            this.myPationte_StandardCosts = (TextView) rootView.findViewById(R.id.myPationte_StandardCosts);
            this.myPationte_Remark = (TextView) rootView.findViewById(R.id.myPationte_Remark);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case  R.id.bt_titlebar_left:
                finish();
                break;
            default:
                break;
        }
    }
}
