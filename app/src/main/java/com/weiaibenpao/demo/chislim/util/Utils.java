/**
 * 
 */
package com.weiaibenpao.demo.chislim.util;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.amap.api.location.AMapLocation;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.common.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils {
	/**
	 *  开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP= 2;
	
	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";
	/**
	 * 根据定位结果返回定位信息的字符串
	 * @param
	 * @return
	 */
	public synchronized static String getLocationStr(AMapLocation location){
		if(null == location){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		//errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		if(location.getErrorCode() == 0){
			sb.append("定位成功" + "\n");
			sb.append("定位类型: " + location.getLocationType() + "\n");
			sb.append("经    度    : " + location.getLongitude() + "\n");
			sb.append("纬    度    : " + location.getLatitude() + "\n");
			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
			sb.append("提 供 者    : " + location.getProvider() + "\n");
			
			if (location.getProvider().equalsIgnoreCase(
					android.location.LocationManager.GPS_PROVIDER)) {
				// 以下信息只有提供者是GPS时才会有
				sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
				sb.append("角    度    : " + location.getBearing() + "\n");
				// 获取当前提供定位服务的卫星个数
				sb.append("星    数    : "
						+ location.getSatellites() + "\n");
			} else {
				// 提供者是GPS时是没有以下信息的
				sb.append("国    家    : " + location.getCountry() + "\n");
				sb.append("省            : " + location.getProvince() + "\n");
				sb.append("市            : " + location.getCity() + "\n");
				sb.append("城市编码 : " + location.getCityCode() + "\n");
				sb.append("区            : " + location.getDistrict() + "\n");
				sb.append("区域 码   : " + location.getAdCode() + "\n");
				sb.append("地    址    : " + location.getAddress() + "\n");
				sb.append("兴趣点    : " + location.getPoiName() + "\n");
				//定位完成的时间
				sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss:sss") + "\n");
			}
		} else {
			//定位失败
			sb.append("定位失败" + "\n");
			sb.append("错误码:" + location.getErrorCode() + "\n");
			sb.append("错误信息:" + location.getErrorInfo() + "\n");
			sb.append("错误描述:" + location.getLocationDetail() + "\n");
		}
		//定位之后的回调时间
		sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:sss") + "\n");
		return sb.toString();
	}
	
	private static SimpleDateFormat sdf = null;
	public synchronized static String formatUTC(long l, String strPattern) {
		if (TextUtils.isEmpty(strPattern)) {
			strPattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (sdf == null) {
			try {
				sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
			} catch (Throwable e) {
			}
		} else {
			sdf.applyPattern(strPattern);
		}
		if (l <= 0l) {
			l = System.currentTimeMillis();
		}
		return sdf == null ? "NULL" : sdf.format(l);
	}


	/**
	 * 得到屏幕宽度
	 */
	public static int getScreenWidth() {
		Context context = BaseApplication.getAppContext();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;

		return screenWidth;
	}

	/**
	 * 得到屏幕高度
	 */
	public static int getScreenHeight() {
		Context context = BaseApplication.getAppContext();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenHeight = size.y;

		return screenHeight;
	}

	public static String  getFormattedTime(long time,String format) {
		Date date = new Date(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String result = dateFormat.format(date);
		return result;
	}


	public static void showSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		//imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static void hideSoftInput(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
	}

	public static boolean isShowSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		//获取状态信息
		return imm.isActive();//true 打开
	}
}
