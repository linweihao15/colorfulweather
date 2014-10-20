package com.example.colorfulweather.controller.http;

public class HttpUrl {

	// 获取城市当天的天气数据 city, cityid, temp1, temp2, weather, img
	public static String WEATHER_PATH_INFO = "http://www.weather.com.cn/data/cityinfo/";
	// 获取城市当天的天气数据 city, cityid, temp, WD, WS, SD, time
	public static String WEATHER_PATH_SK = "http://www.weather.com.cn/data/sk/";
	//获取城市的七天的天气数据
	public static String WEATHER_PATH_DATA = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	//获取城市七天天气的链接后缀
	public static String suffix = "&units=metric&lang=zh_cn";
}
