package com.ptcent.carrier.carrierapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.ptcent.carrier.carrierapp.model.ChatModel;

import java.util.ArrayList;


public class DatabaseManagerChat {
	private DatabaseHelper dbHelper = null;
	private Context context = null;

	public DatabaseManagerChat(Context context) {
		super();
		this.dbHelper = new DatabaseHelper(context);
		this.context = context;
	}

	public long insertData(ChatModel chatModel) {
		long row = 0;
		// 创建存放数据的ContentValues对象
		ContentValues values = new ContentValues();
		// 像ContentValues中存放数据
		values.put("content", chatModel.getContent());
		values.put("type", chatModel.getType());
		values.put("userid", chatModel.getUserid());
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
					row = db.insert(DatabaseHelper.TABLE_NAME_CHART,
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

	public ArrayList<ChatModel> queryData() {
		synchronized (dbHelper) {
			ArrayList<ChatModel> list = null;
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
							+ DatabaseHelper.TABLE_NAME_CHART;

					db.beginTransaction();
					c = db.rawQuery(sql, null);
					list = new ArrayList<ChatModel>();

					while (c != null && c.moveToNext()) {
						ChatModel chatModel = new ChatModel();
						String content =c.getString(c.getColumnIndex("content"));
						int type = c.getInt(c.getColumnIndex("type"));
						String userid = c.getString(c.getColumnIndex("userid"));
						int id = c.getInt(c.getColumnIndex("id"));
						chatModel.setContent(content);
						chatModel.setId(id);
						chatModel.setType(type);
						chatModel.setUserid(userid);
						list.add(chatModel);
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

