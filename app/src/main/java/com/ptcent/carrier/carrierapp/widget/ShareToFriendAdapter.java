package com.ptcent.carrier.carrierapp.widget;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.model.Friendinfo;

import org.elastos.carrier.ConnectionStatus;

import java.util.List;

/**
 * 手动录入适配器
 */

public class ShareToFriendAdapter extends BaseAdapter {
    private Context context;
    private List<Friendinfo> friendInfoList;

    public ShareToFriendAdapter(Context context, List<Friendinfo> friendInfoList){
        this.context = context;
        this.friendInfoList = friendInfoList;
    }
    @Override
    public int getCount() {
        if (friendInfoList != null) {
            return friendInfoList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return friendInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.share_item_layout, parent, false);
            viewHolder.friend_name = (TextView) convertView.findViewById(R.id.friend_name);
            viewHolder.friend_userid = (TextView) convertView.findViewById(R.id.friend_userid);
            viewHolder.online = (TextView) convertView.findViewById(R.id.online);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

           if(friendInfoList!=null&&friendInfoList.size()!=0){
               final Friendinfo friendInfo=friendInfoList.get(position);
               if(friendInfo.getName().equals("")||friendInfo.getName()==null){
                    Log.e("log",""+friendInfo.getName());
                    viewHolder.friend_name.setText("此人很懒没有设置用户名");
                }else{
                   Log.e("log",""+friendInfo.getName());
                   viewHolder.friend_name.setText(friendInfo.getName());
                }
                viewHolder.friend_userid.setText(friendInfo.getUserId());
                if(friendInfo.getConnectionStatus()== ConnectionStatus.Connected){
                    viewHolder.online.setText("在线");
                }else{
                    viewHolder.online.setText("离线");
                }
                viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        friendInfo.setState(1);
                    }
                });
            }
        return convertView;
    }
    class ViewHolder{
        TextView friend_name, friend_userid,online;
        CheckBox checkBox;


    }
}
