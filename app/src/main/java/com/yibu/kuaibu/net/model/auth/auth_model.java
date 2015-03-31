package com.yibu.kuaibu.net.model.auth;

import java.io.Serializable;

/**
 * Created by zys on 2015/3/30.
 */
public class auth_model implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    /*公司名称*/
    private String title;
    /*法人姓名*/
    private String truename;
    /*公司名称*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getGetThumb1() {
        return getThumb1;
    }

    public void setGetThumb1(String getThumb1) {
        this.getThumb1 = getThumb1;
    }

    private String  idcard;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String thumb;
    private String getThumb1;

    private String business_number;
    public  void getBusiness_Numer(String businessNumber){
        this.business_number=businessNumber;
    }

}

