package com.bjym.hyzc.activity.bean;

/**
 * Created by fushaoqing on 2016/7/18.
 */
public class SurveyAnswer {
    public String PatiSurveryNo;
    public String TopicNo;
    public String Answer;
    public String SubTime;


    public SurveyAnswer(String patiSurveryNo, String topicNo, String answer, String subTime) {
        PatiSurveryNo = patiSurveryNo;
        TopicNo = topicNo;
        Answer = answer;
        SubTime = subTime;
    }
}
