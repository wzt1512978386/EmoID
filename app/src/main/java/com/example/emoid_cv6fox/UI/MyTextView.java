package com.example.emoid_cv6fox.UI;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: wzt
 * @date: 2020/11/28
 */
public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {
    public MyTextView(@NonNull Context context) {
        super(context);
        init();
    }
    public MyTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        //Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/font_ty.TTF");
        //setTypeface(typeFace);
        //setTypeface(Typeface.DEFAULT_BOLD);
    }
}
