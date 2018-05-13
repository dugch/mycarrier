package com.ptcent.carrier.carrierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.util.ProgressLoading;

import cn.bingoogolapple.qrcode.core.QRCodeView;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

/**
 * 扫码
 */
public class AddFriendActivity extends BaseActivity implements QRCodeView.Delegate{

    private QRCodeView zbarview;
    private  static final String TAG = "AddFriendActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanningbar_activity);
        init();
    }

    private void init() {
        setTitle("添加朋友");
        setBackBtn();
        zbarview = (QRCodeView) findViewById(R.id.zbarview);
        loading = ProgressLoading.createLoadingDialog(AddFriendActivity.this);
        zbarview.setDelegate(AddFriendActivity.this);
        zbarview.startSpotDelay(1000);
    }


    @Override
    protected void onStart() {
        super.onStart();
        zbarview.startCamera();
        zbarview.showScanRect();

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        if(result!=null){
                if(carrierInst.isReady()){
                    Intent intent = new Intent(AddFriendActivity.this,AddFriendDetailActivity.class);
                    intent.putExtra("address",result);
                    startActivity(intent);
                }else{
                    Toast.makeText(AddFriendActivity.this,"carrier 未准备好",Toast.LENGTH_SHORT).show();
                }
        }else{
            Toast.makeText(AddFriendActivity.this,"扫描结果无效",Toast.LENGTH_SHORT).show();
        }

    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(AddFriendActivity.this,"扫码失败",Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        zbarview.startSpotDelay(1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        zbarview.onDestroy();
    }

    @Override
    protected void onStop() {
        zbarview.stopCamera();
        super.onStop();
    }

}
