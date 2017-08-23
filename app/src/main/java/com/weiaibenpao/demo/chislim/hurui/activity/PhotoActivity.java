package com.weiaibenpao.demo.chislim.hurui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.DeleteEventBus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PhotoActivity extends HBaseActivity {
    public static final String URL_LIST = "url";
    public static final String POSITION = "ID";//added by zjl
    public static final String TYPE="type";

    private List<ImageView> list;
    private List<Integer> deleteList;
    int index;
    private ViewPager vp;
    private MyAdapter myAdapter;
    private List<String> urlList;
    private ImageView delete_btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
       list= new ArrayList<>();
        urlList = getIntent().getStringArrayListExtra(URL_LIST);

        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < urlList.size(); i++) {
            ImageView imageView = new ImageView(this);
            Log.e("wlx33", "initData: "+urlList.get(i) );
            if (urlList.get(i)!=null)
            {
                Glide.with(mActivity).load(urlList.get(i)).into(imageView);
                list.add(imageView);
            }
        }
    }

    private void initView() {
        deleteList=new ArrayList<>();
        vp = ((ViewPager) findViewById(R.id.photo_vp));
        delete_btn = ((ImageView) findViewById(R.id.delete));
        myAdapter = new MyAdapter();
        vp.setAdapter(myAdapter);
        vp.setCurrentItem(getIntent().getIntExtra(POSITION,0));
        if (getIntent().getIntExtra(PhotoActivity.TYPE,0)==1){
            delete_btn.setVisibility(View.GONE);
        }
    }

    public void delete(View v){

        showDelete();
    }

    private void showDelete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定删除图片？");

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index=vp.getCurrentItem();
                deleteList.add(index);
                Log.e("wlx12", "index==== "+index );
                //EventBus -> post事件
                EventBus.getDefault().post(new DeleteEventBus(deleteList));
                if (urlList.size()==1){
                    finish();
                }
                //刷新adapter
                notifyD();

                dialog.cancel();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#999999"));
    }

    private void notifyD() {
        if (list.size()==1){
            finish();
        }
        list.remove(index);
        urlList.remove(index);
        vp.setAdapter(null);
        vp.setAdapter(new MyAdapter());
        if (index!=0)
        vp.setCurrentItem(index-1);
    }

    public void back(View view){
        finish();
    }
    class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

    }


    public static List<String> transformData(String[] urlArray){
//        ArrayList<String> resultList = new ArrayList<>();
//        List<String> strings = Arrays.asList(urlArray);
//        if(urlArray != null){
//            for(int i =0;i<urlArray.length;i++){
//
//                resultList.add(urlArray[i]);
//            }
//        }
        return  Arrays.asList(urlArray);
    }

}
