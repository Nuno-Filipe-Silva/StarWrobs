package com.guillaume.starwrobs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guillaume.starwrobs.R;

public class DetailInfoLayout extends LinearLayout {

    private final TextView mTitleTextView;
    private final TextView mContentTextView;

    public DetailInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.include_item_detail_card, this, true);

        mTitleTextView = (TextView) findViewById(android.R.id.text1);
        mContentTextView = (TextView) findViewById(android.R.id.text2);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DetailInfoLayout);

        final String title = a.getString(R.styleable.DetailInfoLayout_title);
        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }

        a.recycle();
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public TextView getContentTextView() {
        return mContentTextView;
    }

    public void setContentText(CharSequence text) {
        mContentTextView.setText(text);
    }
}