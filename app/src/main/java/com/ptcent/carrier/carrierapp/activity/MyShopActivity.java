package com.ptcent.carrier.carrierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerGoods;
import com.ptcent.carrier.carrierapp.model.Goods;
import com.ptcent.carrier.carrierapp.util.ProgressLoading;
import com.ptcent.carrier.carrierapp.widget.MyGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyShopActivity extends BaseActivity {

    private List<Goods> goodsList = new ArrayList<>();
    private MyGoodsAdapter goodsAdapter;
    @Bind(R.id.recyView)
    RecyclerView recyView;
    @Bind(R.id.share)
    Button share;
    private DatabaseManagerGoods databaseManagerGoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseManagerGoods =new DatabaseManagerGoods(MyShopActivity.this);
        goodsList =databaseManagerGoods.queryData();
        recyView.setLayoutManager(new LinearLayoutManager(MyShopActivity.this));
        goodsAdapter = new MyGoodsAdapter(goodsList, MyShopActivity.this,1);
        recyView.setAdapter(goodsAdapter);
    }

    private void init(){
        setBackBtn();
        setTitle("我的书店");
        loading = ProgressLoading.createLoadingDialog(MyShopActivity.this);
        setRightImage(R.drawable.add_white);
        right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MyShopActivity.this,AddGoodsActivity.class);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodsList==null||goodsList.size()==0){
                    Toast.makeText(MyShopActivity.this, "未有书籍分享", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MyShopActivity.this,ShareBookToFriendActivity.class);
                startActivity(intent);


//                if(loading!=null&& !isFinishing()){
//                    loading.show();
//                }
//                try {
//                    if(carrierInst.isReady()){
//                        databaseManagerGoods =new DatabaseManagerGoods(MyShopActivity.this);
//                        goodsList =databaseManagerGoods.queryData();
//
//                        if(goodsList==null){
//                            Toast.makeText(MyShopActivity.this, "未有书籍分享", Toast.LENGTH_SHORT).show();
//                            dismiss();
//                            return;
//                        }
//                        List<Goods> sharelist =new ArrayList<Goods>();
//                        for (Goods goods : goodsList){
//                            if(goods.getState()==0){
//                                sharelist.add(goods);
//                            }
//                        }
//                        if(sharelist==null||sharelist.size()==0){
//                            Toast.makeText(MyShopActivity.this, "未有书籍分享", Toast.LENGTH_SHORT).show();
//                            dismiss();
//                            return;
//                        }
//
//                        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
//                        String sharestr = gsonBuilder.create().toJson(sharelist);
//                        List<FriendInfo> friendInfoList =carrierInst.getFriends();
//
//                        if(friendInfoList!=null&&friendInfoList.size()>0){
//                            for (int i = 0; i < friendInfoList.size(); i++) {
//                                FriendInfo friendInfo =friendInfoList.get(i);
//                                if(friendInfo.getConnectionStatus()== ConnectionStatus.Connected){
//                                    carrierInst.sendFriendMessage(friendInfo.getUserId(),sharestr);
//                                    Toast.makeText(MyShopActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            dismiss();
//                        }else{
//                            Toast.makeText(MyShopActivity.this, "未有好友", Toast.LENGTH_SHORT).show();
//                            dismiss();
//                        }
//                    }else {
//                        Toast.makeText(MyShopActivity.this, "carrier 未准备好", Toast.LENGTH_SHORT).show();
//                        dismiss();
//                    }
//                } catch (ElastosException e) {
//                    e.printStackTrace();
//                    if(e.getErrorCode()==-2046296061){
//                        Toast.makeText(MyShopActivity.this, "好友未在线", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });
    }
    private  void dismiss(){
        if(loading!=null){
            loading.dismiss();
        }

    }
}
