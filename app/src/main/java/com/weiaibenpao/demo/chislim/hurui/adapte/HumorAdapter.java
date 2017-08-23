package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Moment;
import com.weiaibenpao.demo.chislim.hurui.bean.HumorBean;
import com.weiaibenpao.demo.chislim.hurui.httpClient.ApiClient;
import com.weiaibenpao.demo.chislim.hurui.httpClient.HHttpInterface;
import com.weiaibenpao.demo.chislim.hurui.weight.MomentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/4/17.
 */

public class HumorAdapter extends RecyclerView.Adapter {

    //两种布局形式 ，当position == 0 时，则为 ONE_ITEM 布局，position!= 0 时这位 TWO_ITEM 的布局
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;


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


    private Context mContext;
    private List<Moment> humorlistBean;
    private List<HumorBean.DataBean.MarklistBean> humor_markLists;
    public HHttpInterface apiStores = ApiClient.getModel().getService();

    public HumorAdapter(Context mContext, List<Moment> houmlistBeans, List<HumorBean.DataBean.MarklistBean> humor_markLists) {
        this.mContext = mContext;
        this.humorlistBean = houmlistBeans;
        this.humor_markLists = humor_markLists;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ONE_ITEM;
        } else {
            return TWO_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == ONE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.humor_head_view, parent, false);
            holder = new OneViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.humor_list_view, parent, false);
            holder = new TwoViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OneViewHolder) {
            OneViewHolder holder1 = (OneViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            holder1.tujian_rv.setLayoutManager(lm);
            HumorMarkAdapter adapter = new HumorMarkAdapter(mContext, humor_markLists);
            holder1.tujian_rv.setAdapter(adapter);
        } else if (holder instanceof TwoViewHolder) {
            ((TwoViewHolder) holder).momentView.setApplyType(MomentView.TYPE_RUNNING_CIRCLE_LIST);
            ((TwoViewHolder) holder).momentView.setView(humorlistBean.get(position-1));

            ((TwoViewHolder) holder).momentView.setOnMomentClickListener(new MomentView.OnMomentClickListener() {
                @Override
                public void onClickLike(Moment moment) {
                    //点赞
                    myItemClick.dianzhan(position-1);
                }

                @Override
                public void onClickMore(Moment moment) {
                   //三个点，判断是否为自己的说说
                    myItemClick.jubao_shanchu(moment.getId(),moment.getDelete());
                    //YES  说明是自己的

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return humorlistBean.size() + 1;
    }


    //头布局 , 有一个横向的recycleView
    class OneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tujian_rv)
        RecyclerView tujian_rv;

        public OneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //其他布局
    class TwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.momentView)
        MomentView momentView;


        public TwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
