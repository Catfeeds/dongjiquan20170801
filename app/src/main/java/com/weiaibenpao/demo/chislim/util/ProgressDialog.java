package com.weiaibenpao.demo.chislim.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

/**
 * Created by zhangxing on 2016/12/21.
 */

public class ProgressDialog {
    private Dialog progressDialog;
    public ProgressDialog(Context context) {
        progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("请稍等...");
    }

    public void showDialog(){
        progressDialog.show();
    }

    public void cancelDialog(){
        progressDialog.dismiss();
    }

}
