package com.weiaibenpao.demo.chislim.ble.service;


import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.weiaibenpao.demo.chislim.ble.utils.BluetoothController;
import com.weiaibenpao.demo.chislim.ble.utils.ConstantUtils;


public class BLEService extends Service {
	BluetoothController bleCtrl;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * 其中的serviceHandler是在service中初始化的，所以它会发消息到service中
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ConstantUtils.WM_BLE_CONNECTED_STATE_CHANGE://搜索5秒后停止搜索
				Bundle bundle = (Bundle) msg.obj;
				String address = bundle.getString("address");
				String name = bundle.getString("name");
				//更新蓝牙列表广播
				Bundle bundle1 = new Bundle();
				bundle1.putString("address", address);
				bundle1.putString("name", name);
				Intent intentDevice = new Intent(
						ConstantUtils.ACTION_CONNECTED_ONE_DEVICE);
				intentDevice.putExtras(bundle1);
				sendBroadcast(intentDevice);
				break;

			case ConstantUtils.WM_STOP_CONNECT:
				Intent stopConnect = new Intent(
						ConstantUtils.ACTION_STOP_CONNECT);
				sendBroadcast(stopConnect);
				break;

			case ConstantUtils.WM_STOP_SCAN_BLE:// ����5���ֹͣ����
				bleCtrl.stopScanBLE();
				break;
			case ConstantUtils.WM_UPDATE_BLE_LIST:// �ص������ĸ����б���Ϣ
				// ���������б�㲥
				Intent intent = new Intent(
						ConstantUtils.ACTION_UPDATE_DEVICE_LIST);
				BluetoothDevice device = (BluetoothDevice) msg.obj;
				intent.putExtra("name", device.getName());
				intent.putExtra("address", device.getAddress());
				sendBroadcast(intent);
				break;

			case ConstantUtils.WM_RECEIVE_MSG_FROM_BLE:// ���ܵ��������͵���Ϣ
				String mes = (String) msg.obj;
				Intent mesDevice = new Intent(
						ConstantUtils.ACTION_RECEIVE_MESSAGE_FROM_DEVICE);
				mesDevice.putExtra("message", mes);
				sendBroadcast(mesDevice);
				break;

			}
		}
	};

	public void onStart(Intent intent, int startId) {
		bleCtrl = BluetoothController.getInstance();
		bleCtrl.setServiceHandler(handler);
	};

}
