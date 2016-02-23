package com.warmtel.gemi.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.warmtel.gemi.R;

public class TitleBarView extends RelativeLayout {
    private int leftIcon;
    private int rightIcon;

    public TitleBarView(Context context) {
        super(context);
        init(null, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public void init(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBarView, defStyleAttr, 0);
        leftIcon = a.getResourceId(R.styleable.TitleBarView_left_bar_icon, 0);
        rightIcon = a.getResourceId(R.styleable.TitleBarView_right_bar_icon, 0);
        a.recycle();

        View v = View.inflate(getContext(), R.layout.common_title_layout, this);

        if (leftIcon != 0) {
            ImageView leftIconImg = (ImageView) v.findViewById(R.id.common_left_img);
            leftIconImg.setImageResource(leftIcon);
        }
        if (rightIcon != 0) {
            ImageView rightIconImg = (ImageView) v.findViewById(R.id.common_right_img);
            rightIconImg.setImageResource(rightIcon);
        }
    }

}
