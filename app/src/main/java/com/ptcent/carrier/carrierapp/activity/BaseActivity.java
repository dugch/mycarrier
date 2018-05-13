package com.ptcent.carrier.carrierapp.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ptcent.carrier.carrierapp.R;


/**
 * <b>FileName:</b> BaseActivity.java<br>
 * <b>ClassName:</b> BaseActivity<br>
 *
 * @Description 基础activity类
 */

public class BaseActivity extends AppCompatActivity {
    public TextView title,text_right;
    public ImageButton back, right_img,right0_img;
    private LinearLayout rootLayout;
    public Dialog loading;
    public Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        initToolbar();
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        back = (ImageButton) findViewById(R.id.back);
        right_img = (ImageButton) findViewById(R.id.right_img);
        right0_img = (ImageButton) findViewById(R.id.right0_img);
        title = (TextView) findViewById(R.id.title);
        text_right = (TextView) findViewById(R.id.text_right);

    }

    public void setBackBtn() {
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(String titleStr) {
        title.setText(titleStr);
    }

    public void setRightImage(int drawable) {
        right_img.setVisibility(View.VISIBLE);
        right_img.setImageDrawable(ContextCompat.getDrawable(this, drawable));
    }

    public void setRightImage0(int drawable) {
        right0_img.setVisibility(View.VISIBLE);
        right0_img.setImageDrawable(ContextCompat.getDrawable(this, drawable));
    }

    public void setRightText(String text) {
        text_right.setVisibility(View.VISIBLE);
        text_right.setText(text);
    }


}
