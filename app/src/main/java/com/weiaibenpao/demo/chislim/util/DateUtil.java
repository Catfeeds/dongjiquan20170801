package com.weiaibenpao.demo.chislim.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lenovo on 2017/2/15.
 */

public class DateUtil {

    /**
     * 获取系统当前日期
     * @return
     */
    public static   String getNowDate(){
        Date dt = new Date();
        SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = matter1.format(dt);
        /*Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);*/
        return  dateStr;
    }

    /**
     * 制作标记
     */
    public static String getNowDate(String str){
        SimpleDateFormat formatter = new SimpleDateFormat(str);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 字符串转换为日期
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date getDate(String s) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = format.parse(s);

        return date;
    }

    public static int getTime(String str){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int time = (int) (date.getTime()/1000);

        return time;
    }

    /**
     * 将日期转换为字符串
     * @param date
     * @return
     */
    public String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String str = format.format(date);
        return str;
    }

    /**
     * 日期加一天
     * @param date
     * @return
     */
    public Date addDateOne(Date date){

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR,1); //日期加1天
        date = rightNow.getTime();

        return date;
    }

    /**
     * 日期减一天
     * @param date
     * @return
     */
    public Date loseDateOne(Date date){

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR,-1); //日期加1天
        date = rightNow.getTime();

        return date;
    }

    /**
     * 格式化日期
     * str    "yyyyMMdd"
     * @return
     */
    public String getData(Date date,String str){
        SimpleDateFormat formatter = new SimpleDateFormat(str);
        return formatter.format(date);
    }


    /**
     * 将秒转换为小时分钟秒的格式
     * @param seconds
     * @return
     */
    public static String getFormatTime(int seconds){
        String hourf="";
        String minf = "";
        String secf ="";
        int hour = 0;
        int min = 0;
        int second = 0;

        if(seconds>=3600){

            hour = seconds /3600;
            int s  = seconds - hour*3600;
            min = s / 60;
            second =seconds-hour*3600 - min *60;

        }else if(seconds<3600&&seconds>60){
            hour = 0;
            min = seconds / 60;
            second  = seconds -min*60 ;
        }else{
            hour = 0;
            min = 0;
            second = seconds;
        }

        if(hour<10){
            hourf = "0"+hour;
        }else{
            hourf =hour+"";
        }
        if(min<10){
            minf = "0"+min;
        }else{
            minf = min+"";
        }
        if(second<10){
            secf = "0"+second;
        }else{
            secf =second+"";
        }

        String text = hourf+":"+minf+":"+secf+"";
        return text;

    }


    /**
     * 获取系统当前时间
     * @return
     */
    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    //毫秒转秒
    /*public static String changeTime(long sd){
        Date dat=new Date(sd);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("hh:mm:ss");
        return format.format(gc.getTime());
    }*/
    public static String changeTime(long sd){
        long min = sd/60000;
        String m ="";
        if (min<10){
            m = "0"+min;
        }else {
            m = ""+min;
        }

        long second1 = sd/1000;
        long second = second1%60;
        String n ="";
        if (second<10){
            n = "0"+second;
        }else {
            n = ""+second;
        }
        return m+":"+n;
    }


    //时间戳转换为日期
    public static String getTime(long time){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月");

        String sd = sdf.format(new Date(time));

        return sd;
    }

    //时间戳转换为日期
    public static String getTimeYMD(long time){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        String sd = sdf.format(new Date(time));

        return sd;
    }

    //时间戳转换为日期
    public static String getTimeYMDByCalendar(long time){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        String sd = sdf.format(new Date(time));

        return sd;
    }

}
