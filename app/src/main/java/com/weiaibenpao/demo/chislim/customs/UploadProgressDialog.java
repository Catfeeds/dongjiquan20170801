package com.weiaibenpao.demo.chislim.customs;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

/**
 * Created by zhangxing on 2017/1/13.
 */

public class UploadProgressDialog {
        private Dialog progressDialog;
        public UploadProgressDialog(Context context,String str) {
            progressDialog = new Dialog(context, R.style.upload_progress_dialog);
            progressDialog.setContentView(R.layout.dialog);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            msg.setText(str);
        }

        public void showDialog(){
            progressDialog.show();
        }

        public void cancelDialog(){
            progressDialog.dismiss();
        }

    }

