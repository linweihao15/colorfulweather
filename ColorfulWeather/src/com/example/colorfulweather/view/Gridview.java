package com.example.colorfulweather.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.example.colorfulweather.R;
import com.example.colorfulweather.controller.common.Tools;
import com.example.colorfulweather.controller.common.Tools.DialogListener;
import com.example.colorfulweather.controller.theme.ThemeAdapter;
import com.example.colorfulweather.controller.theme.ThemeTools;

public class Gridview extends Activity {

	private GridView gridView;
	private ThemeAdapter adapter;
	private Button back_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ThemeTools.updateTheme(this);
		setContentView(R.layout.gridview);
		gridView = (GridView) this.findViewById(R.id.gridView);
		back_button = (Button) this.findViewById(R.id.back_btn);

		// 返回
		back_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Gridview.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

		adapter = new ThemeAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub

				Tools.dialogShowMsg(Gridview.this, "确定使用该主题？",
						new DialogListener() {

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								adapter.changeState(position);
								switch (position) {
								case 0:
									ThemeTools.onChangeTheme(Gridview.this, 0);
									break;
								case 1:
									ThemeTools.onChangeTheme(Gridview.this, 1);
									break;
								case 2:
									ThemeTools.onChangeTheme(Gridview.this, 2);
									break;
								case 3:
									ThemeTools.onChangeTheme(Gridview.this, 3);
									break;
								case 4:
									ThemeTools.onChangeTheme(Gridview.this, 4);
									break;
								case 5:
									ThemeTools.onChangeTheme(Gridview.this, 5);
									break;
								}
							}

							@Override
							public void onCancel() {
								// TODO Auto-generated method stub

							}
						});

			}
		});

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Gridview.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
