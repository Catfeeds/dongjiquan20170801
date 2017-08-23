package com.weiaibenpao.demo.chislim.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2016/11/25.
 */

public class SportTypeBean implements Parcelable {
    public String typeImage;
    public String typeIntro;
    public String typeTime;

    public SportTypeBean(String typeImage,String typeIntro, String typeTime) {
        this.typeImage = typeImage;
        this.typeTime = typeTime;
        this.typeIntro = typeIntro;
    }

    public SportTypeBean() {
    }

    protected SportTypeBean(Parcel in) {
        typeImage = in.readString();
        typeIntro = in.readString();
        typeTime = in.readString();
    }

    public static final Creator<SportTypeBean> CREATOR = new Creator<SportTypeBean>() {
        @Override
        public SportTypeBean createFromParcel(Parcel in) {
            return new SportTypeBean(in);
        }

        @Override
        public SportTypeBean[] newArray(int size) {
            return new SportTypeBean[size];
        }
    };

    public String getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(String typeImage) {
        this.typeImage = typeImage;
    }

    public String getTypeIntro() {
        return typeIntro;
    }

    public void setTypeIntro(String typeIntro) {
        this.typeIntro = typeIntro;
    }

    public String getTypeTime() {
        return typeTime;
    }

    public void setTypeTime(String typeTime) {
        this.typeTime = typeTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(typeImage);
        parcel.writeString(typeIntro);
        parcel.writeString(typeTime);
    }
}
