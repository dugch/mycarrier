package com.ptcent.carrier.carrierapp.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.model.Friendinfo;

import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.exceptions.ElastosException;

import java.util.List;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

/**
 * Created by dugc on 2017/9/13.
 */

public class NearPeopleAdapter extends BaseQuickAdapter<Friendinfo, BaseViewHolder> {

    private Context context;

    public NearPeopleAdapter(List<Friendinfo> friendList, Context context) {
        super(R.layout.add_friend_adapter_layout, friendList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Friendinfo friend) {
        if(friend.getName().equals("")||friend.getName()==null){
            helper.setText(R.id.name,"此人很懒没有设置用户名");
        }else{
            helper.setText(R.id.name,friend.getName());
        }
        helper.setText(R.id.hello, friend.getUserId());
        TextView connection =helper.getView(R.id.connection);
        if(friend.getConnectionStatus()== ConnectionStatus.Connected){
            connection.setText("在线");
        }else{
            connection.setText("离线");
        }
        final Button add =helper.getView(R.id.add);
        if(friend.getState()==1){
            add.setText("已添加");
            add.setEnabled(false);
            add.setTextColor(Color.parseColor("#9f9f9f"));
            add.setBackgroundResource(R.drawable.shap_gray);
        }
        try {
            if(carrierInst.isFriend(friend.getUserId())){
                add.setText("已添加");
                add.setEnabled(false);
                add.setTextColor(Color.parseColor("#9f9f9f"));
                add.setBackgroundResource(R.drawable.shap_gray);
            }else{
                add.setText("添加");
            }
        } catch (ElastosException e) {
            e.printStackTrace();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    carrierInst.AcceptFriend(friend.getUserId());
                    add.setText("已添加");
                    add.setEnabled(false);
                    add.setTextColor(Color.parseColor("#9f9f9f"));
                    add.setBackgroundResource(R.drawable.shap_gray);
                    friend.setState(1);
                    notifyDataSetChanged();
                    Toast.makeText(context, "好友添加成功", Toast.LENGTH_SHORT).show();
                } catch (ElastosException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
