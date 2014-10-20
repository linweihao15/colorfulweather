package com.example.colorfulweather.controller.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;


public class DBManager {

	private static final int MSG_ERROR = 0;
	private static final int MSG_FINISHED = 1;
	private DBHelper helper;
	private Context context;
	private SQLiteDatabase database;
	private String cityid = "";

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_ERROR:
				// Tools.toastShowMsgShort(context, msg.obj.toString());
				break;
			case MSG_FINISHED:
				break;
			}
		}
	};

	public DBManager(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		new Thread(new init()).start();
	}

	/**
	 * 关闭数据库
	 */
	public void closeDataBase() {
		if (database != null && database.isOpen() == true) {
			database.close();
		}
	}

	/**
	 * 初始化数据库
	 * 
	 * @author weihao
	 * 
	 */
	private class init implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String db_file_path = Environment.getExternalStorageDirectory()
						+ "/ColorfulWeather/" + DBHelper.DATABASE_NAME;
				File file = new File(db_file_path);
				if (!file.exists()) {
					file = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/ColorfulWeather");
					if (!file.exists()) {
						file.mkdirs();
					}
					AssetManager asset_manager = context.getAssets();
					try {
						InputStream input_stream = asset_manager
								.open(DBHelper.DATABASE_NAME);
						FileOutputStream output_stream = new FileOutputStream(
								db_file_path);
						byte[] data = new byte[1024 * 10];
						int len = 0;
						while ((len = input_stream.read(data)) != -1) {
							output_stream.write(data, 0, len);
						}
						input_stream.close();
						output_stream.close();

					} catch (Exception e) {
						Message msg = Message.obtain();
						msg.what = MSG_ERROR;
						msg.obj = e == null ? "不明原因" : e.getMessage();
						handler.sendMessage(msg);
						return;
					}
				}
				DBHelper.setDatabasePath(db_file_path);
				helper = new DBHelper(context);
				database = helper.getWritableDatabase();
			} else {
				Message msg = Message.obtain();
				msg.what = MSG_ERROR;
				msg.obj = "存储卡不可用";
				handler.sendMessage(msg);
			}

		}

	};

	/**
	 * 得到城市的id
	 * 
	 * @param cityName
	 * @return
	 */
	public String getCityId(String cityName) {
		Cursor cursor = database.query(false, "third_level", null, "value = ?",
				new String[] { cityName }, null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() == 1) {
			String value = cursor.getString(cursor.getColumnIndex("code"));
			if (value.matches("^\\d{9}$")) {
				cityid = value;
			}
		} else {
			cityid = "";
		}
		cursor.close();
		return cityid;
	}

	/**
	 * 添加城市信息
	 * 
	 * @param cityName
	 * @param cityCode
	 * @return
	 */
	public boolean insertCityData(String cityName, String cityCode) {
		boolean flag = false;
		ContentValues values = new ContentValues();
		values.put("value", cityName);
		values.put("code", cityCode);
		long id = database.insert("third_level", null, values);
		flag = (id > 0 ? true : false);
		return flag;
	}

	/**
	 * 删除城市信息
	 * 
	 * @param cityName
	 * @return
	 */
	public boolean deleteCityData(String cityName) {
		boolean flag = false;
		int id = database.delete("third_level", "value = ?",
				new String[] { cityName });
		flag = (id > 0 ? true : false);
		return flag;
	}

}
