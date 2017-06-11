package com.developer.shan.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by HG on 2017/6/10.
 */

public class PmList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pm_name")
    @Expose
    private String pm_name;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("zz_name")
    @Expose
    private String zz_name;
    @SerializedName("xh_name")
    @Expose
    private String xh_name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("bj_com")
    @Expose
    private String bj_com;
    @SerializedName("bj_num")
    @Expose
    private String bj_num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPm_name() {
        return pm_name;
    }

    public void setPm_name(String pm_name) {
        this.pm_name = pm_name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZz_name() {
        return zz_name;
    }

    public void setZz_name(String zz_name) {
        this.zz_name = zz_name;
    }

    public String getXh_name() {
        return xh_name;
    }

    public void setXh_name(String xh_name) {
        this.xh_name = xh_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBj_com() {
        return bj_com;
    }

    public void setBj_com(String bj_com) {
        this.bj_com = bj_com;
    }

    public String getBj_num() {
        return bj_num;
    }

    public void setBj_num(String bj_num) {
        this.bj_num = bj_num;
    }
}
