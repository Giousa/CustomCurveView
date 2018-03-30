package com.zmm.customcurveview;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/2/6
 * Time:上午9:53
 */

public class MyXFormatter implements IAxisValueFormatter {

    private int count;

    public MyXFormatter(int count) {

        this.count = count;
    }

    /**
     * @param value
     * @param axis
     * @return
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        System.out.println("value = "+value+",count = "+count+",label = "+count/60);
        System.out.println("X轴:"+(int)(value*count/12000));
        return (int)(Math.floor(value*count/12000))+"";

    }
}
