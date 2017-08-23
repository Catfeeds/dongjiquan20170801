package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.bean.FunsBean;
import com.weiaibenpao.demo.chislim.hurui.bean.HumorBean;
import com.weiaibenpao.demo.chislim.hurui.httpClient.ApiClient;
import com.weiaibenpao.demo.chislim.hurui.httpClient.HHttpInterface;
import com.weiaibenpao.demo.chislim.hurui.utils.GlideCircleTransform;
import com.weiaibenpao.demo.chislim.hurui.weight.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2017/4/22.
 */

public class HumorMarkAdapter extends RecyclerView.Adapter<HumorMarkAdapter.HumorMarkHolder>{
    private Context mContext ;
    private List<HumorBean.DataBean.MarklistBean> humor_markLists ;
    public HHttpInterface apiStores = ApiClient.getModel().getService();
    public HumorMarkAdapter(Context mContext , List<HumorBean.DataBean.MarklistBean> humor_markLists){
        this.mContext = mContext ;
        this.humor_markLists = humor_markLists ;
    }

    @Override
    public HumorMarkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.follow_people_item , parent ,false) ;
        return new HumorMarkHolder(view);
    }

    @Override
    public void onBindViewHolder(HumorMarkHolder holder, final int position) {
        Glide.with(mContext).load(humor_markLists.get(position).getUserImage())
                //第二个参数是圆角半径，第三个是模糊程度，3-5之间个人感觉比较好。
                .bitmapTransform(new BlurTransformation(mContext, 10, 2))
                .into(holder.mark_icon_iv_bg);

        Glide.with(mContext).load(humor_markLists.get(position).getUserImage())
                //第二个参数是圆角半径，第三个是模糊程度，3-5之间个人感觉比较好。
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into(holder.mark_icon_iv);


        holder.guanzhu_tv.setText(humor_markLists.get(position).getIsFans());
        holder.mark_icon_name.setText(humor_markLists.get(position).getUserName());
        holder.mark_icon_chainId.setText("【" + humor_markLists.get(position).getName() + "】");

        //关注
        holder.guanzhu_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("关注","这是人的关注");
                Call<FunsBean> call = apiStores.getFunsService(humor_markLists.get(position).getId(), UserBean.getUserBean().userId);
                call.enqueue(new Callback<FunsBean>() {
                    @Override
                    public void onResponse(Call<FunsBean> call, Response<FunsBean> response) {
                        if(response.isSuccessful()){
                            FunsBean bean = response.body() ;
                            if(bean.getCode() == 0){
                                if(humor_markLists.get(position).getIsFans().equals("请关注")){
                                    humor_markLists.get(position).setIsFans("请关注");
                                }else{
                                    humor_markLists.get(position).setIsFans("已关注");
                                }
                                notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FunsBean> call, Throwable t) {

                    }
                });
            }
        });
    }



    @Override
    public int getItemCount() {
        return humor_markLists.size();
    }

    public class HumorMarkHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.mark_icon_iv_bg)
        RoundedImageView mark_icon_iv_bg ;

        @BindView(R.id.mark_icon_iv)
        ImageView mark_icon_iv ;

        @BindView(R.id.mark_icon_name)
        TextView mark_icon_name ;

        @BindView(R.id.mark_icon_chainId)
        TextView mark_icon_chainId ;

        @BindView(R.id.guanzhu_tv)
        TextView guanzhu_tv ;

        public HumorMarkHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }

}
