package com.ptcent.carrier.carrierapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.db.DatabaseManagerGoods;
import com.ptcent.carrier.carrierapp.model.Goods;

import org.elastos.carrier.exceptions.ElastosException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.ptcent.carrier.carrierapp.activity.MainActivity.carrierInst;

public class AddGoodsActivity extends BaseActivity {


    @Bind(R.id.goods_name)
    EditText goodsName;
    @Bind(R.id.goods_count)
    TextView goodsCount;
    @Bind(R.id.goods_price)
    TextView goodsPrice;
    private String userid;
    private List<Goods> goodsList;
    private int maxid;
    private DatabaseManagerGoods databaseManagerGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        setBackBtn();
        setTitle("新增书籍");
        setRightText("保存");
        try {
              userid = carrierInst.getUserId();
          } catch (ElastosException e) {
            e.printStackTrace();
          }
        databaseManagerGoods =new DatabaseManagerGoods(AddGoodsActivity.this);
        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String goodsname  =   goodsName.getText().toString().trim();
                 String goodsprice =   goodsPrice.getText().toString().trim();
                 if(goodsname.equals("")){
                     Toast.makeText(AddGoodsActivity.this, "请输入书籍名称", Toast.LENGTH_SHORT).show();
                     return;
                 }
                if(goodsprice.equals("")){
                    Toast.makeText(AddGoodsActivity.this, "请输入书籍价格", Toast.LENGTH_SHORT).show();
                    return;
                }
                 Goods goods =new Goods();
                 goods.setPrice(Integer.parseInt(goodsprice));
                 goods.setCount(1);
                 goods.setGoodsname(goodsname);
                 goods.setUserid(userid);
                 databaseManagerGoods.insertData(goods);
                 Toast.makeText(AddGoodsActivity.this, "书籍添加成功", Toast.LENGTH_SHORT).show();
                 finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
