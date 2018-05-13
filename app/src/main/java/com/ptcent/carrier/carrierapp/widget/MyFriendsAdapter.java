package com.ptcent.carrier.carrierapp.widget;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ptcent.carrier.carrierapp.R;

import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;

import java.util.List;

/**
 * Created by dugc on 2017/9/13.
 */

public class MyFriendsAdapter extends BaseQuickAdapter<FriendInfo, BaseViewHolder> {

    private Context context;

    public MyFriendsAdapter(List<FriendInfo> friendList, Context context) {
        super(R.layout.myfriend_adapter_layout, friendList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final FriendInfo friend) {
        if(friend.getName().equals("")||friend.getName()==null){
            helper.setText(R.id.friend_name,"此人很懒没有设置用户名");
        }else{
            helper.setText(R.id.friend_name,friend.getName());
        }
        helper.setText(R.id.friend_userid, friend.getUserId());
        TextView connection =helper.getView(R.id.online);
        if(friend.getConnectionStatus()== ConnectionStatus.Connected){
            connection.setText("在线");
        }else{
            connection.setText("离线");
        }
    }
}
