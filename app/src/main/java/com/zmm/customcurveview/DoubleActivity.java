package com.zmm.customcurveview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/2/6
 * Time:下午5:32
 */

public class DoubleActivity extends AppCompatActivity {

    @BindView(R.id.curve_double_view)
    CurveDoubleView mCurveDoubleView;
    private ArrayList<Integer> speedList = new ArrayList<>();
    private ArrayList<Integer> heartList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double);
        ButterKnife.bind(this);

        Random random = new Random();

        //模拟30分钟
        for (int i = 0; i < 168; i++) {
            speedList.add(random.nextInt(30)+10);
        }

        for (int i = 0; i < 168; i++) {
            heartList.add(random.nextInt(100)+60);
        }


        mCurveDoubleView.initChartData(speedList,heartList);


    }
}
