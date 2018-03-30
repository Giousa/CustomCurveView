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
 * Time:下午2:55
 */

public class LineActivity extends AppCompatActivity {

    @BindView(R.id.curve_view)
    CurveView mCurveView;

    private ArrayList<Integer> speedList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_line);
        ButterKnife.bind(this);


        Random random = new Random();

        //模拟30分钟
        for (int i = 0; i < 168; i++) {
            speedList.add(random.nextInt(100));
        }

        mCurveView.initChartData(speedList);
    }
}
