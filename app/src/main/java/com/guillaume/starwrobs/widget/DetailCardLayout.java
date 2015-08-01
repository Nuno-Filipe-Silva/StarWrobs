package com.guillaume.starwrobs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guillaume.starwrobs.R;

public class DetailCardLayout extends FrameLayout {

    private final View mTitleLayout;
    private final TextView mTitleTextView;
    private LinearLayout mCardContent;

    public DetailCardLayout(Context context) {
        this(context, null);
    }

    public DetailCardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailCardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.include_detail_card, this, true);

        mTitleLayout = findViewById(R.id.card_header);
        mTitleTextView = (TextView) mTitleLayout.findViewById(R.id.textview_title);
        mCardContent = (LinearLayout) findViewById(R.id.card_content_holder);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DetailCardLayout);
        final String title = a.getString(R.styleable.DetailCardLayout_title);
        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }
        a.recycle();
    }

    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    public void setTitle(int titleResId) {
        setTitle(getResources().getString(titleResId));
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (mCardContent != null) {
            mCardContent.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }
}