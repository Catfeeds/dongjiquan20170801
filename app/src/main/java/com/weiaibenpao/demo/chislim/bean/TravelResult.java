package com.weiaibenpao.demo.chislim.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/10/26.
 */

public class TravelResult implements Serializable{


    /**
     * error : 0
     * travel : [{"t_id":33,"t_name":"归元寺","t_image":"guiyuan.png","t_title":"归元寺","t_text":"归元禅寺位于湖北省武汉市武汉市汉阳区归元寺路，由白光法师于清顺治15年（公元1658年）兴建。占地10公顷，有殿舍200余间，各类佛教经典7000余卷。[1] 归元禅寺属于佛教禅宗五家七宗之一的曹洞宗，故称归元禅寺。归元禅寺又被称为\u201c汉西一境\u201d，是因其古树参天，花木繁茂的人文境致而得。同时还是武汉市佛教协会的所在地。它与宝通寺、溪莲寺、正觉寺合称为武汉的四大丛林。[2]","t_address":"location","t_time":"2016.12.05.16.11.12","t_del":0,"t_hot":0},{"t_id":32,"t_name":"木兰山","t_image":"mulan.png","t_title":"木兰山","t_text":"木兰山风景区位于武汉市黄陂区前川街道城北，距黄陂城区、武汉城区分别为30公里和70公里，处于亚热带季风气候，其主峰祈嗣顶高度为海拔582.1米，是大别山南麓高峰之一。其自然区域东濒木兰天池，西临滠水河，北接长塔公路，南抵研梳公路，占地面积为1550万平方米。山势呈南北走向，南低北高。林地面积373公顷，林区面积达2000公顷，森林覆盖率达95%。整个风景区分为古寨区、石景区、花苑区、山庄区四区。","t_address":"location","t_time":"2016.12.05.16.11.12","t_del":0,"t_hot":0},{"t_id":30,"t_name":"北海","t_image":"beihai.png","t_title":"北海","t_text":"\u201c朝沧梧而夕北海\u201d,北海的名字很早就有了,北海地名的形成，定位于康熙元年（1662）年，清政府设\u201c北海镇标\u201d作为\u201c北海\u201d地名的称谓。由于此种说法转述频繁、引证广泛，因此成为一个定例。据文史资料记载，\u201c北海\u201d一词可追溯至宋朝甚至是魏晋南北朝时期，这个时期，\u201c北海\u201d更多的是被外国人所称呼。1965年6月，北海由广东省划归广西壮族自治区，北海之名也一直沿用至今。","t_address":"location","t_time":"2016.12.05.16.11.12","t_del":0,"t_hot":0}]
     */

    private int error;
    /**
     * t_id : 33
     * t_name : 归元寺
     * t_image : guiyuan.png
     * t_title : 归元寺
     * t_text : 归元禅寺位于湖北省武汉市武汉市汉阳区归元寺路，由白光法师于清顺治15年（公元1658年）兴建。占地10公顷，有殿舍200余间，各类佛教经典7000余卷。[1] 归元禅寺属于佛教禅宗五家七宗之一的曹洞宗，故称归元禅寺。归元禅寺又被称为“汉西一境”，是因其古树参天，花木繁茂的人文境致而得。同时还是武汉市佛教协会的所在地。它与宝通寺、溪莲寺、正觉寺合称为武汉的四大丛林。[2]
     * t_address : location
     * t_time : 2016.12.05.16.11.12
     * t_del : 0
     * t_hot : 0
     */

    private List<TravelBean> travel;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<TravelBean> getTravel() {
        return travel;
    }

    public void setTravel(List<TravelBean> travel) {
        this.travel = travel;
    }

    public static class TravelBean implements Serializable,Parcelable{
        private int t_id;
        private String t_name;
        private String t_image;
        private String t_title;
        private String t_text;
        private String t_address;
        private String t_time;
        private int t_del;
        private int t_hot;

        protected TravelBean(Parcel in) {
            t_id = in.readInt();
            t_name = in.readString();
            t_image = in.readString();
            t_title = in.readString();
            t_text = in.readString();
            t_address = in.readString();
            t_time = in.readString();
            t_del = in.readInt();
            t_hot = in.readInt();
        }

        public static final Creator<TravelBean> CREATOR = new Creator<TravelBean>() {
            @Override
            public TravelBean createFromParcel(Parcel in) {
                return new TravelBean(in);
            }

            @Override
            public TravelBean[] newArray(int size) {
                return new TravelBean[size];
            }
        };

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public String getT_name() {
            return t_name;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public String getT_image() {
            return t_image;
        }

        public void setT_image(String t_image) {
            this.t_image = t_image;
        }

        public String getT_title() {
            return t_title;
        }

        public void setT_title(String t_title) {
            this.t_title = t_title;
        }

        public String getT_text() {
            return t_text;
        }

        public void setT_text(String t_text) {
            this.t_text = t_text;
        }

        public String getT_address() {
            return t_address;
        }

        public void setT_address(String t_address) {
            this.t_address = t_address;
        }

        public String getT_time() {
            return t_time;
        }

        public void setT_time(String t_time) {
            this.t_time = t_time;
        }

        public int getT_del() {
            return t_del;
        }

        public void setT_del(int t_del) {
            this.t_del = t_del;
        }

        public int getT_hot() {
            return t_hot;
        }

        public void setT_hot(int t_hot) {
            this.t_hot = t_hot;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(t_id);
            parcel.writeString(t_name);
            parcel.writeString(t_image);
            parcel.writeString(t_title);
            parcel.writeString(t_text);
            parcel.writeString(t_address);
            parcel.writeString(t_time);
            parcel.writeInt(t_del);
            parcel.writeInt(t_hot);
        }
    }
}
