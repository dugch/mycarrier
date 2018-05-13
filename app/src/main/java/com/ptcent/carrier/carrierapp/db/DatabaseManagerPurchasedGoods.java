package com.ptcent.carrier.carrierapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.ptcent.carrier.carrierapp.model.Goods;

import java.util.ArrayList;
import java.util.List;


public class DatabaseManagerPurchasedGoods {
	private DatabaseHelper dbHelper = null;
	private Context context = null;

	public DatabaseManagerPurchasedGoods(Context context) {
		super();
		this.dbHelper = new DatabaseHelper(context);
		this.context = context;
	}

	public long insertData(Goods goods) {
		long row = 0;
		// 创建存放数据的ContentValues对象
		ContentValues values = new ContentValues();
		// 像ContentValues中存放数据
		values.put("userid", goods.getUserid());
		values.put("goodsname", goods.getGoodsname());
		values.put("count",goods.getCount());
		values.put("price",goods.getPrice());
		values.put("state",goods.getState());
		synchronized (dbHelper) {
			SQLiteDatabase db = null;
			try {
				db = dbHelper.getWritableDatabase();
			} catch (SQLiteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (db != null) {
				try {
					db.beginTransaction();
					// 数据库执行插入命令
					row = db.insert(DatabaseHelper.TABLE_NAME_PURCHASEDGOODS,
							null, values);
					db.setTransactionSuccessful();
				} catch (SQLiteException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (db != null) {
						db.endTransaction();
						db.close();
					}
				}
			}
		}

		return row;
	}

	public List<Goods> queryData() {
		synchronized (dbHelper) {
			List<Goods> list = null;
			SQLiteDatabase db = null;
			Cursor c = null;
			try {
				db = dbHelper.getReadableDatabase();
			} catch (SQLiteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (db != null) {
				try {
					// 创建游标对象
					String sql = "select  *  from "
							+ DatabaseHelper.TABLE_NAME_PURCHASEDGOODS;

					db.beginTransaction();
					c = db.rawQuery(sql, null);
					list = new ArrayList<Goods>();

					while (c != null && c.moveToNext()) {
						Goods goods = new Goods();
						String userid =c.getString(c.getColumnIndex("userid"));
						String goodsname = c.getString(c.getColumnIndex("goodsname"));
						int count = c.getInt(c.getColumnIndex("count"));
						int price = c.getInt(c.getColumnIndex("price"));
						int state = c.getInt(c.getColumnIndex("state"));
						int id = c.getInt(c.getColumnIndex("id"));
						goods.setUserid(userid);
						goods.setCount(count);
						goods.setGoodsname(goodsname);
						goods.setState(state);
						goods.setPrice(price);
						goods.setId(id);
						list.add(goods);
					}
				} catch (SQLiteException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (c != null) {
						c.close();
					}
					if (db != null) {
						db.endTransaction();
						db.close();
					}
				}
			}
			return list;
		}
	}

}
