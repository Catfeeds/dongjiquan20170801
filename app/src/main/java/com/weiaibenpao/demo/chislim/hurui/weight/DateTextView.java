package com.weiaibenpao.demo.chislim.hurui.weight;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * User:ZJL
 * Date:2015-07-01
 * Description:
 */
public class DateTextView extends AppCompatTextView {// TextView换成了 AppCompatTextView


    //服务器返回的动态发布时间的格式,用解析时间文本
    private static final String STATUS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SMALL_DATE_FORMAT = "yyyy-MM-   dd";
    //当发布时间大于1年所用的时间格式
    private static final String YEAR_FORMAT = "yyyy-MM-dd";
    //当发布时间大于2天所用的时间格式
    private static final String MONTH_FORMAT = "MM-dd";
    //private static final String MONTH_FORMAT = "MM-dd HH:mm";
    //当发布时间为昨天所用的时间格式
    private static final String YESTERDAY_FORMAT = "HH:mm";

    private static final String TEXT_YESTERDAY = "昨天 ";
    private static final String TEXT_HOUR_AGO = " 小时前";
    private static final String TEXT_MIN_AGO = " 分钟前";
    private static final String TEXT_JUST_NOW = "刚刚";
    private static final String TEXT_UNKNOWN = "未知";


    private Calendar mCalendar;

    private int mCurrentYear;
    private int mCurrentDay;

    public DateTextView(Context context) {
        super(context);
        init();
    }

    public DateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    private void init() {
        //当前年份
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        mCalendar = Calendar.getInstance();
        mCurrentYear = mCalendar.get(Calendar.YEAR);
        mCurrentDay = mCalendar.get(Calendar.DAY_OF_YEAR);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            super.setText(dateFormat(text.toString()), type);
        } else {
            super.setText(text, type);
        }
    }


    public String dateFormat(String dateStr) {
        String dateFormat;
        try {
            SimpleDateFormat sdf;
            Date date;
            if(dateStr.length() > 10) {
                date = new SimpleDateFormat(STATUS_DATE_FORMAT).parse(dateStr);
            }else{
                date = new SimpleDateFormat(SMALL_DATE_FORMAT).parse(dateStr);
            }

            //获取是几年、几日、几小时、几分钟前发的微博
            mCalendar.setTime(date);

            int dateYear = mCalendar.get(Calendar.YEAR);
            int dateDay = mCalendar.get(Calendar.DAY_OF_YEAR);

            int diffYear = mCurrentYear - dateYear;
            int diffDay = mCurrentDay - dateDay;
            long diffTime = Long.valueOf((System.currentTimeMillis() - date.getTime()) / 1000);
            long minuteTime = diffTime / 60;
            long hourTime = minuteTime / 60;

            if (diffTime < 0 || diffYear >= 1) {//大于等于1年
                sdf = new SimpleDateFormat(YEAR_FORMAT);
                dateFormat = sdf.format(date);
            } else if (diffDay >= 2) {//大于等于2天
                sdf = new SimpleDateFormat(MONTH_FORMAT);
                dateFormat = sdf.format(date);
            } else if (diffDay == 1) {//昨天
                sdf = new SimpleDateFormat(YESTERDAY_FORMAT);
                dateFormat = TEXT_YESTERDAY + sdf.format(date);
            } else if (minuteTime >= 60) {//今天几小时前
                dateFormat = hourTime + TEXT_HOUR_AGO;
            } else if (minuteTime >= 5) {//几分钟前
                dateFormat = minuteTime + TEXT_MIN_AGO;
            } else {
                dateFormat = TEXT_JUST_NOW;
            }
        } catch (Exception e) {
            e.printStackTrace();
            dateFormat = TEXT_UNKNOWN;
        }

        return dateFormat;
    }
}
