package com.ptcent.carrier.carrierapp.model;

import org.elastos.carrier.ConnectionStatus;

import java.io.Serializable;

/**
 * @author by dugc
 * @package com.ptcent.carrier.carrierapp.model
 * @describe
 * @date 2018/5/8    18:10
 */

public class Friendinfo   implements Serializable{

    private int state;
    private String userId;
    private String name;
    private ConnectionStatus connectionStatus;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

}
