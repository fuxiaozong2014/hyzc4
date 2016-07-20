package com.bjym.hyzc.activity.bean;

/**
 * Created by fushaoqing on 2016/7/20.
 */
public class SubmitorMsg {

    public String PatiSurveryNo;
    public String SurveyNo;
    public String PatientsNo;
    public String Name;
    public String SurveyCode;
    public String SurveyName;
    public String Score;

    public SubmitorMsg(String newCode, String surveyNo, String patientsNo, String name, String surveyCode, String surveyName, String score) {
        PatiSurveryNo = newCode;
        SurveyNo = surveyNo;
        PatientsNo = patientsNo;
        Name = name;
        SurveyCode = surveyCode;
        SurveyName = surveyName;
        Score = score;
    }
}
