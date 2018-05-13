package com.ptcent.carrier.carrierapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * <b>FileName:</b> DatabaseHelper.java<br>
 * <b>ClassName:</b> DatabaseHelper<br>
 * 
 * @Description 创建数据库
 * @author nk0000098
 * @date 2015年6月9日 下午1:50:48
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String DB_NAME = "ddz.db";
	public static final String TABLE_NAME_GOODS = "TABLE_NAME_GOODS";
	public static final String TABLE_NAME_PURCHASEDGOODS = "TABLE_NAME_PURCHASEDGOODS";
	public static final String TABLE_NAME_ADDFRIEND = "TABLE_NAME_ADDFRIEND";
	public static final String TABLE_NAME_CHART = "TABLE_NAME_CHART";


	private static final String SQL_CREATE_TABLE_GOODS = "CREATE TABLE "
			+ TABLE_NAME_GOODS
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, goodsname TEXT, count INTEGER, price INTEGER, userid TEXT,state INTEGER)";

	private static final String SQL_CREATE_TABLE_PURCHASEDGOODS = "CREATE TABLE "
			+ TABLE_NAME_PURCHASEDGOODS
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, goodsname TEXT, count INTEGER, price INTEGER, userid TEXT,state INTEGER)";

	private static final String SQL_CREATE_TABLE_ADDFRIEND = "CREATE TABLE "
			+ TABLE_NAME_ADDFRIEND
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT , hello TEXT , name TEXT ,state INTEGER)";

	private static final String SQL_CREATE_TABLE_CHART = "CREATE TABLE "
			+ TABLE_NAME_CHART
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT ,type INTEGER , userid TEXT)";


	// 三个不同参数的构造函数
	// 带全部参数的构造函数，此构造函数必不可少
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	// 创建数据库
	public void onCreate(SQLiteDatabase db) {
		// 执行创建数据库操作
		db.execSQL(SQL_CREATE_TABLE_GOODS);
		db.execSQL(SQL_CREATE_TABLE_PURCHASEDGOODS);
		db.execSQL(SQL_CREATE_TABLE_ADDFRIEND);
		db.execSQL(SQL_CREATE_TABLE_CHART);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(DB_NAME, "update a Database, old version: " + oldVersion
				+ ", new version: " + newVersion);
	}
}