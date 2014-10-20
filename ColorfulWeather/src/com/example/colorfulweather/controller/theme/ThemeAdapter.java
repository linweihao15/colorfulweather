package com.example.colorfulweather.controller.theme;

import com.example.colorfulweather.model.ThemeImage;
import com.example.colorfulweather.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ThemeAdapter extends BaseAdapter {

	private Context context;
	private int lastposition = -1;
	private int[] themes = { R.drawable.gridview_theme1,
			R.drawable.gridview_theme2, R.drawable.gridview_theme3,
			R.drawable.gridview_theme4, R.drawable.gridview_theme5,
			R.drawable.gridview_theme6 };
	private boolean[] checkable = new boolean[6];

	public ThemeAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return themes.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return themes[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ThemeImage theme = null;
		if (convertView == null) {
			theme = new ThemeImage();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.imageitem, null);
			theme.themeimage = (ImageView) convertView.findViewById(R.id.theme);
			theme.check = (ImageView) convertView.findViewById(R.id.check);
			convertView.setTag(theme);
		} else {
			theme = (ThemeImage) convertView.getTag();
		}
		theme.themeimage.setImageResource(themes[position]);
		if (checkable[position]) {
			theme.check.setVisibility(View.VISIBLE);
		} else {
			theme.check.setVisibility(View.GONE);
		}
		return convertView;
	}

	public void changeState(int position) {
		if (lastposition != -1)
			checkable[position] = false;
		checkable[position] = !checkable[position];
		lastposition = position;
	}

	public void setCheckable(int position) {
		for (int i = 0; i < checkable.length; i++) {
			checkable[i] = (i == position ? true : false);
		}
	}

}
