package com.weiaibenpao.demo.chislim.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by lenovo on 2016/11/30.
 */

public class MyTestView extends View {
    public List<Float> xlists , ylists ;

    private Paint mBitPaint ;

    public void setmBitPaint(Paint mBitPaint) {
        this.mBitPaint = mBitPaint;
    }

    public MyTestView(Context context) {
        super(context);
    }

    public void setXlists(List<Float> xlists) {
        this.xlists = xlists;
    }

    public void setYlists(List<Float> ylists) {
        this.ylists = ylists;
    }

    private boolean icon = false; //是否要显示小人

    private ImageView imageView ;

    public void setIcon(boolean icon) {
        this.icon = icon;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public MyTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initPaint();
    }

    public MyTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    /*public void initPaint(){
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setColor(Color.RED); // 画笔颜色为红色
        mBitPaint.setStrokeWidth(5); // 宽度5个像素
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        if(xlists == null || ylists == null || xlists.size() == 0 || ylists.size() == 0 || mBitPaint == null){
            return ;
        }else {
            for(int i = 1 ; i < xlists.size() ; i++) {
                if(icon == true){
                    RelativeLayout.LayoutParams lpB = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
                    lpB.topMargin =  ylists.get(i-1).intValue() - imageView.getMeasuredHeight();
                    lpB.leftMargin = xlists.get(i-1).intValue() - imageView.getMeasuredWidth();
                    imageView.setLayoutParams(lpB);
                }
                canvas.drawLine(xlists.get(i-1), ylists.get(i-1),xlists.get(i),ylists.get(i), mBitPaint);
            }
        }
        /*if(xlists == null || ylists == null || xlists.size() == 0 || ylists.size() == 0 || mBitPaint == null){
            return ;
        }else {
            for(int i = 0 ; i < xlists.size()-1; i++) {
                if(xlists.get(i) < 0){
                    i = i + 2 ;
                }else {
                    canvas.drawLine(xlists.get(i - 1), ylists.get(i - 1), xlists.get(i), ylists.get(i), mBitPaint);
                }
            }
        }*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void changeView(){
        invalidate();
    }
}
