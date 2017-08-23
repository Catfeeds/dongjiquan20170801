package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Moment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/4/24.
 */

public class HumorPinLunAdapter extends RecyclerView.Adapter<HumorPinLunAdapter.HumorPinLunHolder> {
    private Context context ;
    private List<Moment.CommlistBean> lists ;

    private PinLunCallBack pinLunCallBack ;

    public void setPinLunCallBack(PinLunCallBack pinLunCallBack) {
        this.pinLunCallBack = pinLunCallBack;
    }

    public interface PinLunCallBack{
        public void ClickUserName(int userId);
        public void ClickCharContent(int userId , String userName);
    }

    public HumorPinLunAdapter(Context context , List<Moment.CommlistBean> lists){
        this.context = context ;
        this.lists = lists ;
    }

    @Override
    public HumorPinLunHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pinlun_item , parent , false) ;
        return new HumorPinLunHolder(view);
    }

    @Override
    public void onBindViewHolder(HumorPinLunHolder holder, final int position) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        holder.user_content_tv.setMovementMethod(LinkMovementMethod.getInstance());
        if(lists.get(position).getPreUserNickName() != null && !lists.get(position).getPreUserNickName().equals("")) {
            String text =
                    String.format("%s回复%s:%s", lists.get(position).getUserNickName(),
                            lists.get(position).getPreUserNickName(),
                            lists.get(position).getComText());
            SpannableString string = new SpannableString(text);
            string.setSpan(/*new ForegroundColorSpan(context.getResources().getColor(R.color.pin_lun) ),*/
                    new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            //Toast.makeText(context , lists.get(position).getUserNickName(),Toast.LENGTH_LONG).show();
                            if(pinLunCallBack != null){
                                pinLunCallBack.ClickUserName(lists.get(position).getUserId());
                            }
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setColor(context.getResources().getColor(R.color.pin_lun));
                            ds.setUnderlineText(false);
                        }
                    },
                    0, lists.get(position).getUserNickName().length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

            string.setSpan(new ClickableSpan() {
                               @Override
                               public void onClick(View widget) {
                                   //Toast.makeText(context , lists.get(position).getPreUserNickName(),Toast.LENGTH_LONG).show();
                                   if(pinLunCallBack != null){
                                       pinLunCallBack.ClickUserName(lists.get(position).getPreUserId());
                                   }
                               }
                               @Override
                               public void updateDrawState(TextPaint ds) {
                                   ds.setColor(context.getResources().getColor(R.color.pin_lun));
                                   ds.setUnderlineText(false);
                               }
                           }, lists.get(position).getUserNickName().length() + 2,
                    lists.get(position).getUserNickName().length() + 2 + lists.get(position).getPreUserNickName().length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            string.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //Toast.makeText(context,"点击了一下，这里是回复别人的回复",Toast.LENGTH_SHORT).show();
                    if(pinLunCallBack != null){
                        pinLunCallBack.ClickCharContent(lists.get(position).getId() , lists.get(position).getUserNickName());
                    }
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(context.getResources().getColor(R.color.h_text));
                    ds.setUnderlineText(false);
                }
            },text.length() - lists.get(position).getComText().toString().length() , text.length() ,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            builder.append(string);
        }else{
            String text =
                    String.format("%s:%s", lists.get(position).getUserNickName(),
                            lists.get(position).getComText());
            SpannableString string = new SpannableString(text);


            string.setSpan(/*new ForegroundColorSpan(context.getResources().getColor(R.color.pin_lun) ),*/
                    new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            //Toast.makeText(context , lists.get(position).getUserNickName(),Toast.LENGTH_LONG).show();
                            if(pinLunCallBack != null){
                                pinLunCallBack.ClickUserName(lists.get(position).getUserId());
                            }
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setColor(context.getResources().getColor(R.color.pin_lun));
                            ds.setUnderlineText(false);
                        }
                    },
                    0, lists.get(position).getUserNickName().length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
            string.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //Toast.makeText(context,"点击了一下，这里是首次评论别人的评论",Toast.LENGTH_SHORT).show();

                    if(pinLunCallBack != null){
                        pinLunCallBack.ClickCharContent(lists.get(position).getId(),lists.get(position).getUserNickName());
                    }
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(context.getResources().getColor(R.color.h_text));
                    ds.setUnderlineText(false);
                }
            },text.length() - lists.get(position).getComText().toString().length() , text.length() ,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(string);
        }

        holder.user_content_tv.setText(builder);
    }

    @Override
    public int getItemCount() {
       if (lists!=null){
           if(lists.size() > 10){
               return 10 ;
           }else {
               return lists.size();
           }
       }
       return 0;
    }

    public class HumorPinLunHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.user_content_tv)
        TextView user_content_tv ;

        public HumorPinLunHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView) ;
        }
    }
}
