package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.holder.DisHolder;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/13.
 */

public class DisListAdapter extends BaseAdapter {
    private Context myContext;
    private ArrayList disBAList;

    public DisListAdapter() {
    }

    public DisListAdapter(Context myContext, ArrayList teachBAList) {
        this.myContext = myContext;
        this.disBAList = teachBAList;
    }

    @Override
    public int getCount() {
        return disBAList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        DisHolder disHolder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(myContext);
            convertView = mInflater.inflate(R.layout.disitem, null);

            disHolder = new DisHolder();

            disHolder.disText =  (TextView) convertView.findViewById(R.id.disText);

            convertView.setTag(disHolder);
        }else{
            disHolder = (DisHolder) convertView.getTag();
        }

        disHolder.disText.setText(disBAList.get(i).toString() + "千米");


        return convertView;
    }
}
