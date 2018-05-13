package com.ptcent.carrier.carrierapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.ptcent.carrier.carrierapp.model.Friend;

import java.util.ArrayList;
import java.util.List;


public class DatabaseManagerAddFriends {
	private DatabaseHelper dbHelper = null;
	private Context context = null;

	public DatabaseManagerAddFriends(Context context) {
		super();
		this.dbHelper = new DatabaseHelper(context);
		this.context = context;
	}

	public long insertData(Friend friend) {
		long row = 0;
		// 创建存放数据的ContentValues对象
		ContentValues values = new ContentValues();
		// 像ContentValues中存放数据
		values.put("userid", friend.getUserid());
		values.put("hello", friend.getHello());
		values.put("name", friend.getName());
		values.put("state",0);
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
					row = db.insert(DatabaseHelper.TABLE_NAME_ADDFRIEND,
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

	public List<Friend> queryData() {
		synchronized (dbHelper) {
			List<Friend> list = null;
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
							+ DatabaseHelper.TABLE_NAME_ADDFRIEND;

					db.beginTransaction();
					c = db.rawQuery(sql, null);
					list = new ArrayList<Friend>();

					while (c != null && c.moveToNext()) {
						Friend friend = new Friend();
						String userid =c.getString(c.getColumnIndex("userid"));
						String hello = c.getString(c.getColumnIndex("hello"));
						String name = c.getString(c.getColumnIndex("name"));
						int state = c.getInt(c.getColumnIndex("state"));
						int id = c.getInt(c.getColumnIndex("id"));
						friend.setUserid(userid);
						friend.setHello(hello);
						friend.setId(id);
						friend.setName(name);
						friend.setState(state);
						list.add(friend);
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
	public void updateData(int id){
		ContentValues values = new ContentValues();
		values.put("state",1);

		String whereClause = "id=?";//修改条件
		String[] whereArgs = {String.valueOf(id)};//修改条件的参数
		synchronized(dbHelper) {
			SQLiteDatabase db = null;
			try {
				db = dbHelper.getWritableDatabase();
			} catch(SQLiteException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}

			if (db != null) {
				try {
					db.beginTransaction();
					// 数据库执行插入命令
					db.update(DatabaseHelper.TABLE_NAME_ADDFRIEND, values, whereClause,whereArgs);
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
	}
}

