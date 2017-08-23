package com.weiaibenpao.demo.chislim.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

/**
 * Created by lenovo on 2017/8/21.
 */

public class Li_Dialog_View {
    TextView cancle;
    TextView commit;

    TextView copy;
    TextView delete;

    ImageView zhapian;
    ImageView kunhe;
    ImageView other;

    AlertDialog alertDialog;

    String str = "诈骗";


    /**
     * 删除说说
     * @param context
     */
    public void delHummer(Context context){

        View view = LayoutInflater.from(context).inflate(R.layout.is_delcomment, null);
        cancle = (TextView) view.findViewById(R.id.cancle);
        commit = (TextView) view.findViewById(R.id.commit);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 点击取消删除
                alertDialog.dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/8/21 点击确定删除

                myItemClick.userDelComment();
                alertDialog.dismiss();
            }
        });

        alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        alertDialog.show();
    }


    /**
     * 举报说说
     * @param context
     */
    public void reportHummer(Context context){

        View view = LayoutInflater.from(context).inflate(R.layout.is_reportcommennt, null);
        cancle = (TextView) view.findViewById(R.id.cancle);
        commit = (TextView) view.findViewById(R.id.commit);

        zhapian = (ImageView) view.findViewById(R.id.zhapian);
        kunhe = (ImageView) view.findViewById(R.id.kunhe);
        other = (ImageView) view.findViewById(R.id.other);

        zhapian.setImageResource(R.mipmap.circle_b);
        zhapian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "诈骗";

                zhapian.setImageResource(R.mipmap.circle_b);
                kunhe.setImageResource(R.mipmap.circle_d);
                other.setImageResource(R.mipmap.circle_d);
            }
        });
        kunhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "恐吓";

                zhapian.setImageResource(R.mipmap.circle_d);
                kunhe.setImageResource(R.mipmap.circle_b);
                other.setImageResource(R.mipmap.circle_d);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "其他";

                zhapian.setImageResource(R.mipmap.circle_d);
                kunhe.setImageResource(R.mipmap.circle_d);
                other.setImageResource(R.mipmap.circle_b);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 点击取消举报
                alertDialog.dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 点击确定举报
                myItemClick.userReport(str);
                alertDialog.dismiss();
            }
        });

        alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        alertDialog.show();
    }


    /**
     * 复制和删除评论
     * @param context
     */
    public void copyHummer(Context context){

        View view = LayoutInflater.from(context).inflate(R.layout.is_copy, null);
        copy = (TextView) view.findViewById(R.id.copy);
        delete = (TextView) view.findViewById(R.id.deletecommont);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 点击复制
                alertDialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 点击确定删除
                alertDialog.dismiss();
            }
        });

        alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        alertDialog.show();
    }

    public interface GetUserActionOnclickLisnter{
        void userDelComment();
        void userReport(String str);
    }
    private GetUserActionOnclickLisnter myItemClick;

    public void setMyItemClick(GetUserActionOnclickLisnter myItemClick) {
        this.myItemClick = myItemClick;
    }
}
