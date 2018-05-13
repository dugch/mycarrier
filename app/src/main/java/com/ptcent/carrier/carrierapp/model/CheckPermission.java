package com.ptcent.carrier.carrierapp.model;

import java.io.Serializable;

/**
 * Created by asd on 2017/5/8.
 * 权限
 */

public class CheckPermission implements Serializable {
    private boolean permissionWriteExternalStorageAllowed = false;
    private boolean permissionAccessLocationAllowed = false;
    private boolean permissionAccessCamera=false;
    private boolean permissionAccessReadPhoneState=false;
    private boolean permissionAccesswrite_settings=false;

    public boolean isPermissionWriteExternalStorageAllowed() {
        return permissionWriteExternalStorageAllowed;
    }

    public void setPermissionWriteExternalStorageAllowed(boolean permissionWriteExternalStorageAllowed) {
        this.permissionWriteExternalStorageAllowed = permissionWriteExternalStorageAllowed;
    }

    public boolean isPermissionAccessLocationAllowed() {
        return permissionAccessLocationAllowed;
    }

    public void setPermissionAccessLocationAllowed(boolean permissionAccessLocationAllowed) {
        this.permissionAccessLocationAllowed = permissionAccessLocationAllowed;
    }

    public boolean isPermissionAccessCamera() {
        return permissionAccessCamera;
    }

    public void setPermissionAccessCamera(boolean permissionAccessCamera) {
        this.permissionAccessCamera = permissionAccessCamera;
    }

    public boolean isPermissionAccessReadPhoneState() {
        return permissionAccessReadPhoneState;
    }

    public void setPermissionAccessReadPhoneState(boolean permissionAccessReadPhoneState) {
        this.permissionAccessReadPhoneState = permissionAccessReadPhoneState;
    }

    public boolean isPermissionAccesswrite_settings() {
        return permissionAccesswrite_settings;
    }

    public void setPermissionAccesswrite_settings(boolean permissionAccesswrite_settings) {
        this.permissionAccesswrite_settings = permissionAccesswrite_settings;
    }
}
