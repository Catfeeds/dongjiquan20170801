package com.weiaibenpao.demo.chislim.util;

import android.content.Context;

import com.weiaibenpao.demo.chislim.bean.BluResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/11/23.
 */

public class BluDataUtil {

    /**
     * 字符串的拼接,
     * 用来处理接收到的字符串
     */
    String strData = null;                 //用来接收蓝牙收到的字符串
    public BluResult SplitStr(String str, Context context){
        strData += str;
        Pattern p = Pattern.compile("EE(.*)FF");      //截取EE  FF  之间的所有字符串
        Matcher m = p.matcher(strData);
        while (m.find()){
            //Log.i("蓝牙","-------------------"+strData+"--------------------");

            String str5 = m.group(1);                      //找到符合要求的字符串,EE与FF之间
            //去掉后四位校验码
            String str6 = SplitStrTF(str5,0,str5.length()-4);
            //ASCII求和
            int num = stringToAscii(str6);
            //十进制转换为十六进制
            String hexstr = toHexStr(num);
            //截取后两位
            String str3 = hexstr.substring(hexstr.length()-2,hexstr.length());
            //十六进制转换为十进制
            int num2 = toParseInt(str3);
            //将十进制整形转换为字符串
            String str4 = String.valueOf(num2);
            //如果不足三位前边加0
            if(str4.length() == 2){
                str4 = "0" + str4;
            }
            if(str4.length() == 1){
                str4 = "00" + str4;
            }
            //取后边的校验码
            String str2 = SplitStrTF(str5,str5.length()-4,str5.length()-1);
            //Log.i("蓝牙","---计算值： " + str4 + " ------- 收到值： " + str2 + "---");
            //Toast.makeText(context,"---计算值： " + str4 + " ------- 收到值： " + str2 + "---",Toast.LENGTH_SHORT).show();
            //判断校验码是否正确
            if(str4.equals(str2)){
                //数据正确
                //解析字符串放到BluResult中
                strData = null;
                return AnalyzeStr(str6);
            }
            strData = null;
        }
        return null;
    }

    /**
     * 截取字符串的前i和后n位
     */
    public String  SplitStrTF(String str,int i,int n){
        int length = str.length();
        if(length<8 ){

        }
        return str.substring(i,n);
    }

    /**
     * 将字符串转成ASCII的Java方法,并且求和
     */
    public static int stringToAscii(String  str) {
        int num = 0;
        StringBuffer sbu = new StringBuffer();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length)
            {
                sbu.append(chars[i] +"-"+ (int)chars[i]).append(",");
                num = num + (int)chars[i];
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return num;
    }

    /**
     * 十进制转十六进制
     */
    public String toHexStr(int um){
        return Integer.toHexString(um);
    }


    /**
     * 十六进制转十进制
     */
    public int toParseInt(String str){
        return Integer.parseInt(str, 16);
    }

    /**
     * 字符串的解析
     */
    /**
     * 蓝牙返回值对象
     * String strd = temp[i].replaceAll("[0-9.]", "");      //字母
       String strb = temp[i].replaceAll("[a-zA-Z]", "");    //数字
     */
    BluResult bluResult = new BluResult();
    public BluResult AnalyzeStr(String str){

        String strdata = SplitStrTF(str,1,str.length()-1);          //把前边的逗号和最后的逗号去掉
        String[] temp = strdata.split(",");

        for(int i = 0;i < temp.length;i++){
            if(i == 0){                             //错误码
                bluResult.code = temp[0].replaceAll("[a-zA-Z]", "");
            }else if(i == 1){                      //坡度是否可调
                bluResult.slopeFlag = temp[1].replaceAll("[a-zA-Z]", "");
            }else if(i == 2){                      //跑步机状态
                bluResult.state = temp[2].replaceAll("[a-zA-Z]", "");
            }else if(i == 3){                      //最大速度
                bluResult.maxSpeed = temp[3].replaceAll("[a-zA-Z]", "");
            }else if(i == 4){                       //最大坡度
                bluResult.maxSlope = temp[4].replaceAll("[a-zA-Z]", "");
            }else if(i == 5){                        //最小速度
                bluResult.minSpeed = temp[5].replaceAll("[a-zA-Z]", "");
            }else if(i == 6){                        //卡路里
                bluResult.calories = temp[6].replaceAll("[a-zA-Z]", "");
            }else if(i == 7){                      //里程
                bluResult.distance = temp[7].replaceAll("[a-zA-Z]", "");
            }else if(i == 8){                        //心率
                bluResult.heart = temp[8].replaceAll("[a-zA-Z]", "");
            }else if(i == 9){                         //坡度
                bluResult.slope = temp[9].replaceAll("[a-zA-Z]", "");
            }else if(i == 10){                         //速度
                bluResult.speed = temp[10].replaceAll("[a-zA-Z]", "");
            }else if(i == 11){                         //时间
                bluResult.time = temp[11].replaceAll("[a-zA-Z]", "");
            }
        }
        return bluResult;
    }

