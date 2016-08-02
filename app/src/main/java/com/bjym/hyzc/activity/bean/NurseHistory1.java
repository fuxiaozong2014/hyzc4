package com.bjym.hyzc.activity.bean;

/**
 * Created by fushaoqing on 2016/7/29.
 */
public class NurseHistory1 {

    public String NRecordNo;
    public String PatientsNo;
    public String PatiName;
    public String NursingTime;
    public String Pulsation;
    public String Temperature;
    public String BPL;
    public String BHP;
    public String RecordContent;


    public NurseHistory1(String recordContent, String BHP, String NRecordNo, String patientsNo, String patiName, String nursingTime, String pulsation, String temperature, String BPL) {
        RecordContent = recordContent;
        this.BHP = BHP;
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
        return "NurseHistory1{" +
                "NRecordNo='" + NRecordNo + '\'' +
                ", PatientsNo='" + PatientsNo + '\'' +
                ", PatiName='" + PatiName + '\'' +
                ", NursingTime='" + NursingTime + '\'' +
                ", Pulsation='" + Pulsation + '\'' +
                ", Temperature='" + Temperature + '\'' +
                ", BPL='" + BPL + '\'' +
                ", BHP='" + BHP + '\'' +
                ", RecordContent='" + RecordContent + '\'' +
                '}';
    }
}
