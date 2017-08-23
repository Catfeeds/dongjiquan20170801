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
import com.weiaibenpao.demo.chislim.bean.ChatMessage;
import com.weiaibenpao.demo.chislim.util.CircleTransform;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by zhangxing on 2017/3/10.
 */

public class ChatMessageAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<ChatMessage> data;
    LayoutInflater inflater;

    public ChatMessageAdapter2(Context context, List<ChatMessage> list){
        mContext = context;
        data = list;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            //机器人
            View robotview = inflater.inflate(R.layout.from_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(robotview);
            return viewHolder;
        } else if (viewType == 1) {
            //用户
            View peopleview = inflater.inflate(R.layout.to_item, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            MyViewHolder myViewHolder = new MyViewHolder(peopleview);
            return myViewHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = data.get(position);
       if(holder instanceof ViewHolder ){
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           ((ViewHolder)holder).robot_date.setText(df.format(chatMessage.getDate()));
           ((ViewHolder)holder).robot_msg.setText(chatMessage.getMsg());

           Picasso.with(mContext)
                   .load(R.mipmap.service)
                   .resize(48,48)
                   .transform(new CircleTransform())
                   .into(((ViewHolder)holder).robot_iv);

       }else if(holder instanceof MyViewHolder ){
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           ((MyViewHolder)holder).people_date.setText(df.format(chatMessage.getDate()));
           ((MyViewHolder)holder).people_msg.setText(chatMessage.getMsg());
           ((MyViewHolder)holder).people_nicheng.setText(chatMessage.getName());

           Picasso.with(mContext)
                   .load(chatMessage.getImgStr())
                   .resize(64,64)
                   .centerCrop()
                   .error(R.mipmap.true_people)
                   .placeholder(R.mipmap.true_people)
                   .transform(new CircleTransform())
                   .into(((MyViewHolder)holder).people_iv);
       }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = data.get(position);
        if(chatMessage.getType()== ChatMessage.Type.INCOMING){
            return 0;
        }
        return 1;
    }

    /**
     * 机器人
     */
    private final  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView robot_iv;
        TextView robot_date;
        TextView robot_msg;

        public ViewHolder(View itemView) {
            super(itemView);
            robot_iv = (ImageView) itemView.findViewById(R.id.robot_img);
            robot_date = (TextView) itemView.findViewById(R.id.from_msg_date);
            robot_msg = (TextView) itemView.findViewById(R.id.from_msg);
        }
    }

    /**
     * 用户
     */
    private final  class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView people_iv;
        TextView  people_nicheng;
        TextView  people_date;
        TextView  people_msg;

        public MyViewHolder(View itemView) {
            super(itemView);
            people_iv = (ImageView) itemView.findViewById(R.id.people_img);
            people_nicheng = (TextView) itemView.findViewById(R.id.nichen);
            people_date = (TextView) itemView.findViewById(R.id.to_msg_date);
            people_msg = (TextView) itemView.findViewById(R.id.to_msg);

        }
    }
}
