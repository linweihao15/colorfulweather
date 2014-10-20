package com.example.colorfulweather.controller.theme;

import android.app.Activity;
import android.content.Intent;

import com.example.colorfulweather.R;
import com.example.colorfulweather.view.MainActivity;

public class ThemeTools {

	private static final int DEFAULT_THEME = 0;
	private static final int BLUE_THEME = 1;
	private static final int GRAY_THEME = 2;
	private static final int ORANGE_THEME = 3;
	private static final int AZURE_THEME = 4;
	private static final int PINK_THEME = 5;

	private static int select_theme = 0;
	private static ThemeAdapter adapter;
	
	
	/**
	 * 改变主题
	 * 
	 * @param activity
	 * @param theme
	 */
	public static void onChangeTheme(Activity activity, int theme) {
		select_theme = theme;
		activity.finish();
		Intent intent = new Intent(activity, activity.getClass());
		activity.startActivity(intent);
	}

	/**
	 * 更新主题
	 * 
	 * @param activity
	 */
	public static void updateTheme(Activity activity) {
		adapter = new ThemeAdapter(activity);
		switch (select_theme) {
		case DEFAULT_THEME:
			activity.setTheme(R.style.default_theme);
			adapter.setCheckable(DEFAULT_THEME);
			break;
		case BLUE_THEME:
			activity.setTheme(R.style.blue_theme);
			adapter.setCheckable(BLUE_THEME);
			break;
		case GRAY_THEME:
			activity.setTheme(R.style.gray_theme);
			adapter.setCheckable(GRAY_THEME);
			break;
		case ORANGE_THEME:
			activity.setTheme(R.style.orange_theme);
			adapter.setCheckable(ORANGE_THEME);
			break;
		case AZURE_THEME:
			activity.setTheme(R.style.azure_theme);
			adapter.setCheckable(AZURE_THEME);
			break;
		case PINK_THEME:
			activity.setTheme(R.style.pink_theme);
			adapter.setCheckable(PINK_THEME);
			break;
		}
	}
	
}
