package com.bjym.hyzc.activity.bean;

import java.util.List;

/**
 * Created by fushaoqing on 2016/7/14.
 */
public class QuestionOption {
    /**
     * total : 3
     * rows : [{"ChoiceNo":"PSC0000004","TopicNo":"PST0000014","Number":1,"Contents":"可以","IsCustomAnswer":"无","Score":0,"AddBy":"超级管理员","AddOn":"2016-07-11 17:41:35","EditBy":null,"EditOn":"0001-01-01 00:00:00","tempid":0},{"ChoiceNo":"PSC0000005","TopicNo":"PST0000014","Number":2,"Contents":"不可以11","IsCustomAnswer":"","Score":0,"AddBy":"超级管理员","AddOn":"2016-07-11 17:41:52","EditBy":"超级管理员","EditOn":"2016-07-11 17:45:41","tempid":0},{"ChoiceNo":"PSC0000009","TopicNo":"PST0000014","Number":3,"Contents":"中国人民","IsCustomAnswer":"","Score":0,"AddBy":"超级管理员","AddOn":"2016-07-12 11:35:46","EditBy":null,"EditOn":"0001-01-01 00:00:00","tempid":0}]
     */

    public int total;
    /**
     * ChoiceNo : PSC0000004
     * TopicNo : PST0000014
     * Number : 1
     * Contents : 可以
     * IsCustomAnswer : 无
     * Score : 0
     * AddBy : 超级管理员
     * AddOn : 2016-07-11 17:41:35
     * EditBy : null
     * EditOn : 0001-01-01 00:00:00
     * tempid : 0
     */

    public List<Option> rows;

    public QuestionOption() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Option> getRows() {
        return rows;
    }

    public void setRows(List<Option> rows) {
        this.rows = rows;
    }


    public static class Option {
        public String ChoiceNo;
        public String TopicNo;
        public int Number;
        public String Contents;
        public String IsCustomAnswer;
        public int Score;
        public String AddBy;
        public String AddOn;
        public Object EditBy;
        public String EditOn;
        public int tempid;


    }
}
