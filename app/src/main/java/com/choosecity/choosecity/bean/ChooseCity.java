package com.choosecity.choosecity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 为前台生产json字符串
 *
 * @author zqs
 */
public class ChooseCity implements Parcelable {

    public static final Parcelable.Creator<ChooseCity> CREATOR = new Parcelable.Creator<ChooseCity>() {
        @Override
        public ChooseCity createFromParcel(Parcel source) {
            return new ChooseCity(source);
        }

        @Override
        public ChooseCity[] newArray(int size) {
            return new ChooseCity[size];
        }
    };
    /**
     *
     */
    private static final long serialVersionUID = -3034332843182372134L;
    /**
     * id
     */
    private Long id; // 二级 （市级）
    /**
     * 名称
     */
    private String name;
    /**
     * 下级地区
     */
    private List<ChooseCity> child = new ArrayList<ChooseCity>();

    public ChooseCity() {
    }

    protected ChooseCity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.child = new ArrayList<ChooseCity>();
        in.readList(this.child, ChooseCity.class.getClassLoader());
    }

    public Long getId() {
        return id;
    }

    public String getIdString() {
        if (null == this.id) {
            return "";
        }
        return id.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChooseCity> getChild() {
        return child;
    }

    public void setChild(List<ChooseCity> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ChooseCity{" + "id=" + id + ", name='" + name + '\'' + ", child=" + child + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeList(this.child);
    }

}
