package com.ptcent.carrier.carrierapp.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerPurchasedGoods;
import com.ptcent.carrier.carrierapp.model.Goods;
import com.ptcent.carrier.carrierapp.util.UserInfoPref;

import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.exceptions.ElastosException;

import java.util.List;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

/**
 * Created by dugc on 2017/9/13.
 */

public class GoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    private Context context;
    private DatabaseManagerPurchasedGoods databaseManagerPurchasedGoods;
    private Intent intent;

    public GoodsAdapter( List<Goods> goodsList, Context context ,DatabaseManagerPurchasedGoods databaseManagerPurchasedGoods){
        super( R.layout.goods_layout, goodsList);
        this.context=context;
        this.databaseManagerPurchasedGoods =databaseManagerPurchasedGoods;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Goods item) {
        helper.setText(R.id.goods_name,"书籍名称："+item.getGoodsname())
                .setText(R.id.goods_count,"数量："+item.getCount())
                .setText(R.id.goods_price,"  "+item.getPrice()+"  ELA");

        Button buy =helper.getView(R.id.buy);
        if(item.getState()==1){
            buy.setText("已购买");
            buy.setEnabled(false);
            buy.setTextColor(Color.parseColor("#9f9f9f"));
            buy.setBackgroundResource(R.drawable.shap_gray);
        }
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totle = UserInfoPref.readTotle(context);
                int price =item.getPrice();
                Log.e("@@@",""+totle+"&&"+price);
                if(totle>price||totle==price){
                    try {
                        if(carrierInst.getFriend(item.getUserid()).getConnectionStatus()== ConnectionStatus.Connected){
                            item.setState(1);
                            databaseManagerPurchasedGoods.insertData(item);
                            carrierInst.sendFriendMessage(item.getUserid(),""+item.getId());
                            UserInfoPref.writeTotle(context,totle-price);

                            intent = new Intent("UserFragmentActivity");
                            intent.putExtra("code", 1);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, "该好友不在线", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ElastosException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(context, "余额不足", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
