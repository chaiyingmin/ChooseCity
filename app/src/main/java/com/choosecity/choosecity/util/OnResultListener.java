package com.choosecity.choosecity.util;

/**
 * Created by syh on 2016/7/1.
 */
public interface OnResultListener {
     void onSuccess(int requestCode, String str);     //成功的方法
     void onAlert(int requestCode, String str);       //警告
     void onFailure(int requestCode, String str);    //失败的方法

}
