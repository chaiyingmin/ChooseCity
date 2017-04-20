package com.choosecity.choosecity.bean;

import java.io.Serializable;

/**
 * Created by syh on 2016/7/1.
 */
public class Messages implements Serializable {
    private int states;
    private String msg;
    private Object appAreaSimples;

    public Messages(int states, String msg, Object appAreaSimples) {
        this.states = states;
        this.msg = msg;
        this.appAreaSimples = appAreaSimples;
    }

    public Messages() {
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getAppAreaSimples() {
        return appAreaSimples;
    }

    public void setAppAreaSimples(Object appAreaSimples) {
        this.appAreaSimples = appAreaSimples;
    }
}
