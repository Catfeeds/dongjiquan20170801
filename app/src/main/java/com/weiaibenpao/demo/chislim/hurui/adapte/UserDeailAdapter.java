package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Moment;
import com.weiaibenpao.demo.chislim.hurui.weight.MomentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class UserDeailAdapter extends RecyclerView.Adapter<UserDeailAdapter.ViewHolder> {

    private Context context;
    List<Moment> humorlist;
    private View headViewId;
    private final int HEAD = 0;
    private final int NORMAL = 1;
    private boolean isHaveHead=false;

    public UserDeailAdapter(Context context) {
        this.context = context;
        humorlist=new ArrayList<>();
    }

    public void refresh(List<Moment> humorlist ){
        this.humorlist.addAll(humorlist);



        notifyDataSetChanged();
    }
    //添加头部
    public void addHeadViewId( View headViewId) {
        isHaveHead = true;
        this.headViewId = headViewId;
        notifyDataSetChanged();
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            ViewHolder holder=null;
        switch (i) {
            case HEAD:
               holder= new ViewHolder(headViewId);

                break;
            case NORMAL:
                View view = LayoutInflater.from(context).inflate(R.layout.humor_list_view, viewGroup, false);
                Log.e("wlx0202", "onBindViewHolder: "+ this.humorlist.size() );
                holder= new ViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, final int i) {
        if (isHaveHead&& isHead(i)){

        }else {
            int mPosition = isHaveHead ? i - 1 : i;
            viewholder.momentView.setView(humorlist.get(mPosition));

            viewholder.momentView.setOnMomentClickListener(new MomentView.OnMomentClickListener() {
                @Override
                public void onClickLike(Moment moment) {
                    //点赞
                    myItemClick.dianzhan(i - 1);
                }

                @Override
                public void onClickMore(Moment moment) {
                    myItemClick.jubao_shanchu(moment.getId(),moment.getDelete());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (isHaveHead)
        return humorlist.size()+1;

        return humorlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHaveHead)
            return isHead(position) ? HEAD : NORMAL;

        return NORMAL;

    }
    //头部的位置
    private boolean isHead(int position) {
        return position == 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public MomentView momentView;

        public ViewHolder(View itemView) {
            super(itemView);
//         if (isHaveHead)
             Log.e("lyf", "ViewHolder:==== "+isHaveHead );
               momentView = (MomentView) itemView.findViewById(R.id.momentView);
        }
    }

    private MyItemClick myItemClick;

    public void setMyItemClick(MyItemClick myItemClick) {
        this.myItemClick = myItemClick;
    }

    //adapter的事件监听
    public interface MyItemClick {
        public void dianzhan(int postion);

        public void jubao_shanchu(int hummer_id,String is_us);

        public void clickImage(String[] imageUrl, int imgposition);

        public void clickIcon(int userId);

        public void addpinLun(int id, int position);

        public void replaypinLun(int position, int preUserid, String preUserName);
    }

}
