package com.ptcent.carrier.carrierapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerPurchasedGoods;
import com.ptcent.carrier.carrierapp.model.Goods;
import com.ptcent.carrier.carrierapp.widget.GoodsAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author by dugc
 * @describe 主页
 * @date 2018/4/28   15:17
 */

public class IndexFragmentActivity extends Fragment {
    RecyclerView recyView;
    private View view;
    private List<Goods> goodsAllList = new ArrayList<Goods>();
    private GoodsAdapter goodsAdapter;
    private Gson gson = new GsonBuilder().create();
    private DatabaseManagerPurchasedGoods databaseManagerPurchasedGoods;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.index_layout, container, false);
        }
        return view;
    }

    public void init(String message) {
        databaseManagerPurchasedGoods =new DatabaseManagerPurchasedGoods(IndexFragmentActivity.this.getActivity());
        String userid="";
        Type type2 = new TypeToken<List<Goods>>() {
        }.getType();
        List<Goods> goodsList = gson.fromJson(message.trim(), type2);
        if(goodsList!=null&&goodsList.size()>0){
            userid = goodsList.get(0).getUserid();
        }
        if(goodsAllList!=null&&goodsAllList.size()>0){
            Iterator<Goods> it = goodsAllList.iterator();
            while(it.hasNext()){
                if(userid.equals(it.next().getUserid())){
                    it.remove();
                }
            }
        }
        if(!userid.equals("")){
            goodsAllList.addAll(goodsList);
        }
        if(view!=null){
            recyView= (RecyclerView) view.findViewById(R.id.recyView);
            if(goodsList!=null&&goodsList.size()>0){
                recyView.setLayoutManager(new LinearLayoutManager(IndexFragmentActivity.this.getActivity()));
                goodsAdapter = new GoodsAdapter(goodsAllList, IndexFragmentActivity.this.getActivity(),databaseManagerPurchasedGoods);
                recyView.setAdapter(goodsAdapter);
            }
            Toast.makeText(IndexFragmentActivity.this.getActivity(), "同步成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(IndexFragmentActivity.this.getActivity(), "窗口已销毁", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