    /**
     * 速度增加
     * 10.5
     */
    public String addSpeed(String str2){
        String str1 = "EE,p";

        String str3 = ",IDchislim,";

        String str5 = ",FF";

        //求校验码
        String str6 = str1 + str2 + str3;
        //取掉前两位
        String str7 = SplitStrTF(str6,2,str6.length());
        //ASII求和
        int num = stringToAscii(str7);
        //十进制转十六进制
        String str8 = toHexStr(num);
        //截取后两位
        String str9 = SplitStrTF(str8,str8.length()-2,str8.length());
        //十六进制转十进制
        int num2 =  toParseInt(str9);
        //将十进制整形转换为字符串
        String str4 = String.valueOf(num2);
        //如果不足三位前边加0
        if(str4.length() == 2){
            str4 = "0" + str4;
        }
        if(str4.length() == 1){
            str4 = "00" + str4;
        }
        String str10 = str1+str2+str3+str4+str5;
        return str10;
    }

    /**
     * 坡度调节
     * 10
     */
    public String addSlope(String str2){
        String str1 = "EE,s";

        String str3 = ",IDchislim,";

        String str5 = ",FF";

        //求校验码
        String str6 = str1 + str2 + str3;
        //取掉前两位
        String str7 = SplitStrTF(str6,2,str6.length());
        //ASII求和
        int num = stringToAscii(str7);
        //十进制转十六进制
        String str8 = toHexStr(num);
        //截取后两位
        String str9 = SplitStrTF(str8,str8.length()-2,str8.length());
        //十六进制转十进制
        int num2 =  toParseInt(str9);
        //将十进制整形转换为字符串
        String str4 = String.valueOf(num2);
        //如果不足三位前边加0
        if(str4.length() == 2){
            str4 = "0" + str4;
        }
        if(str4.length() == 1){
            str4 = "00" + str4;
        }
        String str10 = str1+str2+str3+str4+str5;
        return str10;
    }

    /**
     * 启动结束调节
     * 10
     */
    public String startStop(String str2){
        String str1 = "EE,A";

        String str3 = ",IDchislim,";

        String str5 = ",FF";

        //求校验码
        String str6 = str1 + str2 + str3;
        //取掉前两位
        String str7 = SplitStrTF(str6,2,str6.length());
        //ASII求和
        int num = stringToAscii(str7);
        //十进制转十六进制
        String str8 = toHexStr(num);
        //截取后两位
        String str9 = SplitStrTF(str8,str8.length()-2,str8.length());
        //十六进制转十进制
        int num2 =  toParseInt(str9);
        //将十进制整形转换为字符串
        String str4 = String.valueOf(num2);
        //如果不足三位前边加0
        if(str4.length() == 2){
            str4 = "0" + str4;
        }
        if(str4.length() == 1){
            str4 = "00" + str4;
        }
        String str10 = str1+str2+str3+str4+str5;
        return str10;
    }
}
