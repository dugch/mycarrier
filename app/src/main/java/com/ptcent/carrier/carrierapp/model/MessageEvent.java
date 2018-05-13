package com.ptcent.carrier.carrierapp.model;

import java.io.Serializable;

/**
 * @author by dugc
 * @package com.ptcent.carrier.carrierapp.model
 * @describe
 * @date 2018/5/9    21:42
 */

public class MessageEvent implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
