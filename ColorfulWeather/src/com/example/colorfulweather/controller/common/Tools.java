package com.example.colorfulweather.controller.common;

import java.io.File;

import com.example.colorfulweather.R;
import com.example.colorfulweather.controller.db.DBHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.widget.Toast;

public class Tools {

	/**
	 * ������Ϣ��
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastShowMsgShort(Context context, String msg) {
		if (context != null)
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ���������
	 * 
	 * @param context
	 * @param msg
	 * @param listener
	 */
	public static void dialogShowMsg(Context context, String msg,
			final DialogListener listener) {
		if (context != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("�������������").setIcon(R.drawable.logo)
					.setMessage(msg);
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							listener.onSuccess();
						}
					});
			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							listener.onCancel();
						}
					});
			builder.show();
		}
	}

	/**
	 * �������
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetCondition(Context context) {
		ConnectivityManager conn_manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network_info = conn_manager.getActiveNetworkInfo();
		if (network_info != null) {
			return network_info.getState() == State.CONNECTED;
		} else {
			return false;
		}
	}

	/**
	 * ������ݿ��Ƿ����
	 * 
	 * @return
	 */
	public static boolean checkDataBasePath() {
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/ColorfulWeather/" + DBHelper.DATABASE_NAME);
		return file.exists();
	}

	public interface DialogListener {

		public void onSuccess();

		public void onCancel();

	}

}
