package com.weiaibenpao.demo.chislim.ble.utils;

import android.util.Log;

/**
 * Created by lenovo on 2016/11/21.
 */

public class Util {
    public Util() {
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台
     * @param b byte[]
     * @return void
     */
    public static String printHexString( byte[] b) {
        String hex = null;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        return hex;
    }

    /**
     *
     * @param b byte[]
     * @return String
     */
    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 将两个ASCII字符合成一个字节；
     * 如："EF"--> 0xEF
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte)(_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte)(_b0 ^ _b1);
        return ret;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式
     * 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
     * @param src String
     * @return byte[]
     */
    public static byte[] HexString2Bytes(String src){
        byte[] ret = new byte[8];
        byte[] tmp = src.getBytes();
        for(int i=0; i<8; i++){
            ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);
        }
        return ret;
    }


    /**
     * 将十六进制字符串转换为字符串
     * @param s
     * @return
     */
    public static String toStringHex(String s)
    {
        byte[] baKeyword = new byte[s.length()/2];
        for(int i = 0; i < baKeyword.length; i++)
        {
            try
            {
                baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        return s;
    }


    /**
     * 将字符串转成ASCII的Java方法
     */
    int num;
    public static String stringToAscii(String  str)
    {
        Log.i("蓝牙",str);
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
        return sbu.toString();
    }

    /**
     * 将ASCII转成字符串的java方法
     * @param value
     * @return
     */

    public static String ASCIIToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * 将十进制转换为十六进制
     */
    public String ToHexString(int n){
        Integer.toHexString(n);
        return Integer.toHexString(n);
    }

}