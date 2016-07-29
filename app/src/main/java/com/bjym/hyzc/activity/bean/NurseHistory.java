package com.bjym.hyzc.activity.bean;

/**
 * Created by fushaoqing on 2016/7/29.
 */
public class NurseHistory {

    public String NRecordNo;
    public String PatientsNo;
    public String PatiName;
    public String NursingTime;
    public int Pulsation;
    public float Temperature;
    public int BPL;
    public int BHP;
    public String RecordContent;


    public NurseHistory(String NRecordNo, String patientsNo, String patiName, String nursingTime, int pulsation, float temperature, int BPL, int BHP, String recordContent) {
        this.NRecordNo = NRecordNo;
        PatientsNo = patientsNo;
        PatiName = patiName;
        NursingTime = nursingTime;
        Pulsation = pulsation;
        Temperature = temperature;
        this.BPL = BPL;
        this.BHP = BHP;
        RecordContent = recordContent;
    }

    @Override
    public String toString() {
        return "NurseHistory{" +
                "NRecordNo='" + NRecordNo + '\'' +
                ", PatientsNo='" + PatientsNo + '\'' +
                ", PatiName='" + PatiName + '\'' +
                ", NursingTime='" + NursingTime + '\'' +
                ", Pulsation=" + Pulsation +
                ", Temperature=" + Temperature +
                ", BPL=" + BPL +
                ", BHP=" + BHP +
                ", RecordContent='" + RecordContent + '\'' +
                '}';
    }
}
