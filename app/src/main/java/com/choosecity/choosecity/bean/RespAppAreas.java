package com.choosecity.choosecity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RespAppAreas implements Parcelable {

    public static final Creator<RespAppAreas> CREATOR = new Creator<RespAppAreas>() {
        @Override
        public RespAppAreas createFromParcel(Parcel source) {
            return new RespAppAreas(source);
        }

        @Override
        public RespAppAreas[] newArray(int size) {
            return new RespAppAreas[size];
        }
    };
    private List<ChooseCity> appAreaSimples;

    public RespAppAreas() {
    }

    protected RespAppAreas(Parcel in) {
        this.appAreaSimples = new ArrayList<ChooseCity>();
        in.readList(this.appAreaSimples, ChooseCity.class.getClassLoader());
    }

    public List<ChooseCity> getAppAreaSimples() {
        return appAreaSimples;
    }

    public void setAppAreaSimples(List<ChooseCity> appAreaSimples) {
        this.appAreaSimples = appAreaSimples;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.appAreaSimples);
    }


}
