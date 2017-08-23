package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.CommentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 留言适配
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerNotesMessageAdapter extends RecyclerView.Adapter<RecyclerNotesMessageAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList messageList;
    private ArrayList reMessage;
    Context context;
    DateFormat formatter;
    long time;
    Calendar calendar;

    /**
     * 构造器
     * @param context
     */
    public RecyclerNotesMessageAdapter(Context context, ArrayList messageList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);

        this.messageList = messageList;
    }

    /**
     * item显示类型
     * 引入布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_travelmessage, parent, false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        time = ((CommentResult.DataBean.ThemelistBean)messageList.get(position)).getCommentTime();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        Picasso.with(context).load(((CommentResult.DataBean.ThemelistBean)messageList.get(position)).getUserIcon()).into(holder.userImage);
        holder.userName.setText(((CommentResult.DataBean.ThemelistBean)messageList.get(position)).getUserNickName());
        holder.wordTime.setText(formatter.format(calendar.getTime())+"");
        holder.wordText.setText(((CommentResult.DataBean.ThemelistBean)messageList.get(position)).getComText());
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return messageList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView userImage;
        public TextView userName;
        public TextView wordText;
        public TextView wordTime;

        public ViewHolder(View view) {
            super(view);
            userImage = (ImageView) view.findViewById(R.id.userImage);
            userName = (TextView) view.findViewById(R.id.userName);
            wordText = (TextView) view.findViewById(R.id.wordText);
            wordTime = (TextView) view.findViewById(R.id.wordTime);
        }
    }
}