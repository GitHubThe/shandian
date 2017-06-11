package com.developer.shan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HG on 2017/6/10.
 */

public class EncryptDataModel {

    @SerializedName("retCode")
    @Expose
    private int retCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("encryptData")
    @Expose
    private String encryptData;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEncryptData() {
        return encryptData;
    }

    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }
}
