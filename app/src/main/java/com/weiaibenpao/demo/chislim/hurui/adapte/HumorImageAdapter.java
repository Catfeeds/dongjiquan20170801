package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/4/24.
 */

public class HumorImageAdapter extends RecyclerView.Adapter<HumorImageAdapter.HumorImageHolder>{
    private Context context ;
    private String[] images ;
    private imageClick imageClick ;

    public void setImageClick(HumorImageAdapter.imageClick imageClick) {
        this.imageClick = imageClick;
    }

    public interface imageClick{
        public void imageClick(int position) ;
    }

    public HumorImageAdapter(Context context , String[] images){
        this.context = context ;
        this.images = images ;
    }

    @Override
    public HumorImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.humor_image_item , parent , false);
        return new HumorImageHolder(view);
    }

    @Override
    public void onBindViewHolder(HumorImageHolder holder, final int position) {
        Picasso.with(context).load(images[position]).resize(300 , 300).centerCrop().into(holder.i_iv);
        holder.i_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageClick != null){
                    imageClick.imageClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class HumorImageHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.i_iv)
        ImageView i_iv ;

        public HumorImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
