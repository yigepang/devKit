package com.pang.devkit.view;

import android.content.Context;
import android.view.ViewConfiguration;

/**
 * 由hoozy于2024/3/1 10:11进行创建
 * 描述：
 */
public class FlingHelperUtil {
    Context context;
    private float DECELERATION_RATE;
    private float mFlingFriction;
    private float mPhysicalCoeff = 0.0f;

    public FlingHelperUtil(Context context) {
        this.context = context;
        mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
        mFlingFriction = ViewConfiguration.getScrollFriction();
    }

    public double getSplineDeceleration(int i) {
        return Math.log((double) (0.35f * Math.abs(i) / (mFlingFriction * mPhysicalCoeff)));
    }

    public double getSplineDecelerationByDistance(double d) {
        return ((double) DECELERATION_RATE - 1.0) * Math.log(d / (double) (mFlingFriction * mPhysicalCoeff)) / (double) DECELERATION_RATE;
    }

    public double getSplineFlingDistance(int i) {
        return Math.exp(getSplineDeceleration(i) * ((double) DECELERATION_RATE / ((double) DECELERATION_RATE - 1.0))) * (double) (mFlingFriction * mPhysicalCoeff);
    }

    public int getVelocityByDistance(double d) {
        return Math.abs((int) (Math.exp(getSplineDecelerationByDistance(d)) * (double) mFlingFriction * (double) mPhysicalCoeff / 0.3499999940395355));
    }
}
