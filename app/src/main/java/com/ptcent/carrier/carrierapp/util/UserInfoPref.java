package com.ptcent.carrier.carrierapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * <b>FileName:</b> UserInfoPref.java<br>
 * <b>ClassName:</b> UseInfo<br>
 * @Description  登录的用户信息
 * @author       dugc
 * @date         2017年8月3日 下午1:56:10
 */
public class UserInfoPref {
    private static final String PREFERENCES_NAME = "useinfo.pref";
    private static final String TOTAL= "total";
    private static final String NEARPOPLE= "nearpople";

    private synchronized static void writeIntValue(Context context, String key, int value) {
        if (null == context) { context = CarrierApplication.getContext(); }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private synchronized static int readIntValue(Context context, String key, int defaultValue) {
        if (null == context) { context = CarrierApplication.getContext(); }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }
    private synchronized static void writeStringValue(Context context, String key, String value) {
        if (null == context) { context = CarrierApplication.getContext(); }
        SharedPreferences pref = context.getSharedPreferences(NEARPOPLE, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private synchronized static String readStringValue(Context context, String key, String defaultValue) {
        if (null == context) { context = CarrierApplication.getContext(); }
        SharedPreferences pref = context.getSharedPreferences(NEARPOPLE, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }


    public static void writeTotle(Context context,int totle){
        writeIntValue(context, TOTAL, totle);
    }
    public static int readTotle(Context context){
        return readIntValue(context, TOTAL, -1);
    }

    public static void writeNearpople(Context context,String  nearpople){
        writeStringValue(context, NEARPOPLE, nearpople);
    }
    public static String readNearpople(Context context){
        return readStringValue(context, NEARPOPLE, "");
    }

    public static void clear(Context context) {
        if (null == context) { context = CarrierApplication.getContext(); }
        SharedPreferences pref = context.getSharedPreferences(NEARPOPLE, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }


}
