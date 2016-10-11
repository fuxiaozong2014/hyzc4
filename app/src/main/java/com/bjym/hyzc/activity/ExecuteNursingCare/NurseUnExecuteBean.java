package com.bjym.hyzc.activity.ExecuteNursingCare;

import java.util.List;

/**
 * Created by fushaoqing on 2016/8/4.
 */
public class NurseUnExecuteBean {

    /**
     * total : 1
     * rows : [{"OrderNo":"PYZ0000067","Name":"666","PatientsNo":"666","OrderCode":"2081","OrderType":"长期医嘱","OrderCategory":"YZ0013","OrderName":"注射用青霉素钠","Specs":"青霉素","Unit":"盒","Dose":"200ml","Way":"注射","Frequency":"pid","SubOrder":"","Required":"否","Replace":"药典","Department":"005","Duration":"1","DurationUnit":"日","CPWCode":"CP160713000038","StageCode":"JD000017","Type":null,"BeginDate":"2016-08-04 00:00:00","ExecutionTime":"2016-08-04 06:55:17","DeptCode":"005","DeptName":null,"StaffCode":null,"ExecutionStaff":null,"Ex1":null,"Ex2":null,"Ex3":null,"AddBy":"超级管理员","AddOn":"2016-08-04 14:55:18","EditBy":null,"EditOn":"0001-01-01 00:00:00","tempid":0}]
     */

    public int total;
    /**
     * OrderNo : PYZ0000067
     * Name : 666
     * PatientsNo : 666
     * OrderCode : 2081
     * OrderType : 长期医嘱
     * OrderCategory : YZ0013
     * OrderName : 注射用青霉素钠
     * Specs : 青霉素
     * Unit : 盒
     * Dose : 200ml
     * Way : 注射
     * Frequency : pid
     * SubOrder : 
     * Required : 否
     * Replace : 药典
     * Department : 005
     * Duration : 1
     * DurationUnit : 日
     * CPWCode : CP160713000038
     * StageCode : JD000017
     * Type : null
     * BeginDate : 2016-08-04 00:00:00
     * ExecutionTime : 2016-08-04 06:55:17
     * DeptCode : 005
     * DeptName : null
     * StaffCode : null
     * ExecutionStaff : null
     * Ex1 : null
     * Ex2 : null
     * Ex3 : null
     * AddBy : 超级管理员
     * AddOn : 2016-08-04 14:55:18
     * EditBy : null
     * EditOn : 0001-01-01 00:00:00
     * tempid : 0
     */

    public List<RowsBean> rows;
    public List<RowsBean> getRows() {
        return rows;
    }
    public  class RowsBean {
        public String OrderNo;
        public String Name;
        public String PatientsNo;
        public String OrderCode;
        public String OrderType;
        public String OrderCategory;
        public String OrderName;
        public String Specs;
        public String Unit;
        public String Dose;
        public String Way;
        public String Frequency;
        public String SubOrder;
        public String Required;
        public String Replace;
        public String Department;
        public String Duration;
        public String DurationUnit;
        public String CPWCode;
        public String StageCode;
        public Object Type;
        public String BeginDate;
        public String ExecutionTime;
        public String DeptCode;
        public Object DeptName;
        public Object StaffCode;
        public Object ExecutionStaff;
        public Object Ex1;
        public Object Ex2;
        public Object Ex3;
        public String AddBy;
        public String AddOn;
        public Object EditBy;
        public String EditOn;
        public int tempid;


        @Override
        public String toString() {
            return "RowsBean{" +
                    "OrderNo='" + OrderNo + '\'' +
                    ", Name='" + Name + '\'' +
                    ", PatientsNo='" + PatientsNo + '\'' +
                    ", OrderCode='" + OrderCode + '\'' +
                    ", OrderType='" + OrderType + '\'' +
                    ", OrderCategory='" + OrderCategory + '\'' +
                    ", OrderName='" + OrderName + '\'' +
                    ", Specs='" + Specs + '\'' +
                    ", Unit='" + Unit + '\'' +
                    ", Dose='" + Dose + '\'' +
                    ", Way='" + Way + '\'' +
                    ", Frequency='" + Frequency + '\'' +
                    ", SubOrder='" + SubOrder + '\'' +
                    ", Required='" + Required + '\'' +
                    ", Replace='" + Replace + '\'' +
                    ", Department='" + Department + '\'' +
                    ", Duration='" + Duration + '\'' +
                    ", DurationUnit='" + DurationUnit + '\'' +
                    ", CPWCode='" + CPWCode + '\'' +
                    ", StageCode='" + StageCode + '\'' +
                    ", Type=" + Type +
                    ", BeginDate='" + BeginDate + '\'' +
                    ", ExecutionTime='" + ExecutionTime + '\'' +
                    ", DeptCode='" + DeptCode + '\'' +
                    ", DeptName=" + DeptName +
                    ", StaffCode=" + StaffCode +
                    ", ExecutionStaff=" + ExecutionStaff +
                    ", Ex1=" + Ex1 +
                    ", Ex2=" + Ex2 +
                    ", Ex3=" + Ex3 +
                    ", AddBy='" + AddBy + '\'' +
                    ", AddOn='" + AddOn + '\'' +
                    ", EditBy=" + EditBy +
                    ", EditOn='" + EditOn + '\'' +
                    ", tempid=" + tempid +
                    '}';
        }
    }
}

