package com.ptcent.carrier.carrierapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ptcent.carrier.carrierapp.model.CheckPermission;


public final class GlobalVarManager {
	private static final String PREFERENCES_NAME = "checkpermission.pref";
	private static final String PERMISSIONWRITE = "permissionWrite";
	private static final String PERMISSIONLOCATION = "permissionlocation";
	private static final String PERMISSCAMERA="permissioncamera";



	public static GlobalVarManager getInstance(Context context) {
		if (context == null) context = CarrierApplication.getContext();
		if (context == null) return null;
		CarrierApplication application = (CarrierApplication)context.getApplicationContext();
		GlobalVarManager manager = application.getGlobalVarManager();
		if (manager == null) {
			manager = new GlobalVarManager();
			application.setGlobalVarManager(manager);
		}
		return manager;
	}


	public static void writeCheckPermission(Context context, CheckPermission checkPermission) {
		if (null == checkPermission)
			return;
		if (null == context) {
			context = CarrierApplication.getContext();
		}
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(PERMISSIONWRITE, checkPermission.isPermissionWriteExternalStorageAllowed());
		editor.putBoolean(PERMISSIONLOCATION,checkPermission.isPermissionAccessLocationAllowed());
		editor.putBoolean(PERMISSCAMERA,checkPermission.isPermissionAccessCamera());
		editor.commit();
	}

	public static CheckPermission readCheckPermission(Context context) {
		if (null == context) {
			context = CarrierApplication.getContext();
		}
		CheckPermission checkPermission = new CheckPermission();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		checkPermission.setPermissionWriteExternalStorageAllowed(pref.getBoolean(PERMISSIONWRITE,false));
		checkPermission.setPermissionAccessLocationAllowed(pref.getBoolean(PERMISSIONLOCATION,false));
		checkPermission.setPermissionAccessCamera(pref.getBoolean(PERMISSCAMERA,false));
		return checkPermission;
	}

}
