package com.example.colorfulweather.controller.json;


import org.json.JSONObject;

import com.example.colorfulweather.model.City;

public class JsonTools {

	public JsonTools() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 将从WEATHER_PATH_SK下载的数据保存到city对象中
	 * 
	 * @param jsonString
	 * @param city
	 * @return
	 */
	public static boolean saveJsonDataFromSK(String jsonString, City city) {
		boolean flag = false;
		try {
			JSONObject object1 = new JSONObject(jsonString);
			String temp = object1.getString("weatherinfo");
			JSONObject object2 = new JSONObject(temp);
			city.setCity(object2.getString("city"));
			city.setCityid(object2.getString("cityid"));
			city.setTemp(object2.getString("temp"));
			city.setWD(object2.getString("WD"));
			city.setWS(object2.getString("WS"));
			city.setSD(object2.getString("SD"));
			city.setTime(object2.getString("time"));
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 将从WEATHER_PATH_INFO下载的数据保存到city对象中
	 * 
	 * @param jsonString
	 * @param city
	 * @return
	 */
	public static boolean saveJsonDataFromINFO(String jsonString, City city) {
		boolean flag = false;
		try {
			JSONObject object1 = new JSONObject(jsonString);
			String temp = object1.getString("weatherinfo");
			JSONObject object2 = new JSONObject(temp);
			city.setTemp1(object2.getString("temp1"));
			city.setTemp2(object2.getString("temp2"));
			city.setWeather(object2.getString("weather"));
			city.setImg(object2.getString("img1"));
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
}
