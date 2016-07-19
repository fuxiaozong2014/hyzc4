package com.bjym.hyzc.activity.utils;

/**
 * Created by fushaoqing on 2016/6/27.
 */
public class MyConstant {
    /**
     * 服务器ip
     */
    public static final String BASE_URL = "http://cp.sqall.com";
    /**
     * 登录接口
     */
    public static final String LOGIN_URL = BASE_URL + "/login/loginauth";
    /*
    * 管床病人接口
    *
    * */
    public static final String MYPATIONTE_URL = BASE_URL + "/cpw/CPW_Patients/getpatients";
    /*
    * 用户详细信息接口
    * */
    public static final String MYMSG_URL = BASE_URL + "/Sys/Base_User/getuserinfo?usercode=sysadmin";
    /*
    * 问卷名列表接口
    * */
    public static final String MYDIAOCHA_URL = BASE_URL + "/cpw/CPW_Survey/getlist";
    /*
    * 问题表接口
    * */
    public static final String QUESTIONLIST_URL = "http://192.168.0.168/cpw/CPW_Topic/getlistbysurvey?Survey=";
    /*
    * 选项表接口
    * */
    public static final String OPTION_URL = "http://192.168.0.168/cpw/CPW_Choice/getlistbytopicno?topic=";
    /*
    * 提交答案接口
    * */
    public static final String ANSWERS_URL = BASE_URL + "/cpw/CPW_PatiAnswer/addpatianswer";

}
