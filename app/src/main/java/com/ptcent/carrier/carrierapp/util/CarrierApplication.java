package com.ptcent.carrier.carrierapp.util;

import android.app.Application;
import android.content.Context;


/**
 * Created by Administrator on 2017/6/27.
 */

 public class CarrierApplication extends Application {

    private static Context mContext;
    private GlobalVarManager globalVarManager = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }


    public static Context getContext() {
        return mContext;
    }

    public GlobalVarManager getGlobalVarManager() {
        return globalVarManager;
    }

    public void setGlobalVarManager(GlobalVarManager globalVarManager) {
        this.globalVarManager = globalVarManager;
    }


}
