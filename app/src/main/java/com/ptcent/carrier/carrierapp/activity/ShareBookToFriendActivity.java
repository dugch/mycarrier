package com.ptcent.carrier.carrierapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerGoods;
import com.ptcent.carrier.carrierapp.model.Friendinfo;
import com.ptcent.carrier.carrierapp.model.Goods;
import com.ptcent.carrier.carrierapp.util.Common;
import com.ptcent.carrier.carrierapp.widget.ShareToFriendAdapter;

import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.FriendInfo;
import org.elastos.carrier.exceptions.ElastosException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;
import static com.ptcent.carrier.carrierapp.util.Common.UID;

/**
 *
 */
public class ShareBookToFriendActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.button_receipt)
    Button buttonReceipt;
    private ShareToFriendAdapter shareToFriendAdapter;
    private List<Friendinfo> friendinfoList = new ArrayList<>();
    private DatabaseManagerGoods databaseManagerGoods;
    private List<Goods> goodsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_book_to_friend);
        ButterKnife.bind(this);
        init();
        event();
    }

    private void init() {
        setBackBtn();
        setTitle("分享");
        databaseManagerGoods = new DatabaseManagerGoods(ShareBookToFriendActivity.this);
        if (carrierInst.isReady()) {
            try {
                List<FriendInfo> friendInfoList = carrierInst.getFriends();
                if (friendInfoList != null && friendInfoList.size() > 0) {
                    for (FriendInfo friendInfo : friendInfoList) {
                        Friendinfo friend = new Friendinfo();
                        if (!friendInfo.getUserId().equals(UID) && !friendInfo.getUserId().equals(Common.SERVERUSERID)) {
                            friend.setUserId(friendInfo.getUserId());
                            friend.setConnectionStatus(friendInfo.getConnectionStatus());
                            friend.setName(friendInfo.getName());
                            friendinfoList.add(friend);
                        }
                    }
                }
                shareToFriendAdapter = new ShareToFriendAdapter(ShareBookToFriendActivity.this, friendinfoList);
                listView.setAdapter(shareToFriendAdapter);
            } catch (ElastosException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "carrier  未准备好", Toast.LENGTH_SHORT).show();
        }
    }
  private void event(){
      buttonReceipt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(friendinfoList!=null&&friendinfoList.size()>0){
                  databaseManagerGoods =new DatabaseManagerGoods(ShareBookToFriendActivity.this);
                  goodsList =databaseManagerGoods.queryData();
                  if(goodsList==null){
                      Toast.makeText(ShareBookToFriendActivity.this, "未有书籍分享", Toast.LENGTH_SHORT).show();
                      return;
                  }
                  List<Goods> sharelist =new ArrayList<Goods>();
                  for (Goods goods : goodsList){
                      if(goods.getState()==0){
                          sharelist.add(goods);
                      }
                  }
                  GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
                  String sharestr = gsonBuilder.create().toJson(sharelist);
                  boolean click=false;
                  try {
                  for (int i = 0; i < friendinfoList.size(); i++) {
                      Friendinfo friendinfo =friendinfoList.get(i);
                      if(friendinfo.getState()==1){
                          String userid=friendinfo.getUserId();
                          click=true;
                          if(carrierInst.getFriend(userid).getConnectionStatus()== ConnectionStatus.Connected){
                               carrierInst.sendFriendMessage(userid,sharestr);
                          }else{
                              Toast.makeText(ShareBookToFriendActivity.this, userid+"未在线", Toast.LENGTH_SHORT).show();
                          }

                      }
                   }
                  } catch (ElastosException e) {
                      e.printStackTrace();

                  }
                  if(click==false){
                      Toast.makeText(ShareBookToFriendActivity.this, "未选择好友", Toast.LENGTH_SHORT).show();
                  }
              }else{
                  Toast.makeText(ShareBookToFriendActivity.this, "未有好友", Toast.LENGTH_SHORT).show();
              }

          }
      });
  }
}