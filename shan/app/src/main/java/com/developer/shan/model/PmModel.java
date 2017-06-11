package com.developer.shan.model;

import com.developer.shan.model.entity.PmList;
import com.developer.shan.model.entity.Topic;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HG on 2017/6/10.
 */

public class PmModel {

    @SerializedName("data")
    @Expose
    private List<PmList> data = new ArrayList<>();

    public List<PmList> getData() {
        return data;
    }

    public void setData(List<PmList> data) {
        this.data = data;
    }


}
