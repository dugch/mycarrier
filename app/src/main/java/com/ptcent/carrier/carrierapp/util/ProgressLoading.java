package com.ptcent.carrier.carrierapp.util;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptcent.carrier.carrierapp.R;


/**
 * <b>FileName:</b> ProgressLoading.java<br>
 * <b>ClassName:</b> ProgressLoading<br>
 * @Description  TODO
 * loading图标
 * @author       hero	
 * @date         2017年2月23日 下午2:11:11
 */ 
public class ProgressLoading {

	public static Dialog createLoadingDialog(Context context) {
		if (null == context) {
			context = CarrierApplication.getContext();
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.spaceshipImage);
		spaceshipImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.loading));
		AnimationDrawable animationDrawable= (AnimationDrawable) spaceshipImage.getDrawable();
		animationDrawable.start();
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;
	}

	public static Dialog createLoadingdec(Context context,String dec) {
		if (null == context) {
			context = CarrierApplication.getContext();
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.spaceshipImage);
		TextView loading_txt= (TextView) v.findViewById(R.id.loading_txt);
		loading_txt.setText(dec);
		loading_txt.setVisibility(View.VISIBLE);
		spaceshipImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.loading));
		AnimationDrawable animationDrawable= (AnimationDrawable) spaceshipImage.getDrawable();
		animationDrawable.start();
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;
	}

}
