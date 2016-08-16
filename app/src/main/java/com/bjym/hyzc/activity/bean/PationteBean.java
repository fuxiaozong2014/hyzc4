package com.bjym.hyzc.activity.bean;

import java.io.Serializable;

/**
 * Created by fushaoqing on 2016/7/4.
 */
public class PationteBean implements Serializable{


    /**
     * PatientsNo : 10010
     * CardNo : 10010
     * Name : 测试
     * Sex : null
     * PYM : null
     * IDNo : null
     * Mobile : null
     * DeptName : null
     * DeptCode : null
     * BedNo : null
     * NuseCellCode : null
     * NuseCellName : null
     * InDocCode : null
     * InDocName : null
     * ChargeDocCode : null
     * ChargeDocName : null
     * ChifeDocCode : null
     * ChifeDocName : null
     * DutyNuseCode : null
     * DutyNuseName : null
     * InDate : 2016-06-13 00:00:00
     * DiagnosisName : null
     * Source : null
     * CPWCode : null
     * CPWName : null
     * BeginDays : 0
     * BeginTime : 0001-01-01 00:00:00
     * StandardCosts : 0.0
     * PatientState : null
     * CPState : null
     * Remark : null
     * Opeator : null
     * OperatorDate : 0001-01-01 00:00:00
     * tempid : 0
     */

    public String PatientsNo;
    public String CardNo;
    public String Name;
    public String Sex;
    public String PYM;
    public String IDNo;
    public String Mobile;
    public String DeptName;
    public String DeptCode;
    public String BedNo;
    public String NuseCellCode;
    public String NuseCellName;
    public String InDocCode;
    public String InDocName;
    public String ChargeDocCode;
    public String ChargeDocName;
    public String ChifeDocCode;
    public String ChifeDocName;
    public String DutyNuseCode;
    public String DutyNuseName;
    public String InDate;
    public String DiagnosisName;
    public String Source;
    public String CPWCode;
    public String CPWName;
    public String BeginDays;
    public String BeginTime;
    public String StandardCosts;
    public String PatientState;
    public String CPState;
    public String Remark;
    public String Opeator;
    public String OperatorDate;
    public int tempid;


    public PationteBean() {
    }

    @Override
    public String toString() {
        return "PationteBean [PatientsNo=" + PatientsNo + ", CardNo=" + CardNo
                + ", Name=" + Name + ", Sex=" + Sex + ", PYM=" + PYM
                + ", IDNo=" + IDNo + ", Mobile=" + Mobile + ", DeptName="
                + DeptName + ", DeptCode=" + DeptCode + ", BedNo=" + BedNo
                + ", NuseCellCode=" + NuseCellCode + ", NuseCellName="
                + NuseCellName + ", InDocCode=" + InDocCode + ", InDocName="
                + InDocName + ", ChargeDocCode=" + ChargeDocCode
                + ", ChargeDocName=" + ChargeDocName + ", ChifeDocCode="
                + ChifeDocCode + ", ChifeDocName=" + ChifeDocName
                + ", DutyNuseCode=" + DutyNuseCode + ", DutyNuseName="
                + DutyNuseName + ", InDate=" + InDate + ", DiagnosisName="
                + DiagnosisName + ", Source=" + Source + ", CPWCode=" + CPWCode
                + ", CPWName=" + CPWName + ", BeginDays=" + BeginDays
                + ", BeginTime=" + BeginTime + ", StandardCosts="
                + StandardCosts + ", PatientState=" + PatientState
                + ", CPState=" + CPState + ", Remark=" + Remark + ", Opeator="
                + Opeator + ", OperatorDate=" + OperatorDate + ", tempid="
                + tempid + "]";
    }
}
