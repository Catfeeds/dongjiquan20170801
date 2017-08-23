package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by lenovo on 2017/2/11.
 */

public class MonthRemberAdapter extends RecyclerView.Adapter<MonthRemberAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    Context context;

    HashMap<String,String> hashMap;
    ArrayList arrayList;

    /**
     * 构造器
     * @param context
     */
    public MonthRemberAdapter(Context context, HashMap<String,String> hashMap) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.hashMap = hashMap;

        arrayList = new ArrayList();
        //  将map中的key存入set集合，通过迭代器取出所有的key，再获取每一个键对应的值
        Set keySet = hashMap.keySet(); // key的set集合
        Iterator it = keySet.iterator();
        while(it.hasNext()){
            String k = (String) it.next(); // key
            String v = hashMap.get(k);  //value

            arrayList.add(k + "月份累计跑步" + v + "公里");
        }
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
        View view = mInflater.inflate(R.layout.item_monthrember, parent, false);

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

        holder.myText.setText(arrayList.get(position) + "");
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return hashMap.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myText;

        public ViewHolder(View view) {
            super(view);
            myText = (TextView) view.findViewById(R.id.myText);
        }
    }
}