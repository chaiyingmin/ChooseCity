package com.choosecity.choosecity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


public class LineView extends LinearLayout {

    private Context mContext;
    private View mLine;

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        bindViews();

    }

    private void bindViews() {
        LayoutInflater.from(mContext).inflate(R.layout.comm_line, this);
        mLine = (View) findViewById(R.id.comm_line_view);
        mLine.setBackgroundResource(R.color.color_divider);

    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        mLine.setBackgroundResource(resid);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        mLine.setBackgroundColor(color);
    }
}
