package com.example.colorfulweather.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.colorfulweather.R;
import com.example.colorfulweather.controller.common.Tools;
import com.example.colorfulweather.controller.common.Tools.DialogListener;

public class WelcomeActivity extends Activity {

	private Button btn_begin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		btn_begin = (Button) this.findViewById(R.id.welcome_btn_begin);
		btn_begin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,
						AddCityActivity.class);
				intent.putExtra("flag", false);
				startActivity(intent);
				finish();
			}
		});
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
