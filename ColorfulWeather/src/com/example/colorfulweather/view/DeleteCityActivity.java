package com.example.colorfulweather.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.colorfulweather.R;
import com.example.colorfulweather.controller.common.Tools;
import com.example.colorfulweather.controller.common.Tools.DialogListener;
import com.example.colorfulweather.controller.sharepreference.CityMsgService;

public class DeleteCityActivity extends Activity {

	private List<CityData> list = null;
	private ListView listView;
	private Button btn_confirm, btn_cancel;
	private CityMsgService service;
	private Map<String, ?> map = null;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deletecity);
		listView = (ListView) this.findViewById(R.id.deletecity_listView);
		btn_confirm = (Button) this.findViewById(R.id.deletecity_btn_confirm);
		btn_cancel = (Button) this.findViewById(R.id.deletecity_btn_cancel);
		btn_confirm.setOnClickListener(clickListener);
		btn_cancel.setOnClickListener(clickListener);
		list = new ArrayList<DeleteCityActivity.CityData>();
		service = new CityMsgService(this);
		map = service.getSharePreference("CityMsg");
		if (map.size() > 0) {
			List<String> cityname_list = new ArrayList<String>();
			for (Entry<String, ?> entry : map.entrySet()) {
				CityData data = new CityData();
				data.setCityName(entry.getKey());
				list.add(data);
			}
			for (int i = 0; i < list.size(); i++) {
				cityname_list.add(list.get(i).getCityName());
			}
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			adapter = new ArrayAdapter<String>(DeleteCityActivity.this,
					android.R.layout.simple_list_item_multiple_choice,
					cityname_list);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					boolean sign = list.get(arg2).isSign();
					list.get(arg2).setSign(!sign);
				}
			});
		} else {
			Tools.dialogShowMsg(this, "尚未添加城市，是否添加？", new DialogListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DeleteCityActivity.this,
							AddCityActivity.class);
					intent.putExtra("flag", true);
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

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.deletecity_btn_confirm:
//				for (int i = 0; i < list.size(); i++) {
//					if (list.get(i).isSign()) {
//						service.deleteCityMsg(list.get(i).getCityName());
//					}
//				}
				finish();
				break;
			case R.id.deletecity_btn_cancel:
				finish();
				break;
			}
		}
	};

	/**
	 * 要删除的城市信息
	 * 
	 * @author weihao
	 * 
	 */
	private class CityData {
		String cityName;
		boolean sign = false;

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
		}

		public boolean isSign() {
			return sign;
		}

		public void setSign(boolean sign) {
			this.sign = sign;
		}
	}

}
