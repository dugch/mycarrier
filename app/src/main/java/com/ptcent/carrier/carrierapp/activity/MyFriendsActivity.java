package com.ptcent.carrier.carrierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerAddFriends;
import com.ptcent.carrier.carrierapp.model.CheckPermission;
import com.ptcent.carrier.carrierapp.model.Friend;
import com.ptcent.carrier.carrierapp.util.GlobalVarManager;
import com.ptcent.carrier.carrierapp.widget.AddFriendAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsActivity extends BaseActivity {

    private static final String TAG = "MyFriendsActivity";
    private RecyclerView recyView;
    private DatabaseManagerAddFriends databaseManagerAddFriends;
    private List<Friend> friendList = new ArrayList<>();
    private AddFriendAdapter addFriendAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        init();
    }

    private void init() {
        setBackBtn();
        setTitle("新的朋友");
        setRightText("添加朋友");
        recyView = (RecyclerView) findViewById(R.id.recyView);
        databaseManagerAddFriends = new DatabaseManagerAddFriends(MyFriendsActivity.this);
        friendList =  databaseManagerAddFriends.queryData();

        recyView.setLayoutManager(new LinearLayoutManager(MyFriendsActivity.this));
        addFriendAdapter = new AddFriendAdapter(friendList, MyFriendsActivity.this,databaseManagerAddFriends);
        recyView.setAdapter(addFriendAdapter);


        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPermission checkPermission = GlobalVarManager.readCheckPermission(MyFriendsActivity.this);
                if (checkPermission.isPermissionAccessCamera()) {
                    Intent intent = new Intent(MyFriendsActivity.this,AddFriendActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MyFriendsActivity.this, "进入应用管理器打开相机权限", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
