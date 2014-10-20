package com.example.colorfulweather.controller.db;

import java.io.File;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "weather_city.db";
	private static final int VERSION = 1;
	private static String database_path = "";

	public DBHelper(Context context) {
		super(new DatabaseContext(context), getDatabasePath(), null, VERSION);
		// TODO Auto-generated constructor stub
	}

	// 创建三个表
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql_1 = "create table first_level(code char(5) primary key,value varchar(40) not null)";
		String sql_2 = "create table second_level(code char(7) primary key,value varchar(40) not null)";
		String sql_3 = "create table third_level(code char(9) primary key,value varchar(40) not null)";
		try {
			db.execSQL(sql_1);
			db.execSQL(sql_2);
			db.execSQL(sql_3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	// 获取数据库路径
	public static String getDatabasePath() {
		return database_path ;
	}

	// 设置数据库路径
	public static void setDatabasePath(String path) {
		if (path != null && path.length() > 0) {
			database_path = path;
		}
	}

	// 关闭数据库
	public void closeDatabase(SQLiteDatabase db) {
		if (db != null && db.isOpen() == true) {
			db.close();
		}
	}

	// 关闭游标
	public static void closeCursor(Cursor c) {
		if (c != null && c.isClosed() == false) {
			c.close();
		}
	}

	// 检查数据库是否存在
	public static boolean checkDatabase() {
		if (database_path == null || database_path.length() <= 0) {
			return false;
		} else {
			File f = new File(database_path);
			return f.exists();
		}
	}

}

class DatabaseContext extends ContextWrapper {
	public DatabaseContext(Context base) {
		super(base);
	}

	@Override
	public File getDatabasePath(String name) {
		File file = new File(name);
		return file;
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode,
			SQLiteDatabase.CursorFactory factory) {
		SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(
				getDatabasePath(name), null);
		return result;
	}
}
