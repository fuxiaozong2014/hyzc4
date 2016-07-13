package com.bjym.hyzc.activity.bean;

import java.util.List;

/**
 * Created by fushaoqing on 2016/7/13.
 */
public class Question {

    /**
     * total : 1
     * rows : [{"TopicNo":"PST0000014","SurveyNo":"PSI0000001","Number":2,"Contents":"您熟悉病区环境吗？","Description":"","TopicType":"","Required":"","Score":0,"AddBy":"超级管理员","AddOn":"2016-07-08 15:56:20","EditBy":null,"EditOn":"0001-01-01 00:00:00","tempid":0}]
     */

    public int total;
    /**
     * TopicNo : PST0000014
     * SurveyNo : PSI0000001
     * Number : 2
     * Contents : 您熟悉病区环境吗？
     * Description :
     * TopicType :
     * Required :
     * Score : 0
     * AddBy : 超级管理员
     * AddOn : 2016-07-08 15:56:20
     * EditBy : null
     * EditOn : 0001-01-01 00:00:00
     * tempid : 0
     */

    public List<RowsBean> rows;

    public Question() {
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

    public static class RowsBean {
        public String TopicNo;
        public String SurveyNo;
        public int Number;
        public String Contents;
        public String Description;
        public String TopicType;
        public String Required;
        public int Score;
        public String AddBy;
        public String AddOn;
        public Object EditBy;
        public String EditOn;
        public int tempid;


    }
}
