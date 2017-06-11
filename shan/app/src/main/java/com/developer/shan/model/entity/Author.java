package com.developer.shan.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HG on 2017/6/10.
 */

public class Author {


    @SerializedName("loginname")
    @Expose
    private String loginname;
    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
