package com.weiaibenpao.demo.chislim.customs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import org.xclcharts.chart.CircleChart;
import org.xclcharts.chart.PieData;

import java.util.LinkedList;

/**
 * Created by zhangxing on 2017/1/11.
 */

public class CircleProgressBar extends org.xclcharts.view.GraphicalView {

    private String TAG = "CircleChart02View";
    private CircleChart chart = new CircleChart();

    //设置图表数据源
    private LinkedList<PieData> mlPieData = new LinkedList<PieData>();
    private String mDataInfo = "";

    public CircleProgressBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setPercentage(0);
        chartRender();
    }

    public CircleProgressBar(Context context, AttributeSet attrs){
        super(context, attrs);
        setPercentage(0);
        chartRender();
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPercentage(0);
        chartRender();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w,h);
    }


    public void chartRender()
    {
        try {
            //设置信息
            chart.setAttributeInfo(mDataInfo);
            //数据源
            chart.setDataSource(mlPieData);

            //背景色
            chart.getBgCirclePaint().setColor(Color.rgb(46, 150, 163));
            //深色
            chart.getFillCirclePaint().setColor(Color.rgb(46, 150, 163));
            //信息颜色
            chart.getDataInfoPaint().setColor(Color.rgb(250, 250, 250));
            //显示边框
            //chart.showRoundBorder();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.toString());
        }
    }

    //百分比
    public void setPercentage(int per)
    {
        //PieData(标签，百分比，在饼图中对应的颜色)
        mlPieData.clear();
        int color = Color.rgb(0, 120, 87);
        mDataInfo = "";
		/*if(per < 40)
		{
			mDataInfo = "长 按 解 锁";
		}else if(per < 60){
			mDataInfo = "长 按 解 锁";
			color = Color.rgb(0, 205, 102);
		}else{
			mDataInfo = "长 按 解 锁";
			color = Color.rgb(255, 48, 48);
		}*/
        mlPieData.add(new PieData(Integer.toString(per)+"%",per,color));

    }

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

	/*
	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);
		return lst;
	}
	*/
}
