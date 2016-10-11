package com.bjym.hyzc.activity.ExecuteNursingCare;

/**
 * Created by fushaoqing on 2016/8/19.
 */
public class NurseContentUnexecuteBean {
    public String ActivitiesType;
    public String ContentCode;
    public String ContentName;
    public String ContentType;
    public  String CPWCode;
    public String CPWType;
    public  String MedicalRecord ;
    public String OrderCategory;
    public String OrderType ;
    public String StageCode ;

    public String Type ;
    public String BeginDate ;
    public String ExecutionTime ;
    public String DeptName ;
    public String DeptCode ;
    public String ExecutionStaff ;

    public String PatientsNo;
    public String Name;

    @Override
    public String toString() {
        return "NurseContentUnexecuteBean{" +
                "ActivitiesType='" + ActivitiesType + '\'' +
                ", ContentCode='" + ContentCode + '\'' +
                ", ContentName='" + ContentName + '\'' +
                ", ContentType='" + ContentType + '\'' +
                ", CPWCode='" + CPWCode + '\'' +
                ", CPWType='" + CPWType + '\'' +
                ", MedicalRecord='" + MedicalRecord + '\'' +
                ", OrderCategory='" + OrderCategory + '\'' +
                ", OrderType='" + OrderType + '\'' +
                ", StageCode='" + StageCode + '\'' +
                ", Type='" + Type + '\'' +
                ", BeginDate='" + BeginDate + '\'' +
                ", ExecutionTime='" + ExecutionTime + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", DeptCode='" + DeptCode + '\'' +
                ", ExecutionStaff='" + ExecutionStaff + '\'' +
                ", PatientsNo='" + PatientsNo + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }

   /* public NurseContentUnexecuteBean(String activitiesType, String name, String contentCode, String contentName, String contentType, String CPWCode, String CPWType, String medicalRecord, String orderCategory, String orderType, String stageCode, String type, String beginDate, String executionTime, String deptName, String deptCode, String executionStaff, String patientsNo) {
        ActivitiesType = activitiesType;
        Name = name;
        ContentCode = contentCode;
        ContentName = contentName;
        ContentType = contentType;
        this.CPWCode = CPWCode;
        this.CPWType = CPWType;
        MedicalRecord = medicalRecord;
        OrderCategory = orderCategory;
        OrderType = orderType;
        StageCode = stageCode;
        Type = type;
        BeginDate = beginDate;
        ExecutionTime = executionTime;
        DeptName = deptName;
        DeptCode = deptCode;
        ExecutionStaff = executionStaff;
        PatientsNo = patientsNo;
    }*/

    public NurseContentUnexecuteBean() {
    }
}
