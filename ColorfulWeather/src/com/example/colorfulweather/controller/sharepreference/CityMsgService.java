package com.example.colorfulweather.controller.sharepreference;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CityMsgService {

	private Context context;
	
	public CityMsgService(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	//添加城市
	public boolean addCityMsg(String cityName, String cityCode){
		boolean flag=false;
		SharedPreferences preferences = context.getSharedPreferences("CityMsg",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(cityName, cityCode);
		flag = editor.commit();
		return flag;
	}
	
	//获取全部城市数据
	public Map<String, ?> getSharePreference(String fileName) {
		Map<String, ?> map = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		map = preferences.getAll();
		return map;
	}
	
	//删除城市
	public boolean deleteCityMsg(String cityName){
		boolean flag=false;
		SharedPreferences preferences = context.getSharedPreferences("CityMsg",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(cityName);
		flag = editor.commit();
		return flag;
	}
	
	//清空数据
	public boolean deleteAll(){
		boolean flag = false;
		SharedPreferences preferences = context.getSharedPreferences("CityMsg",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.clear();
		flag = editor.commit();
		return flag;
	}
}
