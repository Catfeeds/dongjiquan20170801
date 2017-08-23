/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiaibenpao.demo.chislim.adater;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 2016/7/14.
 *
 * @author Yan Zhenjie.
 */
public class ListRecyclerAdapter extends Adapter<ListRecyclerAdapter.DefineViewHolder> {

    private List<String> list;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ArrayList talkList); //设置点击事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public ListRecyclerAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(final DefineViewHolder viewHolder, final int position) {
        viewHolder.setData(list.get(position));

        if (mOnItemClickListener != null) {
            viewHolder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(viewHolder.tvTitle, position, (ArrayList) list);
                }
            });
        }

    }

    @Override
    public DefineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new DefineViewHolder(view);
    }

    static class DefineViewHolder extends ViewHolder {

        TextView tvTitle;

        public DefineViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(String data) {
            tvTitle.setText(data);
        }

    }

}
