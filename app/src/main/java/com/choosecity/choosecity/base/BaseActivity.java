package com.choosecity.choosecity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


/**
 * activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final static String TAG = BaseActivity.class.getSimpleName();


    /**
     * 失败页面的点击
     */
    public void onReload() {
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void invalidateMyOptionsMenu() {
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public BaseActivity getContext() {
        return BaseActivity.this;
    }

}
