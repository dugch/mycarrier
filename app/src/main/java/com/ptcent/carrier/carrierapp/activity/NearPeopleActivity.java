package com.ptcent.carrier.carrierapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.ptcent.carrier.carrierapp.R;
import com.ptcent.carrier.carrierapp.model.Friendinfo;
import com.ptcent.carrier.carrierapp.util.UserInfoPref;
import com.ptcent.carrier.carrierapp.widget.NearPeopleAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NearPeopleActivity extends BaseActivity {
    private static final String TAG = "NearPeopleActivity";
    private RecyclerView recyView;
    private List<Friendinfo> friendInfoList = new ArrayList<>();
    private NearPeopleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_people);
        init();
    }

    private void init() {
        setBackBtn();
        setTitle("附近的人");
        recyView = (RecyclerView) findViewById(R.id.recyView);
        String  nearpople = UserInfoPref.readNearpople(NearPeopleActivity.this).trim();
        Type type2 = new TypeToken<List<Friendinfo>>() {
        }.getType();
        friendInfoList = gson.fromJson(nearpople, type2);
        recyView.setLayoutManager(new LinearLayoutManager(NearPeopleActivity.this));
        adapter = new NearPeopleAdapter(friendInfoList, NearPeopleActivity.this);
        recyView.setAdapter(adapter);

    }

}
