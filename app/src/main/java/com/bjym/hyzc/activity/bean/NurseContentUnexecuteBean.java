package com.bjym.hyzc.activity.bean;

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
}
