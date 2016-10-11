package com.bjym.hyzc.activity.ExecuteNursingCare;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fushaoqing on 2016/8/4.
 */
public class NurseExecuteBean {


    /**
     * total : 24
     * rows : [{"StageCode":"JD000017","StageName":"住院第1天","StageBeginDays":1,"StageEndDays":1,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"0","tempid":0},{"StageCode":"JD000020","StageName":"住院第2-4天","StageBeginDays":2,"StageEndDays":4,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"0","tempid":0},{"StageCode":"JD000021","StageName":"住院第3-5天（手术日）","StageBeginDays":3,"StageEndDays":5,"StageEvents":"手术","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"0","tempid":0},{"StageCode":"JD000022","StageName":"主要诊疗工作","StageBeginDays":1,"StageEndDays":1,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000017","tempid":0},{"StageCode":"JD000023","StageName":"重点医嘱","StageBeginDays":1,"StageEndDays":1,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000017","tempid":0},{"StageCode":"JD000025","StageName":"主要护理工作","StageBeginDays":1,"StageEndDays":1,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000017","tempid":0},{"StageCode":"JD000029","StageName":"主要诊疗工作","StageBeginDays":2,"StageEndDays":4,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000020","tempid":0},{"StageCode":"JD000030","StageName":"重点医嘱","StageBeginDays":2,"StageEndDays":4,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000020","tempid":0},{"StageCode":"JD000031","StageName":"主要护理工作","StageBeginDays":2,"StageEndDays":4,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000020","tempid":0},{"StageCode":"JD000033","StageName":"主要诊疗工作","StageBeginDays":3,"StageEndDays":5,"StageEvents":"手术","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000021","tempid":0},{"StageCode":"JD000034","StageName":"重点医嘱","StageBeginDays":3,"StageEndDays":5,"StageEvents":"手术","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000021","tempid":0},{"StageCode":"JD000035","StageName":"主要护理工作","StageBeginDays":3,"StageEndDays":5,"StageEvents":"手术","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000021","tempid":0},{"StageCode":"JD000036","StageName":"住院第4-8天（术后第1-3天）","StageBeginDays":4,"StageEndDays":8,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"0","tempid":0},{"StageCode":"JD000037","StageName":"主要诊疗工作","StageBeginDays":4,"StageEndDays":8,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000036","tempid":0},{"StageCode":"JD000038","StageName":"重点医嘱","StageBeginDays":4,"StageEndDays":8,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000036","tempid":0},{"StageCode":"JD000039","StageName":"主要护理工作","StageBeginDays":4,"StageEndDays":8,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000036","tempid":0},{"StageCode":"JD000040","StageName":"住院第5-13天（术后第2-10天）","StageBeginDays":5,"StageEndDays":13,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"0","tempid":0},{"StageCode":"JD000041","StageName":"主要诊疗工作","StageBeginDays":5,"StageEndDays":13,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000040","tempid":0},{"StageCode":"JD000042","StageName":"重点医嘱","StageBeginDays":5,"StageEndDays":13,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000040","tempid":0},{"StageCode":"JD000043","StageName":"主要护理工作","StageBeginDays":5,"StageEndDays":13,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000040","tempid":0},{"StageCode":"JD000047","StageName":"住院8-14天（出院日）","StageBeginDays":8,"StageEndDays":14,"StageEvents":"预出院","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"0","tempid":0},{"StageCode":"JD000048","StageName":"主要诊疗工作","StageBeginDays":8,"StageEndDays":14,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000047","tempid":0},{"StageCode":"JD000049","StageName":"重点医嘱","StageBeginDays":8,"StageEndDays":14,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000047","tempid":0},{"StageCode":"JD000050","StageName":"主要护理工作","StageBeginDays":8,"StageEndDays":14,"StageEvents":"","CPWCode":"CP160713000038","IconClass":"icon-hamburg-sitemap","IconUrl":"","ParentCode":"JD000047","tempid":0}]
     */

    public int total;
    /**
     * StageCode : JD000017
     * StageName : 住院第1天
     * StageBeginDays : 1
     * StageEndDays : 1
     * StageEvents : 
     * CPWCode : CP160713000038
     * IconClass : icon-hamburg-sitemap
     * IconUrl : 
     * ParentCode : 0
     * tempid : 0
     */

    public List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public static class RowsBean  implements Serializable{
        public String StageCode;
        public String StageName;
        public int StageBeginDays;
        public int StageEndDays;
        public String StageEvents;
        public String CPWCode;
        public String IconClass;
        public String IconUrl;
        public String ParentCode;
        public int tempid;
    }
}
