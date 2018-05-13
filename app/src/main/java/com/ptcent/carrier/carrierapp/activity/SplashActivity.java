package com.ptcent.carrier.carrierapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.model.CheckPermission;
import com.ptcent.carrier.carrierapp.util.APIHelper;
import com.ptcent.carrier.carrierapp.util.GlobalVarManager;

/**
 * Created by Administrator on 2017/6/27.
 * 欢迎页
 */

public class SplashActivity extends Activity {

    private String[] permissions = null;
    private int currentPermissionIndex = -1;
    private CheckPermission checkPermission = new CheckPermission();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        checkPermissions();


    }

    private void checkPermissions() {
        String[] permissions = APIHelper.permissions();
        if (APIHelper.hasPermissions(this, permissions)) {
            this.savePermissionState(APIHelper.PERMISSION_TAKE_CAMERA, true);
            this.savePermissionState(APIHelper.PERMISSION_WRITE_EXTERNAL_STORAGE, true);
            this.savePermissionState(APIHelper.READ_PHONE_STATE, true);
            this.savePermissionState(APIHelper.WRITE_SETTINGS, true);
            this.startMain();
        } else {
            this.permissions = permissions;
            this.currentPermissionIndex = -1;
            this.requestNextPermission();
        }
    }

    private void requestNextPermission() {
        ++this.currentPermissionIndex;
        if (this.permissions == null || this.currentPermissionIndex >= this.permissions.length) {
            this.startMain();
            return;
        }

        final String permission = this.permissions[this.currentPermissionIndex];
        this.requestPermission(permission);
    }

    private void requestPermission(final String permission) {
        boolean request = true;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            if (this.showPermissionRationale(this, permission)) {
                request = false;
            }
        }
        if (request) {
            String[] permissions = APIHelper.getPermissions(permission);
            int permissionId = APIHelper.getPermissionID(permission);
            ActivityCompat.requestPermissions(this, permissions, permissionId);
        }
    }

    private void savePermissionState(final int permissionId, final boolean allowed) {
        if (permissionId == APIHelper.PERMISSION_WRITE_EXTERNAL_STORAGE) {
            checkPermission.setPermissionWriteExternalStorageAllowed(allowed);
        } else if (permissionId == APIHelper.PERMISSION_TAKE_CAMERA) {
            checkPermission.setPermissionAccessCamera(allowed);
        } else if (permissionId == APIHelper.READ_PHONE_STATE) {
            checkPermission.setPermissionAccessReadPhoneState(allowed);
        }else if(permissionId == APIHelper.WRITE_SETTINGS){
            checkPermission.setPermissionAccesswrite_settings(allowed);
        }
        GlobalVarManager.writeCheckPermission(SplashActivity.this, checkPermission);
    }

    private boolean showPermissionRationale(Context context, final String permission) {
        String rationale = APIHelper.getPermissionRationale(permission);
        if (rationale == null)
            return false;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.comm_dialog);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.alert_dialog);
        TextView tvAlertDialogMessage = (TextView) alertDialog.findViewById(R.id.tvAlertDialogMessage);
        tvAlertDialogMessage.setText(rationale);
        Button btnCancel = (Button) alertDialog.findViewById(R.id.cancel);
        Button btnOK = (Button) alertDialog.findViewById(R.id.sure);
        btnOK.setText("知道了");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                String[] permissions = APIHelper.getPermissions(permission);
                int permissionId = APIHelper.getPermissionID(permission);
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, permissionId);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                int permissionId = APIHelper.getPermissionID(permission);
                savePermissionState(permissionId, false);
                requestNextPermission();
            }
        });
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0 && grantResults != null) {
            boolean isGranted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
            String result = isGranted ? "GRANTED" : "DENIED";
            String log = permissions[0] + " " + result;
            if (isGranted) {
            } else {
            }
            this.savePermissionState(requestCode, isGranted);
            this.requestNextPermission();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startMain(){
        new Handler().postDelayed(checkAppVersion, 700);
    }
      Runnable checkAppVersion = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
