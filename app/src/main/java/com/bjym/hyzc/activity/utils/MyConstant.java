package com.bjym.hyzc.activity.utils;

/**
 * Created by fushaoqing on 2016/6/27.
 */
public class MyConstant {

    /**
     * 服务器ip
     */
    public static final String BASE_URL = "http://192.168.0.168";
    // public static final String BASE_URL = "http://192.168.0.188";
    // public static final String BASE_URL = "http://cp.hyzczg.com";
    //public static final String BASE_URL = "http://cp.sqall.com";
    // public static final String BASE_URL = "http://hyzc.tpddns.cn:6060";

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
    public static final String QUESTIONLIST_URL = BASE_URL + "/cpw/CPW_Topic/getlistbysurvey?Survey=";
    /*
    * 选项表接口
    * */
    public static final String OPTION_URL = BASE_URL + "/cpw/CPW_Choice/getlistbytopicno?topic=";

    /*
    * 获取调查编号接口
    * */
    public static final String PATISURVERYNO_URL = BASE_URL + "/Sys/Base_CodeRule/getrulecode?RuleCode=PSS";

    /*
    * 提交调查基本信息接口
    * */                                                    ///cpw/CPW_PatiSurvery/addpatisurvery
    public static final String SUBMITORMSG_URL = BASE_URL + "/cpw/CPW_PatiSurvery/addpatisurvery";
    /*
    * 提交答案接口
    * */                                               ///cpw/CPW_PatiAnswer/addpatianswer
    public static final String ANSWERS_URL = BASE_URL + "/cpw/CPW_PatiAnswer/addpatianswer";

    /*
    * 获得护理记录编码
    * */
    public static final String NURSE_HISTORY = BASE_URL + "/Sys/Base_CodeRule/getrulecode?RuleCode=CPRJJL";
    /*
    *
    * 提交护理记录
    * */                                                        ///CPW/CPW_PatiNursingrecords/addnursingrecords
    public static final String NURSE_HISTORY_COMMIT = BASE_URL + "/CPW/CPW_PatiNursingrecords/add";

    /*
    * 根据cpwCode获取详细路径信息
    * */
    public static final String NURSE_SELECT_STAGE = BASE_URL + "/CPW/CPW_InfoStage/getlist?cpwcode=";

    /*
   * 根据病人编号和stageCode去获取未执行的医嘱
   * */
    public static final String NURSE_UNEXECUTE_STAGE = BASE_URL + "/CPW/CPW_PatiOrder/getlistnoexec";

    /*
  * 提交护士已选择的未执行的医嘱
  * */
    public static final String NURSE_UNEXECUTE_COMIT = BASE_URL + "/CPW/CPW_PatiExecOrder/execorder?OrderNo=";


    /*
     * 获取退出路径患者的信息
     * */
    public static final String PATIONTE_EXITCPW_MSG = BASE_URL + "/CPW/CPW_Patients/getpatientsexitcpw";
    /*
     * 获取患者退出路径的原因
     * */
    public static final String PATIONTE_EXITCPW_CAUSE = BASE_URL + "/CPW/CPW_PatiVariation/GetListOrder";

    /*
        * 提交新增护理
        * */
    public static final String ADD_NURSINGCARE_COMIT = BASE_URL + "/CPW/CPW_PatiNursing/add";

    /*
       * 获取可选的护理内容
       * */
    public static final String NURSING_CONTENT_SELECT= BASE_URL + "/CPW/CPW_InfoContent/getlist?cpwcode=";
}
