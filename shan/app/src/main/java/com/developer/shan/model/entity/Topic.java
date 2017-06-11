package com.developer.shan.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by plasway on 2017/6/9.
 */

public class Topic {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author_id")
    @Expose
    private String author_id;
    @SerializedName("tab")
    @Expose
    private String tab;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("last_reply_at")
    @Expose
    private Date last_reply_at;
    @SerializedName("good")
    @Expose
    private boolean good;
    @SerializedName("top")
    @Expose
    private boolean top;
    @SerializedName("reply_count")
    @Expose
    private Integer reply_count;
    @SerializedName("visit_count")
    @Expose
    private Integer visit_count;
    @SerializedName("create_at")
    @Expose
    private Date create_at;
    @SerializedName("author")
    @Expose
    private Author author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLast_reply_at() {
        return last_reply_at;
    }

    public void setLast_reply_at(Date last_reply_at) {
        this.last_reply_at = last_reply_at;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public Integer getReply_count() {
        return reply_count;
    }

    public void setReply_count(Integer reply_count) {
        this.reply_count = reply_count;
    }

    public Integer getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(Integer visit_count) {
        this.visit_count = visit_count;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
