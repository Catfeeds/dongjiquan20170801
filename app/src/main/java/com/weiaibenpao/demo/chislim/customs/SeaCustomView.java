package com.weiaibenpao.demo.chislim.customs;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotGrid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangxing on 2017/1/7.
 */

public class SeaCustomView extends DemoView {

    private String TAG = "SplineChart04View";
    private SplineChart chart = new SplineChart();
    //分类轴标签集合
    private LinkedList<String> labels = new LinkedList<String>();
    private LinkedList<SplineData> chartData = new LinkedList<SplineData>();
    Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);

    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
    private List<PointD> linePoint3;

    public SeaCustomView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public SeaCustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    public SeaCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        chartLabels();         //横轴数据
        chartDataSet();	          //折线数据
        chartDesireLines();        //线的样式
        chartRender();           //
        //綁定手势滑动事件
        this.bindTouch(this,chart);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w,h);
    }


    /**
     * 坐标系样式
     */
    private void chartRender()
    {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0] + DensityUtil.dip2px(this.getContext(), 10), ltrb[1],
                    ltrb[2]+ DensityUtil.dip2px(this.getContext(), 20), ltrb[3]);

            //标题
            chart.setTitle("海拔曲线");
            chart.getAxisTitle().setLeftTitle("海拔：米");
            chart.getAxisTitle().setLowerTitle("时间：分钟");
            chart.getAxisTitle().getLeftTitlePaint().setColor(Color.WHITE);


            //显示边框
            chart.showRoundBorder();

            //数据源
            chart.setCategories(labels);
            chart.setDataSource(chartData);
            chart.setCustomLines(mCustomLineDataset);

            //坐标系
            //数据轴最大值
            /*chart.getDataAxis().setAxisMax(120);
            //chart.getDataAxis().setAxisMin(0);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(20);*/

            //标签轴最大值
            chart.setCategoryAxisMax(6);
            //标签轴最小值
            chart.setCategoryAxisMin(0);

            //背景网格
            PlotGrid plot = chart.getPlotGrid();
            plot.hideHorizontalLines();
            plot.hideVerticalLines();
            chart.getDataAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));
            chart.getCategoryAxis().getAxisPaint().setColor(Color.rgb(127, 204, 204));

            chart.getDataAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));
            chart.getCategoryAxis().getTickMarksPaint().setColor(Color.rgb(127, 204, 204));

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(tmp).toString();
                    return (label);
                }
            });

            //不使用精确计算，忽略Java计算误差,提高性能
            chart.disableHighPrecision();

            chart.disablePanMode();
            chart.hideBorder();
            chart.getPlotLegend().hide();

            //chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.ODD_EVEN);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 折线数据源
     */
    private void chartDataSet()
    {

        //线3的数据集
        linePoint3 = new ArrayList<PointD>();
        linePoint3.add(new PointD(0d, 10d));
        linePoint3.add(new PointD(1d, 20d));
        linePoint3.add(new PointD(2d, 50d));
        linePoint3.add(new PointD(3d, 90d));
        linePoint3.add(new PointD(4d, 70d));
        linePoint3.add(new PointD(5d, 50d));
        linePoint3.add(new PointD(6d, 90d));
        SplineData dataSeries3 = new SplineData("Java", linePoint3,
                Color.rgb(54, 141, 238) );
        dataSeries3.setLabelVisible(false);
        dataSeries3.setDotStyle(XEnum.DotStyle.HIDE);


        chartData.add(dataSeries3);
    }



    public void setSeaLinePoint(List<PointD> linePoint3, double max, double equity) {
        this.linePoint3 = linePoint3;
        SplineData dataSeries2 = new SplineData("Java", linePoint3,
                Color.rgb(54, 141, 238) );
        dataSeries2.setLabelVisible(false);
        dataSeries2.setDotStyle(XEnum.DotStyle.HIDE);
        chartData.remove();
        chartData.add(dataSeries2);
        //数据轴最大值
        chart.getDataAxis().setAxisMax(max);
        //chart.getDataAxis().setAxisMin(0);
        //数据轴刻度间隔
        chart.getDataAxis().setAxisSteps(equity);
        refreshChart();
    }


    /**
     * 横轴数据源
     */
    private void chartLabels()
    {
       /* labels.add("0");
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");
        labels.add("9");
        labels.add("10");*/
    }

    /**
     * 折线样式
     */
    private void chartDesireLines()
    {
       /* CustomLineData s = new CustomLineData("GO",15d,Color.rgb(54, 141, 238),3);

        s.hideLine();
        s.getLineLabelPaint().setColor(Color.rgb(54, 141, 238));
        s.getLineLabelPaint().setTextSize(27);
        s.setLineStyle(XEnum.LineStyle.DASH);
        s.setLabelOffset(5);
        mCustomLineDataset.add(s);

        CustomLineData s2 = new CustomLineData("C/C++",18d,Color.rgb(255, 165, 132),3);

        s2.hideLine();
        s2.getLineLabelPaint().setColor(Color.rgb(255, 165, 132));
        s2.getLineLabelPaint().setTextSize(25);
        s2.setLabelOffset(5);
        mCustomLineDataset.add(s2);*/

        CustomLineData s3 = new CustomLineData("",16d, Color.rgb(54, 141, 238),3);

        s3.getLineLabelPaint().setColor(Color.rgb(54, 141, 238));
        s3.getLineLabelPaint().setTextSize(25);
        s3.hideLine();
        s3.setLabelOffset(10);
        mCustomLineDataset.add(s3);

    }


    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

}
