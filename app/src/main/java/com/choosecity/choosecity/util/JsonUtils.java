package com.choosecity.choosecity.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.choosecity.choosecity.bean.Messages;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**fastjson alibaba的json解析库
 * Created by syh on 2016/7/1.
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";


    public static String JsonParse(Messages messages){
        if (messages.getAppAreaSimples() instanceof String){
            return (String)messages.getAppAreaSimples();
        }else{
            return JSON.toJSONString(messages.getAppAreaSimples());
        }

    }

    /**{"flag":0,"msg":"","data":要获取的内容}
     * -1：系统错误
     0：正常
     1：提示错误，需要显示给用户
     * 获取Json的内容
     * @param
     * @return
     */
    public static Messages getMessages(String str){
        Messages messages = JSON.parseObject(str,new TypeReference<Messages>(){});
        return messages;
    }
}
