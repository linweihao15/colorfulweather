package com.example.colorfulweather.controller.http;

public class HttpUrl {

	// ��ȡ���е������������ city, cityid, temp1, temp2, weather, img
	public static String WEATHER_PATH_INFO = "http://www.weather.com.cn/data/cityinfo/";
	// ��ȡ���е������������ city, cityid, temp, WD, WS, SD, time
	public static String WEATHER_PATH_SK = "http://www.weather.com.cn/data/sk/";
	//��ȡ���е��������������
	public static String WEATHER_PATH_DATA = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	//��ȡ�����������������Ӻ�׺
	public static String suffix = "&units=metric&lang=zh_cn";
}
