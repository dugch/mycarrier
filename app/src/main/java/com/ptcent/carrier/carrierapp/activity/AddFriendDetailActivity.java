package com.ptcent.carrier.carrierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;

import org.elastos.carrier.exceptions.ElastosException;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

public class AddFriendDetailActivity extends BaseActivity {

    @Bind(R.id.ed_verification)
    EditText edVerification;
    private String address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("朋友验证");
        setBackBtn();
        setRightText("发送");
        Intent intent=getIntent();
        if(intent!=null){
            address = intent.getStringExtra("address");
        }
            if(carrierInst.isReady()) {
                try {
                    edVerification.setText("我是："+carrierInst.getSelfInfo().getName());
                } catch (ElastosException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this, "carrier  未准备好", Toast.LENGTH_SHORT).show();
            }

        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  verificatstr = "";
                verificatstr= edVerification.getText().toString().trim();
                    try {
                        carrierInst.addFriend(address, verificatstr);
                        Toast.makeText(AddFriendDetailActivity.this,"好友申请已发送",Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (ElastosException e) {
                        e.printStackTrace();
                    }
                }
        });

    }
}
