package com.example.colorfulweather.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colorfulweather.R;
import com.example.colorfulweather.controller.common.Tools;
import com.example.colorfulweather.controller.common.Tools.DialogListener;
import com.example.colorfulweather.controller.http.HttpTools;
import com.example.colorfulweather.controller.http.HttpUrl;
import com.example.colorfulweather.controller.json.JsonTools;
import com.example.colorfulweather.controller.sharepreference.CityMsgService;
import com.example.colorfulweather.controller.theme.ThemeTools;
import com.example.colorfulweather.model.City;

public class MainActivity extends FragmentActivity {

	private CityMsgService service;
	private ViewPager viewPager;
	private WeatherAdapter adapter;
	private Map<String, ?> map = null;
	private List<View> viewList = null;
	private List<City> cityList = null;
	private boolean update_flag = true;
	private ProgressDialog dialog;
	private Button btn_refresh;
	private ProgressBar bar;

	private int[] images = { R.drawable.weather00, R.drawable.weather01,
			R.drawable.weather02, R.drawable.weather03, R.drawable.weather04,
			R.drawable.weather05, R.drawable.weather06, R.drawable.weather07,
			R.drawable.weather08, R.drawable.weather09, R.drawable.weather10,
			R.drawable.weather11, R.drawable.weather12, R.drawable.weather13,
			R.drawable.weather14, R.drawable.weather15, R.drawable.weather16,
			R.drawable.weather17, R.drawable.weather18, R.drawable.weather19,
			R.drawable.weather20, R.drawable.weather21, R.drawable.weather22,
			R.drawable.weather23, R.drawable.weather24, R.drawable.weather25,
			R.drawable.weather26, R.drawable.weather27, R.drawable.weather28,
			R.drawable.weather29, R.drawable.weather30, R.drawable.weather31,
			R.drawable.weather32, R.drawable.weather33, R.drawable.weather35,
			R.drawable.weather34 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ThemeTools.updateTheme(this);
		setContentView(R.layout.main);
		viewPager = (ViewPager) this.findViewById(R.id.viewpager);
		btn_refresh = (Button)this.findViewById(R.id.refresh_btn);
		bar = (ProgressBar)this.findViewById(R.id.updateball);
		viewList = new ArrayList<View>();
		cityList = new ArrayList<City>();
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在加载数据...");
		dialog.setCancelable(false);
		service = new CityMsgService(this);
		map = service.getSharePreference("CityMsg");
		if (map == null || map.isEmpty()) {
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
			finish();
		} else if (map.size() == 0) {
			Intent intent = new Intent(this, AddCityActivity.class);
			intent.putExtra("flag", false);
			startActivity(intent);
		} else if (Tools.checkDataBasePath()) {
			new WeatherAsyncTask().execute(HttpUrl.WEATHER_PATH_INFO,
					HttpUrl.WEATHER_PATH_SK);
		} else {
			Tools.toastShowMsgShort(this, "加载数据失败");
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		update_flag = false;
		map = service.getSharePreference("CityMsg");
		new WeatherAsyncTask().execute(HttpUrl.WEATHER_PATH_INFO,
				HttpUrl.WEATHER_PATH_SK);
	}

	/**
	 * 异步加载天气数据
	 * 
	 * @author weihao
	 * 
	 */
	public class WeatherAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (update_flag) {
				dialog.show();
			} else {
				bar.setVisibility(View.VISIBLE);
				btn_refresh.setVisibility(View.GONE);
			}
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			boolean flag = false;
			cityList.clear();
			for (Entry<String, ?> entry : map.entrySet()) {
				String cityCode = (String) entry.getValue();
				String info_result = HttpTools.downloadJsonData(params[0],
						cityCode, "utf-8");
				String sk_result = HttpTools.downloadJsonData(params[1],
						cityCode, "utf-8");
				City city = new City();
				if (JsonTools.saveJsonDataFromINFO(info_result, city)
						&& JsonTools.saveJsonDataFromSK(sk_result, city)) {
					cityList.add(city);
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			return flag;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				updateWeather();
				adapter = new WeatherAdapter();
				viewPager.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				Tools.toastShowMsgShort(MainActivity.this, "加载数据失败，请刷新");
			}
			if (update_flag) {
				dialog.dismiss();
			} else {
				bar.setVisibility(View.GONE);
				btn_refresh.setVisibility(View.VISIBLE);
			}
		}

	}

