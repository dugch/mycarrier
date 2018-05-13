package com.ptcent.carrier.carrierapp.model;

import java.io.Serializable;

/**
 * @author by dugc
 * @package com.ptcent.carrier.carrierapp.model
 * @describe
 * @date 2018/5/8    13:53
 */

public class Friend implements Serializable {

    private String userid;
    private String hello;
    private int id;
    private String name;

    // 0 未通过 1已通过
    private int state;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
