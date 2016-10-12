package com.bjym.hyzc.activity.utils;

/**
 * Created by fushaoqing on 2016/6/27.
 */
public class MyConstant {

    /**
     * 服务器ip
     */
    //public static  String BASE_URL = "http://192.168.0.168";
    // public static  String BASE_URL = "http://192.168.0.188";
    public static  String BASE_URL = "http://cp.hyzczg.com";
    //public static  String BASE_URL = "http://cp.sqall.com";
    // public static  String BASE_URL = "http://hyzc.tpddns.cn:6060";

    /**
     * 登录接口
     */
    public static final String LOGIN_URL = "/login/loginauth";
    /*
    * 管床病人接口
    *
    * */
    public static final String MYPATIONTE_URL = "/cpw/CPW_Patients/getpatients";
    /*
    * 用户详细信息接口
    * */
    //TODO 传值给mainactivity
    public static final String MYMSG_URL = "/Sys/Base_User/getuserinfo?usercode=";
    /*
    * 问卷名列表接口
    * */
    public static final String MYDIAOCHA_URL = "/cpw/CPW_Survey/getlist";
    /*
    * 问题表接口
    * */
    public static final String QUESTIONLIST_URL = "/cpw/CPW_Topic/getlistbysurvey?Survey=";
    /*
    * 选项表接口
    * */
    public static final String OPTION_URL = "/cpw/CPW_Choice/getlistbytopicno?topic=";

    /*
    * 获取调查编号接口
    * */
    public static final String PATISURVERYNO_URL = "/Sys/Base_CodeRule/getrulecode?RuleCode=PSS";

    /*
    * 提交调查基本信息接口
    * */                                                    ///cpw/CPW_PatiSurvery/addpatisurvery
    public static final String SUBMITORMSG_URL = "/cpw/CPW_PatiSurvery/addpatisurvery";
    /*
    * 提交答案接口
    * */                                               ///cpw/CPW_PatiAnswer/addpatianswer
    public static final String ANSWERS_URL = "/cpw/CPW_PatiAnswer/addpatianswer";

    /*
    * 获得护理记录编码
    * */
    public static final String NURSE_HISTORY = "/Sys/Base_CodeRule/getrulecode?RuleCode=CPRJJL";
    /*
    *
    * 提交护理记录
    * */                                                        ///CPW/CPW_PatiNursingrecords/addnursingrecords
    public static final String NURSE_HISTORY_COMMIT = "/CPW/CPW_PatiNursingrecords/add";

    /*
    * 根据cpwCode获取详细路径信息
    * */
    public static final String NURSE_SELECT_STAGE = "/CPW/CPW_InfoStage/getlist?cpwcode=";

    /*
   * 根据病人编号和stageCode去获取未执行的医嘱
   * */
    public static final String NURSE_UNEXECUTE_YIZHU = "/CPW/CPW_PatiOrder/getlistnoexec";

    /*
  * 提交护士已选择的未执行的医嘱
  * */
    public static final String NURSE_UNEXECUTEYIZHU_COMIT = "/CPW/CPW_PatiExecOrder/execorder?OrderNo=";


    /*
     * 获取退出路径患者的信息
     * */
    public static final String PATIONTE_EXITCPW_MSG = "/CPW/CPW_Patients/getpatientsexitcpw";
    /*
     * 获取患者退出路径的原因
     * */
    public static final String PATIONTE_EXITCPW_CAUSE = "/CPW/CPW_PatiVariation/GetListOrder";

    /*
        * 提交护理内容                                            /CPW/CPW_PatiNursing/add
        * */
    public static final String NURSE_UNEXECUTECONTENT_COMIT = "/CPW/CPW_PatiNursing/add";

    /*
       * 获取可执行的护理内容
       * */
    public static final String NURSING_CONTENT_SELECT= "/CPW/CPW_InfoContent/getlist?cpwcode=";
    /*
    * 获取轮播图
    * */
    public static final String LUNBOTU= "http://www.hyzczg.com/plugins/advert/advert_js.ashx?id=1";

    /*
    * 根据患者编号得到，患者姓名，用于条形框赋值
    * */
    public static final String PATIONTE_SINGLE= "/cpw/CPW_Patients/getsinglepatients?PatientsNo=";
    /*
    * 根据用户提供的阶段和病人编号，得到已经执行的医嘱
    * */
    public static final String NURSE_EXECUTE_YIZHU= "/CPW/CPW_PatiOrder/getlistexecuted?PatientsNo=";
    /*
    * 根据用户的路径阶段和病人编号，得到已经执行的护理内容
    * */
    public static final String NURSE_EXECUTE_CONTENT= "/CPW/CPW_PatiNursing/getlistexecuted?PatientsNo=";
    /*
    * 得到住院时间和入径时间
    * */
    public static final String INDAYSANDENTERCPWDAYS= "/CPW/CPW_Patients/getpatientsentercpwdays?PatientsNo=";
}
