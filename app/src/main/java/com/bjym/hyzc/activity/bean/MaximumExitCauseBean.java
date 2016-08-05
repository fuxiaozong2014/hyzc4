package com.bjym.hyzc.activity.bean;

import java.util.List;

/**
 * Created by fushaoqing on 2016/8/5.
 */
public class MaximumExitCauseBean {

    /**
     * VariationNo : 所有
     * PatientsNo : 所有
     * Name : 所有
     * VariationCode : BY0101
     * VariationType : 患方同意出院
     * VariationName : 改保别的方法治疗
     * VariationDate : 0001-01-01 00:00:00
     * Type : 所有
     * CPWCode : 所有
     * AddBy : null
     * AddOn : 0001-01-01 00:00:00
     * EditBy : null
     * EditOn : 0001-01-01 00:00:00
     * tempid : 0
     */

    public List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public  class RowsBean {
        public String VariationNo;
        public String PatientsNo;
        public String Name;
        public String VariationCode;
        public String VariationType;
        public String VariationName;
        public String VariationDate;
        public String Type;
        public String CPWCode;
        public Object AddBy;
        public String AddOn;
        public Object EditBy;
        public String EditOn;
        public int tempid;

       
    }
}
