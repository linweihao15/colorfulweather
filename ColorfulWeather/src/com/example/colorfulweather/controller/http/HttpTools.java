package com.example.colorfulweather.controller.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpTools {

	public HttpTools() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ����JSON����
	 * @param url
	 * @param cityid
	 * @param encoding
	 * @return 
	 */
	public static String downloadJsonData(String url, String cityid, String encoding) {
		String result = "";
		String path = url+cityid+".html";
		HttpClient client = new DefaultHttpClient();
		try {
			// ��Ϊ�й�������ֻ�ܽ���get��������������HttpGet
			HttpGet get = new HttpGet(path);
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), encoding);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}
}
