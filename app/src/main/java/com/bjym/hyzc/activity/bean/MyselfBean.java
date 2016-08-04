package com.bjym.hyzc.activity.bean;

/**
 * Created by fushaoqing on 2016/7/7.
 */
public class MyselfBean {


    /**
     * UserId : 8005
     * UserType : 3
     * CompanyCode : 999
     * DepartmentCode : 999002
     * UserCode : 8005
     * Password : 202cb962ac59075b964b07152d234b70
     * Secretkey : null
     * RealName : 姓名8005
     * Spell : 
     * Sex : 男
     * Phone : 
     * Email : 
     * QQ : 
     * Sort : 999
     * Enabled : 1
     * Remark : 测试用户
     * IsAudit : 1
     * AuditBy : 超级管理员
     * AuditTime : 2015-11-08 17:31:24
     * IsSuperAdmin : 0
     * IsSingleLogin : 0
     * IsOnline : 0
     * LoginCount : 101
     * LoginTime : 2015-12-19 14:07:26
     * LoginIP : 114.111.166.171
     * LoginCity : 上海市
     * LastChangePassword : null
     * AddBy : 超级管理员
     * AddOn : 2015-11-08 17:31:24
     * EditBy : 超级管理员
     * EditOn : 2015-12-26 22:08:57
     * tempid : 0
     */

    public int UserId;
    public int UserType;
    public String CompanyCode;
    public String DepartmentCode;
    public String UserCode;
    public String Password;
    public Object Secretkey;
    public String RealName;
    public String Spell;
    public String Sex;
    public String Phone;
    public String Email;
    public String QQ;
    public int Sort;
    public int Enabled;
    public String Remark;
    public int IsAudit;
    public String AuditBy;
    public String AuditTime;
    public int IsSuperAdmin;
    public int IsSingleLogin;
    public int IsOnline;
    public int LoginCount;
    public String LoginTime;
    public String LoginIP;
    public String LoginCity;
    public Object LastChangePassword;
    public String AddBy;
    public String AddOn;
    public String EditBy;
    public String EditOn;
    public int tempid;

    public MyselfBean() {
    }


    @Override
    public String toString() {
        return "MyselfBean{" +
                "UserId=" + UserId +
                ", UserType=" + UserType +
                ", CompanyCode='" + CompanyCode + '\'' +
                ", DepartmentCode='" + DepartmentCode + '\'' +
                ", UserCode='" + UserCode + '\'' +
                ", Password='" + Password + '\'' +
                ", Secretkey=" + Secretkey +
                ", RealName='" + RealName + '\'' +
                ", Spell='" + Spell + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", QQ='" + QQ + '\'' +
                ", Sort=" + Sort +
                ", Enabled=" + Enabled +
                ", Remark='" + Remark + '\'' +
                ", IsAudit=" + IsAudit +
                ", AuditBy='" + AuditBy + '\'' +
                ", AuditTime='" + AuditTime + '\'' +
                ", IsSuperAdmin=" + IsSuperAdmin +
                ", IsSingleLogin=" + IsSingleLogin +
                ", IsOnline=" + IsOnline +
                ", LoginCount=" + LoginCount +
                ", LoginTime='" + LoginTime + '\'' +
                ", LoginIP='" + LoginIP + '\'' +
                ", LoginCity='" + LoginCity + '\'' +
                ", LastChangePassword=" + LastChangePassword +
                ", AddBy='" + AddBy + '\'' +
                ", AddOn='" + AddOn + '\'' +
                ", EditBy='" + EditBy + '\'' +
                ", EditOn='" + EditOn + '\'' +
                ", tempid=" + tempid +
                '}';
    }
}
