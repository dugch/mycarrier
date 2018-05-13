package com.ptcent.carrier.carrierapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;

import org.elastos.carrier.UserInfo;
import org.elastos.carrier.exceptions.ElastosException;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.name)
    EditText edname;
    @Bind(R.id.phone)
    EditText edphone;
    @Bind(R.id.email)
    EditText edemail;
    UserInfo user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        init();
        initEvent();
    }


    private void init() {
        setBackBtn();
        setTitle("编辑个人信息");
        setRightText("保存");
        try {
            if(carrierInst.isReady()) {
                user = carrierInst.getSelfInfo();
                edname.setText(user.getName());
                edphone.setText(user.getPhone());
                edemail.setText(user.getEmail());
            }else{
                Toast.makeText(this, "carrier  未准备好", Toast.LENGTH_SHORT).show();
            }
        } catch (ElastosException e) {
            e.printStackTrace();
        }

    }
    private void initEvent() {
        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =edname.getText().toString().trim();
                String phone =edphone.getText().toString().trim();
                String email = edemail.getText().toString().trim();
                    try {
                        UserInfo userinfo = carrierInst.getSelfInfo();
                        userinfo.setName(name);
                        userinfo.setEmail(email);
                        userinfo.setPhone(phone);
                        carrierInst.setSelfInfo(userinfo);
                        Toast.makeText(UserInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (ElastosException e) {
                        e.printStackTrace();
                    }
            }
        });

    }
}
