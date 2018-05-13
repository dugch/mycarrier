package com.ptcent.carrier.carrierapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerPurchasedGoods;
import com.ptcent.carrier.carrierapp.model.Goods;
import com.ptcent.carrier.carrierapp.widget.MyGoodsAdapter;

import java.util.ArrayList;
import java.util.List;


public class PurchaseHistoryActivity extends BaseActivity {

    private MyGoodsAdapter goodsAdapter;
    private List<Goods> goodsList =new ArrayList<>();
    private RecyclerView recyView;
    private DatabaseManagerPurchasedGoods databaseManagerPurchasedGoods ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        init();
    }

    private void init() {
        setTitle("购买记录");
        setBackBtn();
        databaseManagerPurchasedGoods  =new DatabaseManagerPurchasedGoods(PurchaseHistoryActivity.this);
        recyView = (RecyclerView) findViewById(R.id.recyView);
//        String goods = UserInfoPref.readPurchasedGoods(PurchaseHistoryActivity.this);
//        Type type2 = new TypeToken<List<Goods>>() {
//        }.getType();
        goodsList = databaseManagerPurchasedGoods.queryData();
        recyView.setLayoutManager(new LinearLayoutManager(PurchaseHistoryActivity.this));
        goodsAdapter = new MyGoodsAdapter(goodsList, PurchaseHistoryActivity.this,2);
        recyView.setAdapter(goodsAdapter);
    }
}
