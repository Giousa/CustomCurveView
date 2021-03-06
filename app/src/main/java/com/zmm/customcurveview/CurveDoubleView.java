package com.zmm.customcurveview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/2/1
 * Time:下午3:44
 */

public class CurveDoubleView extends RelativeLayout {


    private ArrayList<Integer> entryList1 = new ArrayList<>();
    private ArrayList<Integer> entryList2 = new ArrayList<>();

    private LineChart mChart;


    public CurveDoubleView(Context context) {
        this(context,null);
    }

    public CurveDoubleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CurveDoubleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {

        View view = View.inflate(getContext(), R.layout.item_curve_view, this);
        mChart = view.findViewById(R.id.line_chart);
    }

    public void initChartData(ArrayList<Integer> speedList,ArrayList<Integer> heartList) {


        initList(speedList,heartList);
        int size = speedList.size();

        //初始化图表
        Description description = new Description();
        if(size > 200){
            description.setText("时间/min");//设置描述文字内容
        }else {
            description.setText("时间/s");//设置描述文字内容
        }
        description.setTextColor(this.getResources().getColor(R.color.colorAccent));//设置描述文字的颜色
        description.setTextSize(10f);
        description.setXOffset(-45f);
        description.setYOffset(3f);//描述文字的偏移

        mChart.setDescription(description);//设置图表的描述文字，会显示在图表的右下角
        mChart.setTouchEnabled(false);//设置是否可触摸
        mChart.setNoDataText("当前数据为空");//设置当 chart 为空时显示的描述文字

//        mChart.getDescription().setEnabled(false);//隐藏右下角描述


        //setChartData下面的"圈数" 的设置
        Legend legend = mChart.getLegend();
        legend.setEnabled(true);//设置Legend启用或禁用。 如果禁用， Legend 将不会被绘制。
        legend.setFormSize(18f);
        legend.setForm(Legend.LegendForm.CIRCLE);//样式，圆形
        legend.setPosition(RIGHT_OF_CHART);
        legend.setXOffset(3f);
        legend.setYOffset(3f);
        legend.setYEntrySpace(10f);


        //X轴 建议隐藏，然后自定义一组展示出的数据,如：分钟0 5 10 15 20 30等
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8);
        xAxis.setTextColor(this.getResources().getColor(R.color.chart_text));
        xAxis.setAxisMinimum(0);
        if(size <= 200){
            xAxis.setAxisMaximum(size);
        }else {
            xAxis.setAxisMaximum(200);
            xAxis.setLabelCount(size/60+1, true);
            xAxis.setValueFormatter(new MyXFormatter(size));
        }

//        xAxis.setAxisMaximum(200);

        xAxis.setAxisLineWidth(2f);
        xAxis.setDrawGridLines(false);//是否展示网格线

        //Y轴   getAxisLeft
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(8);
        yAxis.setTextColor(this.getResources().getColor(R.color.chart_text));
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(200);
        yAxis.setLabelCount(6, false);
        yAxis.setAxisLineWidth(2f);
        yAxis.setDrawGridLines(true);//是否展示网格线



        //右侧，一般不用，设为false即可
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < entryList1.size(); i++){
            entries1.add(new Entry(i,entryList1.get(i)));
        }

        ArrayList<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < entryList2.size(); i++){
            entries2.add(new Entry(i,entryList2.get(i)));
        }

        setChartData(entries1,entries2);

        //去除折线图上的小圆圈
        List<ILineDataSet> sets = mChart.getData().getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setDrawValues(false);
            set.setDrawCircles(false);
//            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set.setMode(LineDataSet.Mode.LINEAR);
        }

    }

    private void initList(ArrayList<Integer> speedList, ArrayList<Integer> heartList) {
        int size = speedList.size();

        //初始化数据
        if(size > 200){
            //取300个点的平均值
            int count = size / 200;
            int totalSpeed = 0;

            if(count == 1){
                for (int i = 0; i < size; i++) {
                    entryList1.add(speedList.get(i));
                }
            }else if(count >= 2){
                for (int i = 0; i < size; i++) {
                    if(i%count == 0 && i != 0){
                        entryList1.add(totalSpeed/(count-1));
                        totalSpeed = 0;
                    }else {
                        totalSpeed += speedList.get(i);
                    }
                }
            }

        }else {
            for (int i = 0; i < size; i++) {
                entryList1.add(speedList.get(i));
            }
        }

        int size2 = heartList.size();

        //初始化数据
        if(size2 > 200){
            //取300个点的平均值
            int count = size2 / 200;
            int totalSpeed = 0;

            if(count == 1){
                for (int i = 0; i < size2; i++) {
                    entryList2.add(heartList.get(i));
                }
            }else if(count >= 2){
                for (int i = 0; i < size2; i++) {
                    if(i%count == 0 && i != 0){
                        entryList2.add(totalSpeed/(count-1));
                        totalSpeed = 0;
                    }else {
                        totalSpeed += heartList.get(i);
                    }
                }
            }

        }else {
            for (int i = 0; i < size2; i++) {
                entryList2.add(heartList.get(i));
            }
        }
    }

    private void setChartData(ArrayList<Entry> yVals1,ArrayList<Entry> yVals2) {

        mChart.invalidate();
        mChart.notifyDataSetChanged();
        LineDataSet set1,set2;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);

            set1.setValues(yVals1);
            set2.setValues(yVals2);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "圈数");
            set1.setColor(this.getResources().getColor(R.color.colorAccent));
            set1.setLineWidth(0.5f);
            //是否绘制阴影
            set1.setDrawFilled(true);
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_right);
            set1.setFillDrawable(drawable);

            set2 = new LineDataSet(yVals2, "心率");
            set2.setColor(this.getResources().getColor(R.color.colorPrimary));
            set2.setLineWidth(0.5f);
            set2.setDrawFilled(true);
            Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.fade_left);
            set2.setFillDrawable(drawable2);

            LineData data = new LineData(set1,set2);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            mChart.setData(data);
        }
    }


}
