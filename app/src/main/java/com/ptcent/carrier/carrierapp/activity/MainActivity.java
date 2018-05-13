package com.ptcent.carrier.carrierapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerAddFriends;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerChat;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerGoods;
import com.ptcent.carrier.carrierapp.model.ChatModel;
import com.ptcent.carrier.carrierapp.model.CheckPermission;
import com.ptcent.carrier.carrierapp.model.Friend;
import com.ptcent.carrier.carrierapp.model.Friendinfo;
import com.ptcent.carrier.carrierapp.model.Goods;
import com.ptcent.carrier.carrierapp.model.MessageEvent;
import com.ptcent.carrier.carrierapp.util.Common;
import com.ptcent.carrier.carrierapp.util.GlobalVarManager;
import com.ptcent.carrier.carrierapp.util.ProgressLoading;
import com.ptcent.carrier.carrierapp.util.Synchronizer;
import com.ptcent.carrier.carrierapp.util.TestOptions;
import com.ptcent.carrier.carrierapp.util.UserInfoPref;
import com.ptcent.carrier.carrierapp.widget.MyViewPager;

import org.elastos.carrier.AbstractCarrierHandler;
import org.elastos.carrier.Carrier;
import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.Log;
import org.elastos.carrier.UserInfo;
import org.elastos.carrier.exceptions.ElastosException;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ptcent.carrier.carrierapp.util.Common.UID;


