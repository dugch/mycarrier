package com.ptcent.carrier.carrierapp.model;

import java.io.Serializable;

/**
 * Created by WangChang on 2016/4/28.
 */
public class ChatModel implements Serializable {
    private String icon="";
    private String content="";
    // 1 from 2.to
    private int type=1;

    private String userid;
    private int id;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
