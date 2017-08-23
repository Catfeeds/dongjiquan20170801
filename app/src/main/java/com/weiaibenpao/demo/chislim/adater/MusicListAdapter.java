package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_MusicResult;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    private ArrayList<Li_MusicResult.DataBean.MusicTypelistBean.MusicListBean> mMusicList;
    private Context mContext;
    private int curClickPosition = -1;
    private static OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public  void registerOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void unregisterOnItemClickListener(){
        mOnItemClickListener = null;
    }

    public MusicListAdapter(Context context, ArrayList musicList) {
        this.mContext = context;
        this.mMusicList = musicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.serialNum.setText(String.valueOf(position+1));
        holder.song.setText(mMusicList.get(position).getMusicName());
        holder.singer.setText(mMusicList.get(position).getMusicSinger());
        if (position == curClickPosition) {
            if (holder.switchBtn.isSelected()) {
                setSelected(holder, false);
            } else {
                setSelected(holder, true);
            }
        } else {
            setSelected(holder, false);
        }
    }

    private void setSelected(ViewHolder holder, boolean selected) {
        holder.switchBtn.setSelected(selected);
        if (selected) {
            holder.song.setTextColor(mContext.getResources().getColor(R.color.song_playing));
            holder.singer.setTextColor(mContext.getResources().getColor(R.color.song_playing));
        } else {
            holder.song.setTextColor(mContext.getResources().getColor(R.color.song_stop));
            holder.singer.setTextColor(mContext.getResources().getColor(R.color.song_stop));
        }
    }

    public void clickItem(int position) {
        Log.e("clickItem","position is "+position);
        curClickPosition = position;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
//        Log.e("getItemCount ","count is "+count);
        return mMusicList == null ? 0 : mMusicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView switchBtn;
        private TextView song;
        private TextView singer;
        private TextView serialNum;
        private OnItemClickListener listener;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);
            switchBtn = (ImageView) view.findViewById(R.id.switch_btn);
            song = (TextView) view.findViewById(R.id.song);
            singer = (TextView) view.findViewById(R.id.singer);
            serialNum = (TextView) view.findViewById(R.id.serial_num);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition());
            }
        }
    }
}