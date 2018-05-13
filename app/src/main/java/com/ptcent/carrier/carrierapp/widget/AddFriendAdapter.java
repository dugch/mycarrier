package com.ptcent.carrier.carrierapp.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerAddFriends;
import com.ptcent.carrier.carrierapp.model.Friend;

import org.elastos.carrier.exceptions.ElastosException;

import java.util.List;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

/**
 * Created by dugc on 2017/9/13.
 */

public class AddFriendAdapter extends BaseQuickAdapter<Friend, BaseViewHolder> {

    private Context context;
    private DatabaseManagerAddFriends databaseManagerAddFriends;

    public AddFriendAdapter(List<Friend> friendList, Context context , DatabaseManagerAddFriends databaseManagerAddFriends){
        super( R.layout.add_friend_adapter_layout, friendList);
        this.context=context;
        this.databaseManagerAddFriends =databaseManagerAddFriends;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Friend friend) {
        if(friend.getName().equals("")||friend.getName()==null){
            helper.setText(R.id.name,"此人很懒没有设置用户名");
        }else{
            helper.setText(R.id.name,friend.getName());
        }
        helper.setText(R.id.hello, friend.getHello());
        Button add =helper.getView(R.id.add);
        if(friend.getState()==1){
            add.setText("已添加");
            add.setEnabled(false);
            add.setTextColor(Color.parseColor("#9f9f9f"));
            add.setBackgroundResource(R.drawable.shap_gray);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    carrierInst.AcceptFriend(friend.getUserid());
                    databaseManagerAddFriends.updateData(friend.getId());
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                    friend.setState(1);
                    notifyDataSetChanged();
                } catch (ElastosException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
