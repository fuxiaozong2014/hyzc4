package com.bjym.hyzc.activity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fushaoqing on 2016/7/11.
 */
public class DiaoChaSortBean {


    /**
     * total : 3
     * rows : [{"SurveyNo":"PSI0000001","SurveyName":"责任护士满意度调查表","TotalScore":100,"GoodMax":100,"GoodMin":80,"SosoMax":60,"SosoMin":80,"BadMax":60,"BadMin":0,"Description":"","AddBy":"超级管理员","AddOn":"2016-07-08 14:41:04","EditBy":null,"EditOn":"0001-01-01 00:00:00","tempid":0},{"SurveyNo":"PSI0000003","SurveyName":"主管护士满意度度调查表","TotalScore":100,"GoodMax":100,"GoodMin":80,"SosoMax":80,"SosoMin":60,"BadMax":60,"BadMin":0,"Description":"","AddBy":"超级管理员","AddOn":"2016-07-08 14:45:51","EditBy":null,"EditOn":"0001-01-01 00:00:00","tempid":0},{"SurveyNo":"PSI0000004","SurveyName":"主管医生满意的调查表","TotalScore":100,"GoodMax":100,"GoodMin":80,"SosoMax":80,"SosoMin":60,"BadMax":60,"BadMin":0,"Description":"","AddBy":"超级管理员","AddOn":"2016-07-08 14:46:24","EditBy":null,"EditOn":"0001-01-01 00:00:00","tempid":0}]
     */

    public int total;
    /**
     * SurveyNo : PSI0000001
     * SurveyName : 责任护士满意度调查表
     * TotalScore : 100
     * GoodMax : 100
     * GoodMin : 80
     * SosoMax : 60
     * SosoMin : 80
     * BadMax : 60
     * BadMin : 0
     * Description :
     * AddBy : 超级管理员
     * AddOn : 2016-07-08 14:41:04
     * EditBy : null
     * EditOn : 0001-01-01 00:00:00
     * tempid : 0
     */

    public List<RowsBean> rows;

    public DiaoChaSortBean() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "DiaoChaSortBean{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public class RowsBean implements Serializable{
        public String SurveyNo;
        public String SurveyName;
        public int TotalScore;
        public int GoodMax;
        public int GoodMin;
        public int SosoMax;
        public int SosoMin;
        public int BadMax;
        public int BadMin;
        public String Description;
        public String AddBy;
        public String AddOn;
        public Object EditBy;
        public String EditOn;
        public int tempid;

    }



}
