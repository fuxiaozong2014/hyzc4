package com.bjym.hyzc.activity.bean;

/**
 * Created by fushaoqing on 2016/7/29.
 */
public class NurseHistoryBean {

    public String NRecordNo;
    public String PatientsNo;
    public String PatiName;
    public String NursingTime;
    public String Pulsation;
    public String Temperature;
    public String BPL;
    public String BPH;
    public String RecordContent;


    public NurseHistoryBean(String recordContent, String BPH, String NRecordNo, String patientsNo, String patiName, String nursingTime, String pulsation, String temperature, String BPL) {
        RecordContent = recordContent;
        this.BPH = BPH;
        this.NRecordNo = NRecordNo;
        PatientsNo = patientsNo;
        PatiName = patiName;
        NursingTime = nursingTime;
        Pulsation = pulsation;
        Temperature = temperature;
        this.BPL = BPL;
    }

    @Override
    public String toString() {
        return "NurseHistoryBean{" +
                "NRecordNo='" + NRecordNo + '\'' +
                ", PatientsNo='" + PatientsNo + '\'' +
                ", PatiName='" + PatiName + '\'' +
                ", NursingTime='" + NursingTime + '\'' +
                ", Pulsation='" + Pulsation + '\'' +
                ", Temperature='" + Temperature + '\'' +
                ", BPL='" + BPL + '\'' +
                ", BPH='" + BPH + '\'' +
                ", RecordContent='" + RecordContent + '\'' +
                '}';
    }
}
