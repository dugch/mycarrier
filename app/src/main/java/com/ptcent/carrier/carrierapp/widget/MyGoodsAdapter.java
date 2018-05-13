package com.ptcent.carrier.carrierapp.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.model.Goods;

import java.util.List;

/**
 * Created by dugc on 2017/9/13.
 */

public class MyGoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

     private Context context;
     private int type;
    public MyGoodsAdapter(List<Goods> goodsList, Context context,int type){
        super( R.layout.mygoods_layout, goodsList);
        this.context=context;
        this.type =type;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Goods item) {
        helper.setText(R.id.goods_name,"书籍名称："+item.getGoodsname())
                .setText(R.id.goods_count,"数量： "+item.getCount())
                .setText(R.id.goods_price,"  "+item.getPrice()+ "  ELA");
        TextView state =helper.getView(R.id.state);

        if(item.getState()==1&&type==1){
            state.setVisibility(View.VISIBLE);
        }else{
            state.setVisibility(View.GONE);
        }

    }

}
