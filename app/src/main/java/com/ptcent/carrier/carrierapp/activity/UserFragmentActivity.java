package com.ptcent.carrier.carrierapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.util.UserInfoPref;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author by dugc
 * @package com.chinatelling.psisystem.activity_salePerson
 * @describe 我的
 * @date 2017/11/16    15:17
 */

public class UserFragmentActivity extends Fragment {
    @Bind(R.id.use_info)
    RelativeLayout useInfo;
    @Bind(R.id.addfriend)
    RelativeLayout addfriend;
    @Bind(R.id.myfriend)
    RelativeLayout myfriend;
    TextView userAsset;

    @Bind(R.id.QRcode_re)
    RelativeLayout QRcode;
    @Bind(R.id.nearpeople_re)
    RelativeLayout nearpeopleRe;


    private View view;
    private Intent intent;
    public static final String LOCAL_BROADCAST_FILTER = "UserFragmentActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.user_fragment_layout, container, false);
            ButterKnife.bind(this, view);
            init();
            registerLocalBroadcast();
        }
        return view;
    }

    public void init() {
        userAsset = (TextView) view.findViewById(R.id.user_asset);
        int totle = UserInfoPref.readTotle(UserFragmentActivity.this.getActivity());
        if (totle == -1) {
            UserInfoPref.writeTotle(UserFragmentActivity.this.getActivity(), 100);
            totle = 100;
        }
        userAsset.setText("资产：" + totle + "  ELA");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.use_info, R.id.addfriend, R.id.myfriend, R.id.purchase_history, R.id.QRcode_re,R.id.nearpeople_re})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use_info:
                intent = new Intent(UserFragmentActivity.this.getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.addfriend:
                intent = new Intent(UserFragmentActivity.this.getActivity(), MyFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.myfriend:
                intent = new Intent(UserFragmentActivity.this.getActivity(), MyShopActivity.class);
                startActivity(intent);
                break;
            case R.id.purchase_history:
                intent = new Intent(UserFragmentActivity.this.getActivity(), PurchaseHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.QRcode_re:
                intent = new Intent(UserFragmentActivity.this.getActivity(), UserCardActivity.class);
                startActivity(intent);
                break;
            case R.id.nearpeople_re:
                intent = new Intent(UserFragmentActivity.this.getActivity(), NearPeopleActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void registerLocalBroadcast() {
        IntentFilter filter = new IntentFilter(LOCAL_BROADCAST_FILTER);
        LocalBroadcastManager.getInstance(UserFragmentActivity.this.getActivity()).registerReceiver(
                broadcastReceiver, filter);
    }

    private void unregisterLocalBroadcast() {
        LocalBroadcastManager.getInstance(UserFragmentActivity.this.getActivity()).unregisterReceiver(
                broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int code = intent.getIntExtra("code", 0);
            if (code == 1) {
                init();
            }

        }
    };

    @Override
    public void onDestroy() {
        this.unregisterLocalBroadcast();
        super.onDestroy();
    }

}