/**
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @Bind(R.id.mian_viewpager)
    MyViewPager myViewPager;
    @Bind(R.id.index_tv)
    TextView indexTv;
    @Bind(R.id.index_img)
    ImageView indexImg;
    @Bind(R.id.index_re)
    RelativeLayout index_re;


    @Bind(R.id.shop_tv)
    TextView shopTv;
    @Bind(R.id.shop_img)
    ImageView shopImg;
    @Bind(R.id.shop_re)
    RelativeLayout shopRe;

    @Bind(R.id.me_tv)
    TextView meTv;
    @Bind(R.id.me_img)
    ImageView meImg;
    @Bind(R.id.me_re)
    RelativeLayout meRe;

    private IndexFragmentActivity indexFrag;
    private ShopFragmentActivity shopFragmentActivity;
    private UserFragmentActivity userFrag;
    private ArrayList<Fragment> alFragments;
    private MyFragmentAdapter myFragmentAdapter;
    private int index;
    public static MainActivity mainActivity;
    private long lastBackKeyTappedTime = 0;
    public  TestOptions options;
    public  static Carrier carrierInst;
    private DatabaseManagerGoods databaseManagerGoods;
    private DatabaseManagerAddFriends databaseManagerAddFriends;
    private DatabaseManagerChat databaseManagerChat;
    private List<FriendInfo> friendInfoList = new ArrayList<>();
    private GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
    private List<Goods> quList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initEvent();
    }
    private void init() {
        setTitle("书店");
        setRightImage(R.drawable.message);
        upDateView(0);
        myViewPager.setOnPageChangeListener(this);
        databaseManagerGoods =new DatabaseManagerGoods(MainActivity.this);
        databaseManagerAddFriends = new DatabaseManagerAddFriends(MainActivity.this);
        databaseManagerChat = new DatabaseManagerChat(MainActivity.this);
        loading = ProgressLoading.createLoadingDialog(MainActivity.this);
        if(loading!=null&& !isFinishing()){
            loading.show();
        }
        checkPermission();
        new Thread(new Runnable() {
            @Override
            public void run() {
                options = new TestOptions(getAppPath());
                TestHandler handler = new TestHandler();
                try {
                    carrierInst = Carrier.getInstance(options, handler);
                    carrierInst.start(10000);
                    handler.synch.await();
                    if(!Common.SERVERADDRESS.equals(carrierInst.getAddress())){
                        if(!carrierInst.isFriend(Common.SERVERUSERID)){
                            carrierInst.addFriend(Common.SERVERADDRESS,"admin");
                        }
                    }
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shopFragmentActivity.init();
                            if(loading!=null&&loading.isShowing()){
                                loading.dismiss();
                            }
                        }
                    });

                } catch (ElastosException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        quList =databaseManagerGoods.queryData();
    }

    private void initEvent() {
        alFragments=new ArrayList<Fragment>();
        indexFrag =new IndexFragmentActivity();
        shopFragmentActivity = new ShopFragmentActivity();
        userFrag = new UserFragmentActivity();

        alFragments.add(indexFrag);
        alFragments.add(shopFragmentActivity);
        alFragments.add(userFrag);

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(),
                alFragments);
        myViewPager.setAdapter(myFragmentAdapter);
        myViewPager.setSwipeEnabled(false);


    }
    class TestHandler extends AbstractCarrierHandler {
        private Synchronizer synch = new Synchronizer();

        public void onReady(Carrier carrier) {
            synch.wakeup();
        }

        @Override
        public void onFriendRequest(Carrier carrier, final String userId, UserInfo info, String hello) {
            try {
                if (hello.equals("admin")) {
                    carrierInst.AcceptFriend(userId);
                }else{
                    Friend friend = new Friend();
                    friend.setUserid(userId);
                    friend.setHello(hello);
                    friend.setName(info.getName());
                    databaseManagerAddFriends.insertData(friend);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFriendConnection(Carrier carrier, final String friendId, ConnectionStatus status) {
            if (status == ConnectionStatus.Connected) {
                sendserverMessage(friendId);
                sendgoodsInfo(friendId);
                initFriendsList();
                synch.wakeup();
            }
            super.onFriendConnection(carrier, friendId, status);
        }

        @Override
        public void onFriendMessage(Carrier carrier, final String from, final String message) {
            super.onFriendMessage(carrier, from, message);
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        if (message != null) {
                            if(message.contains("connectionStatus")){
                                UserInfoPref.writeNearpople(MainActivity.this,message);
                                Toast.makeText(MainActivity.this,"已接收到附近人的信息", Toast.LENGTH_SHORT).show();
                            }else if(message.contains("goodsname")){
                                indexFrag.init(message);
                            }else if(message.contains("*&*")){
                                String Msg =message.replace("*&*","");
                                MessageEvent messageEvent=new MessageEvent();
                                messageEvent.setMessage(Msg);
                                EventBus.getDefault().post(messageEvent);
                                ChatModel chatModel = new ChatModel();
                                chatModel.setUserid(from);
                                chatModel.setType(2);
                                chatModel.setContent(Msg);
                                databaseManagerChat.insertData(chatModel);
                            }else {
                                updateGoodsData(message);
                            }
                        }
                }
            });
        }
    }

    /**
     * server 分享好友
     * @param friendId
     */
    private void sendserverMessage(String friendId){
        try {
            String userId =carrierInst.getUserId();
            if(userId.equals(Common.SERVERUSERID)){
                friendInfoList = carrierInst.getFriends();
                List<Friendinfo> friendinfos =new ArrayList<>();
                if(friendInfoList!=null&&friendInfoList.size()>0){
                    for(FriendInfo friend: friendInfoList){
                        if(!friend.getUserId().equals(UID)&&!friend.getUserId().equals(friendId)){
                            Friendinfo friendinfo =new Friendinfo();
                            friendinfo.setName(friend.getName());
                            friendinfo.setUserId(friend.getUserId());
                            friendinfo.setConnectionStatus(friend.getConnectionStatus());
                            friendinfos.add(friendinfo);
                        }
                    }
                    if(friendinfos.size()>0){
                        String goodstr = gsonBuilder.create().toJson(friendinfos);
                        carrierInst.sendFriendMessage(friendId,goodstr);
                    }
                }
            }
        } catch (ElastosException e) {
            e.printStackTrace();
            Log.e("printStackTrace",""+e.getErrorCode());
        }
    }

    /**
     * 分享商品
     * @param friendId
     */
    private void sendgoodsInfo(String friendId){
        if(quList!=null&&quList.size()>0){
            List<Goods> sharelist =new ArrayList<Goods>();
            for (Goods goods : quList){
                if(goods.getState()==0){
                    sharelist.add(goods);
                }
            }
            try {
                if(sharelist!=null&&sharelist.size()>0){
                    String goodstr = gsonBuilder.create().toJson(sharelist);
                    carrierInst.sendFriendMessage(friendId,goodstr);
                }
            } catch (ElastosException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新商品状态 资产
     * @param message
     */
    private void updateGoodsData(String message){
        try {
            int id =Integer.parseInt(message);
            List<Goods> goodsList =databaseManagerGoods.queryData();
            if(goodsList==null) return;
            int price =0;
            for(Goods good :goodsList){
                if(good.getId()==id){
                    good.setState(1);
                    price = good.getPrice();
                    databaseManagerGoods.updateData(good);
                    break;
                }
            }
            int totle =UserInfoPref.readTotle(MainActivity.this);
            UserInfoPref.writeTotle(MainActivity.this,totle+price);
            userFrag.init();
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void initFriendsList(){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                shopFragmentActivity.init();
            }
        });
    }


    @OnClick({R.id.index_re, R.id.shop_re,R.id.me_re})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_re:
                setTitle("书店");
                index = 0;
                break;
            case R.id.shop_re:
                setTitle("微聊");
                index = 1;
                break;
            case R.id.me_re:
                setTitle("我的");
                index = 2;
                break;
        }
        upDateView(index);
        myViewPager.setCurrentItem(index, false);
    }
    private void upDateView(int index){
        indexImg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.home_normal));
        shopImg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.message_nomal));
        meImg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.me_normal));


        indexTv.setTextColor(Color.parseColor("#7F7F7F"));
        shopTv.setTextColor(Color.parseColor("#7F7F7F"));
        meTv.setTextColor(Color.parseColor("#7F7F7F"));

        switch (index) {
            case 0:
                indexImg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.home_light));
                indexTv.setTextColor(this.getResources().getColor(R.color.theme_color));
                break;
            case 1:
                shopImg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.message_light));
                shopTv.setTextColor(this.getResources().getColor(R.color.theme_color));
                break;
            case 2:
                meImg.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.me_light));
                meTv.setTextColor(this.getResources().getColor(R.color.theme_color));
                break;
            default:
                break;
        }
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        upDateView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyFragmentAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;

        public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (myViewPager.getCurrentItem() != 0) {
                upDateView(0);
                myViewPager.setCurrentItem(0);
            } else if (myViewPager.getCurrentItem() == 0) {
                if ((System.currentTimeMillis() - lastBackKeyTappedTime) > 2000) {
                    Toast.makeText(this,"再按一次退出登录",Toast.LENGTH_SHORT).show();
                    lastBackKeyTappedTime = System.currentTimeMillis();
                } else {
                    this.finish();
                    UserInfoPref.clear(MainActivity.this);
                    if(carrierInst!=null){
                        carrierInst.kill();
                        carrierInst=null;
                    }

                }
            }
        }
        return true;
    }

    private String getAppPath() {
        return MainActivity.this.getFilesDir().getAbsolutePath() + "-ptcent";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loading.dismiss();
        UserInfoPref.clear(MainActivity.this);
    }

    private void checkPermission(){
        CheckPermission checkPermission = GlobalVarManager.readCheckPermission(MainActivity.this);
        if (!checkPermission.isPermissionWriteExternalStorageAllowed()) {
            Toast.makeText(MainActivity.this, "请在应用管理器中打开存储权限", Toast.LENGTH_LONG).show();
            if(loading!=null&&loading.isShowing()){
                loading.dismiss();
            }
            return;
        }
    }
}
