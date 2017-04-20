package com.choosecity.choosecity;

import android.content.Context;
import com.choosecity.choosecity.util.HttpUtils;
import com.choosecity.choosecity.util.OnResultListener;
import com.loopj.android.http.RequestParams;

public class HttpShared  {
    public static String HOST = "http://www.yykaoo.com/yykaoo/";
    private static String get_areas = HOST + "app/common/get_areas.do";

    /**
     * 获取城市信息
     */
    public static void getAreas(Context context,OnResultListener listener){
        RequestParams params = new RequestParams();
        HttpUtils.post(context,get_areas,params,listener,true);
    }
}
