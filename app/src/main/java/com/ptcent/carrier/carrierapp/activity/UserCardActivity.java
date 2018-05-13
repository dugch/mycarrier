package com.ptcent.carrier.carrierapp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.util.ProgressLoading;

import org.elastos.carrier.exceptions.ElastosException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

public class UserCardActivity extends BaseActivity {

    @Bind(R.id.zqview)
    ImageView zqview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("个人名片");
        setBackBtn();
        loading = ProgressLoading.createLoadingDialog(UserCardActivity.this);
        if (!isFinishing() && loading != null) {
            loading.show();
        }
        if (carrierInst.isReady()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (carrierInst.getAddress() != null && carrierInst.getUserId() != null) {
                            final String address =carrierInst.getAddress();
                            final String userid =carrierInst.getUserId();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                if(address!=null){
                                    Log.e("adress",""+address);
                                    Log.e("userid",""+userid);
                                    Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(address, 200);
                                    zqview.setImageBitmap(bitmap);
                                    if (loading.isShowing() && loading != null) {
                                        loading.dismiss();
                                    }
                                    }
                                }
                            });
                        }
                    }catch (ElastosException   e){
                        e.printStackTrace();
                    }

                }
            }).start();
        }else{
            Toast.makeText(UserCardActivity.this,"carrier 未准备好",Toast.LENGTH_SHORT).show();
            if (loading.isShowing() && loading != null) {
                loading.dismiss();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
