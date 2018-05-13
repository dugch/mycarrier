package com.ptcent.carrier.carrierapp.model;

import java.io.Serializable;

/**
 * @author by dugc
 * @package com.ptcent.carrier.carrierapp.model
 * @describe
 * @date 2018/4/28    16:28
 */

public class Goods implements Serializable{

    private String goodsname;
    private int count;
    private int price;
    private String userid;
    private int id;
    private boolean isbug=false;
    private int state;


    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isbug() {
        return isbug;
    }

    public void setIsbug(boolean isbug) {
        this.isbug = isbug;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