	/**
	 * 天气适配器， 加载天气页面
	 * 
	 * @author weihao
	 * 
	 */
	private class WeatherAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(viewList.get(position));
			return viewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(viewList.get(position));
		}

	}

	/**
	 * 
	 * @author weihao
	 * 
	 */
	private class WeatherView {
		TextView city, temp, temp1, temp2, weather, WD, WS, SD, time;
		ImageView img;
	}

	/**
	 * 更新天气信息
	 */
	private void updateWeather() {
		viewList.clear();
		for (int i = 0; i < cityList.size(); i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.weather,
					null);
			WeatherView weatherView = new WeatherView();
			weatherView.city = (TextView) view.findViewById(R.id.city);
			weatherView.city.setText(cityList.get(i).getCity());
			weatherView.temp = (TextView) view.findViewById(R.id.temp);
			weatherView.temp.setText(cityList.get(i).getTemp());
			weatherView.temp1 = (TextView) view.findViewById(R.id.low_temp);
			weatherView.temp1.setText(cityList.get(i).getTemp2());
			weatherView.temp2 = (TextView) view.findViewById(R.id.high_temp);
			weatherView.temp2.setText(cityList.get(i).getTemp1());
			weatherView.weather = (TextView) view.findViewById(R.id.weathermsg);
			weatherView.weather.setText(cityList.get(i).getWeather());
			weatherView.WD = (TextView) view.findViewById(R.id.WD);
			weatherView.WD.setText(cityList.get(i).getWD());
			weatherView.WS = (TextView) view.findViewById(R.id.WS);
			weatherView.WS.setText(cityList.get(i).getWS());
			weatherView.SD = (TextView) view.findViewById(R.id.SD);
			weatherView.SD.setText(cityList.get(i).getSD());
			weatherView.time = (TextView) view.findViewById(R.id.time);
			weatherView.time.setText(cityList.get(i).getTime());
			weatherView.img = (ImageView) view.findViewById(R.id.weatherimage);
			String image_num = cityList.get(i).getImg();
			int result = Integer.parseInt(image_num.substring(1,
					image_num.lastIndexOf(".gif")));
			Calendar calendar = Calendar.getInstance();
			if ((result == 0 || result == 1 || result == 3)
					&& calendar.get(Calendar.HOUR_OF_DAY) >= 18) {
				weatherView.img.setImageResource(images[result + 32]);
			} else {
				weatherView.img.setImageResource(images[result]);
			}
			viewList.add(view);
		}
	}

	/**
	 * 刷新按钮事件
	 * 
	 * @param view
	 */
	public void onClick(View view) {
		if (map.size() > 0) {
			update_flag = false;
			new WeatherAsyncTask().execute(HttpUrl.WEATHER_PATH_INFO,
					HttpUrl.WEATHER_PATH_SK);
		} else {
			Tools.dialogShowMsg(this, "您还没添加城市", new DialogListener() {
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,
							AddCityActivity.class);
					intent.putExtra("flag", false);
					startActivity(intent);
					finish();
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
				}
			});
		}
	}

	/**
	 * 弹出菜单
	 * 
	 * @param v
	 */
	public void showPopup(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.main, popup.getMenu());
		popup.show();
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.addCity:
					Intent intent = new Intent(MainActivity.this,
							AddCityActivity.class);
					intent.putExtra("flag", true);
					startActivity(intent);
					return true;
				case R.id.changeTheme:
					Intent intent3 = new Intent(MainActivity.this,
							Gridview.class);
					startActivity(intent3);
					finish();
					return true;
				case R.id.share:
					Toast.makeText(MainActivity.this, "还没弄呢，别分享了",
							Toast.LENGTH_SHORT).show();
					return true;
				case R.id.setting:
					Toast.makeText(MainActivity.this, "有什么好设置的，将就用得了",
							Toast.LENGTH_SHORT).show();
					return true;
				default:
					break;
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Tools.dialogShowMsg(this, "是否退出应用？", new DialogListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					finish();
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});
		}
		return super.onKeyDown(keyCode, event);
	}

}
