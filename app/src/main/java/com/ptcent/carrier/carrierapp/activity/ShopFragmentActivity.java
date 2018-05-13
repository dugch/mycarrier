package com.ptcent.carrier.carrierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.util.Common;
import com.ptcent.carrier.carrierapp.widget.MyFriendsAdapter;

import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.exceptions.ElastosException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;
import static com.ptcent.carrier.carrierapp.util.Common.UID;

/**
 * @author by dugc
 * @date 2018/4/28   15:17
 */

public class ShopFragmentActivity extends Fragment {
    @Bind(R.id.recyView)
    RecyclerView recyView;
    private View view;
    private List<FriendInfo> friendInfoList = new ArrayList<>();
    private MyFriendsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.shop_layout, container, false);
        }
        return view;
    }

    public void init() {
        if(carrierInst.isReady()){
            try {
                friendInfoList =carrierInst.getFriends();
                if(friendInfoList!=null){
                Iterator<FriendInfo> it = friendInfoList.iterator();
                while(it.hasNext()){
                    String userid =it.next().getUserId();
                    Log.e("userid",""+userid);
                    if(userid.equals(UID)||userid.equals(Common.SERVERUSERID)){
                        it.remove();
                    }
                }
                }
                recyView = (RecyclerView) view .findViewById(R.id.recyView);
                recyView.setLayoutManager(new LinearLayoutManager(ShopFragmentActivity.this.getActivity()));
                adapter = new MyFriendsAdapter(friendInfoList, ShopFragmentActivity.this.getActivity());
                recyView.setAdapter(adapter);


                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        FriendInfo friendInfo =friendInfoList.get(position);
                        if(friendInfo!=null){
                            Intent intent = new Intent(ShopFragmentActivity.this.getActivity(),ChatActivity.class);
                            intent.putExtra("userid", friendInfo.getUserId());
                            intent.putExtra("name", friendInfo.getName());
                            startActivity(intent);
                        }
                    }
                });
            } catch (ElastosException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
