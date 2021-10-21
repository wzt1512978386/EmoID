package com.example.emoid_cv6fox.UI.Recommend;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.App.MyApp;

/**
 * @author: wzt
 * @date: 2020/11/29
 */
public class FrameRecommend extends LinearLayout {
    //外部调用
    public FramePie framePie;
    public FrameRecommend(Context context) {
        super(context);
        MyApp.frameRecommend=this;
    }
    public FrameRecommend(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        MyApp.frameRecommend=this;
    }
}
