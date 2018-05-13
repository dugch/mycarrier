package com.ptcent.carrier.carrierapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerChat;
import com.ptcent.carrier.carrierapp.model.ChatModel;
import com.ptcent.carrier.carrierapp.model.MessageEvent;
import com.ptcent.carrier.carrierapp.widget.ChatAdapter;

import org.elastos.carrier.exceptions.ElastosException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

public class ChatActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText et;
    private TextView tvSend;
    private String content;
    private String userid;
    private String name;
    private DatabaseManagerChat databaseManagerChat;
    private LinearLayoutManager layoutManager;
    private ArrayList<ChatModel> chatModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        initData();
    }

    private void init() {
        setBackBtn();
        Intent intent = getIntent();
        if(intent!=null){
             userid = intent.getStringExtra("userid");
             name = intent.getStringExtra("name");
        }
        if(name!=null){
            setTitle(name);
        }
        databaseManagerChat= new DatabaseManagerChat(ChatActivity.this);
        chatModelList =databaseManagerChat.queryData();
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        EventBus.getDefault().register(this);
        et = (EditText) findViewById(R.id.et);
        tvSend = (TextView) findViewById(R.id.tvSend);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(chatModelList.size()-1,0);
        adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);
        adapter.replaceAll(chatModelList);


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        if(messageEvent!=null){
            ArrayList<ChatModel> data = new ArrayList<>();
            ChatModel model = new ChatModel();
            model.setType(2);
            model.setContent(messageEvent.getMessage());
            data.add(model);
            adapter.addAll(data);
            ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(adapter.getItemCount()-1,0);
        }
    }

    private void initData() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(content==null||content.equals("")){
                    Toast.makeText(ChatActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(carrierInst.isReady()){
                    try {
                        carrierInst.sendFriendMessage(userid,"*&*"+content);
                        ArrayList<ChatModel> data = new ArrayList<>();
                        ChatModel model = new ChatModel();
                        model.setContent(content);
                        model.setType(1);
                        model.setUserid(userid);
                        data.add(model);
                        adapter.addAll(data);
                        ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(adapter.getItemCount()-1,0);
                        et.setText("");
                        databaseManagerChat.insertData(model);
                    } catch (ElastosException e) {
                        e.printStackTrace();
                        if(e.getErrorCode()==-2046296061){
                            Toast.makeText(ChatActivity.this, "好友已下线", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ChatActivity.this, "错误码："+e.getErrorCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(ChatActivity.this, "carrier 未准备好", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
