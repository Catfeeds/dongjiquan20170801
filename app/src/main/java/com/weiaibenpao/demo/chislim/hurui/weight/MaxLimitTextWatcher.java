package com.weiaibenpao.demo.chislim.hurui.weight;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/8/17.
 */

public class MaxLimitTextWatcher implements TextWatcher {
    private int mMaxBytes;
    private EditText mEditText;

    public MaxLimitTextWatcher(EditText editText, int maxBytes) {
        this.mEditText = editText;
        this.mMaxBytes = maxBytes;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Editable editable = mEditText.getText();
        int len = editable.toString().getBytes().length;

        if(len > mMaxBytes)
        {
            int selEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            //截取新字符串
            String newStr = getWholeText(str, mMaxBytes);
            mEditText.setText(newStr);
            editable = mEditText.getText();

            //新字符串的长度
            int newLen = editable.length();
            //旧光标位置超过字符串长度
            if(selEndIndex > newLen)
            {
                selEndIndex = editable.length();
            }
            //设置新光标所在的位置
            Selection.setSelection(editable, selEndIndex);
        }
    }
    public static String getWholeText(String text, int byteCount){
        try {
            if (text != null && text.getBytes("GBK").length > byteCount) {
                char[] tempChars = text.toCharArray();
                int sumByte = 0;
                int charIndex = 0;
                for (int i = 0, len = tempChars.length; i < len; i++) {
                    char itemChar = tempChars[i];
                    // 根据Unicode值，判断它占用的字节数0x3000到0x9FFF
                    if (itemChar >= 0x3000 && itemChar <= 0x9FFF) {
                        sumByte += 2;
                    }  else {
                        sumByte += 1;
                    }
                    if (sumByte > byteCount) {
                        charIndex = i;
                        break;
                    }
                }
                return String.valueOf(tempChars, 0, charIndex);
            }
        } catch (UnsupportedEncodingException e) {
        }
        return text;
    }
}
