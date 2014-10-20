package com.example.colorfulweather.view;

import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.colorfulweather.R;
import com.example.colorfulweather.controller.common.Tools;
import com.example.colorfulweather.controller.common.Tools.DialogListener;
import com.example.colorfulweather.controller.db.DBManager;
import com.example.colorfulweather.controller.sharepreference.CityMsgService;
import com.example.colorfulweather.controller.theme.ThemeTools;

public class AddCityActivity extends Activity {

	private final int ERROR_MSG = 0x001;
	private final int SUCCESS_MSG = 0x002;
	private EditText editText;
	private Button btn_back;
	private String cityName = "";
	private ProgressDialog dialog;
	private DBManager manager;
	private CityMsgService service;
	private Map<String, ?> map = null;
	private boolean flag = false;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ERROR_MSG:
				Tools.toastShowMsgShort(AddCityActivity.this, "û���ҵ��ó��У�����������");
				break;
			case SUCCESS_MSG:
				service = new CityMsgService(AddCityActivity.this);
				map = service.getSharePreference("CityMsg");
				boolean sign = true;
				for (Entry<String, ?> entry : map.entrySet()) {
					if (entry.getKey().equals(cityName)) {
						Tools.toastShowMsgShort(AddCityActivity.this,
								"�ó�������ӣ�������ѡ��");
						sign = false;
						break;
					}
				}
				if (sign) {
					String cityCode = (String) msg.obj;
					service.addCityMsg(cityName, cityCode);
					if (flag) {
						Tools.toastShowMsgShort(AddCityActivity.this, "���  <"
								+ cityName + ">  �ɹ�");
					} else {
						Intent intent = new Intent(AddCityActivity.this,
								MainActivity.class);
						startActivity(intent);
						AddCityActivity.this.finish();
					}
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ThemeTools.updateTheme(this);
		setContentView(R.layout.addcity);
		Intent intent = getIntent();
		flag = intent.getBooleanExtra("flag", false);
		dialog = new ProgressDialog(this);
		dialog.setMessage("���ڼ���...");
		dialog.setCancelable(false);
		manager = new DBManager(this);
		btn_back = (Button) this.findViewById(R.id.addcity_btn_back);
		btn_back.setVisibility(flag ? View.VISIBLE : View.GONE);
		editText = (EditText) this.findViewById(R.id.addcity_text);
	}

	/**
	 * ��ť����¼�����
	 * 
	 * @param v
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addcity_btn_back:
			finish();
			break;
		case R.id.addcity_beijing:
			editText.setText("����");
			break;
		case R.id.addcity_dalian:
			editText.setText("����");
			break;
		case R.id.addcity_zhuhai:
			editText.setText("�麣");
			break;
		case R.id.addcity_guangzhou:
			editText.setText("����");
			break;
		case R.id.addcity_jieyang:
			editText.setText("����");
			break;
		case R.id.addcity_nanjing:
			editText.setText("�Ͼ�");
			break;
		case R.id.addcity_shanghai:
			editText.setText("�Ϻ�");
			break;
		case R.id.addcity_shenzhen:
			editText.setText("����");
			break;
		case R.id.addcity_tianjin:
			editText.setText("���");
			break;
		case R.id.addcity_btn_add:
			cityName = editText.getText().toString().trim();
			if (!cityName.equals("")) {
				new AddCityAsyncTask().execute(cityName);
			} else {
				Tools.toastShowMsgShort(this, "��������е�����");
			}
			break;
		}
	}

	/**
	 * �첽���ҳ���ID
	 * 
	 * @author weihao
	 * 
	 */
	private class AddCityAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = "";
			result = manager.getCityId(params[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.equals("")) {
				mHandler.sendEmptyMessage(ERROR_MSG);
			} else {
				Message msg = Message.obtain();
				msg.obj = result;
				msg.what = SUCCESS_MSG;
				mHandler.sendMessage(msg);
			}
			dialog.dismiss();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		manager.closeDataBase();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (flag) {
				finish();
			} else {
				Tools.dialogShowMsg(this, "�Ƿ��˳�Ӧ�ã�", new DialogListener() {

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
		}
		return super.onKeyDown(keyCode, event);
	}
}
