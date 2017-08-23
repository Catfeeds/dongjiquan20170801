package com.weiaibenpao.demo.chislim.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/11/17.
 */

public class MainBeanResult implements Serializable {


    /**
     * error : 0
     * talk : [{"aid":1,"aTitle":"2017-不丹国际马拉松","aImage":"http://ofplk6att.bkt.clouddn.com/budan_run.png","aText":"白云深处的小国度， 喜马拉雅山脉下的天堂，\r\n最接近幸福的地方， 这里是最快乐的穷国， 也是世间最神秘的人间净土，\r\n不丹可以像任何一个国家， 但没有一个国家可以像不丹。\r\n去不丹跑一场马拉松吧，飞越过喜马拉雅山，\r\n来到这快不食人间烟火的灵魂之地，\r\n用一场酣畅淋漓的奔跑， 重新思考生活的意义，\r\n或许你会在这里找到遗失很久的幸福","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":1,"aZhanwei":"54545"},{"aid":2,"aTitle":"中国大学生马拉松联赛","aImage":"http://ofplk6att.bkt.clouddn.com/collage_run.jpg","aText":"中国大学生马拉松联赛是由康湃思体育首次推出的原创校园体育赛事，由中国大学生体育协会（大体协）主办，大体协田径分会协办，并委托虎扑体育负责商业运营和市场推广工作。\r\n与传统的校园长跑活动不同，本项赛事力图将专业马拉松服务带入校园，在安全保障、报名计时、赛事补给和完赛纪念方面为校园跑步爱好者提供专业的赛事服务","aPeople":22214,"aStartTime":"2015年03月01日","aStopTime":"2016年03月07日","aAddress":"全国各大高校","aTab":1,"aZhanwei":"54545"},{"aid":3,"aTitle":"贵人鸟全民冬跑日","aImage":"http://ofplk6att.bkt.clouddn.com/guirenniao.jpg","aText":"123全民冬跑日暨Halorun年度盛典以\u201c运动、健康、娱乐\u201d为主题，分为10km竞速跑、5km任务跑两个项目，赛事规模达6000人，届时将会有明星互动、嘉年华游戏体验、冬季运动文化展示等环节，融竞技魅力与全民健身于一体，发展全民健身运动，倡导健康生活方式，让越来越多的人感受到冬季运动的魅力。","aPeople":1214,"aStartTime":"2016年12月03日","aStopTime":"2016年12月03日","aAddress":"辽宁-沈阳","aTab":1,"aZhanwei":"54545"}]
     * activity : [{"aid":4,"aTitle":"#我和品牌的故事#","aImage":"http://ofplk6att.bkt.clouddn.com/logo.png","aText":"您与您欣欣已久的品牌之间有何爱恨纠缠","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":0,"aZhanwei":"54545"},{"aid":5,"aTitle":"#背包旅行#","aImage":"http://ofplk6att.bkt.clouddn.com/travel_with_bag.png","aText":"心灵和身体总要有一个在路上，你的呢？","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":0,"aZhanwei":"54545"},{"aid":6,"aTitle":"#跑步故事#","aImage":"http://ofplk6att.bkt.clouddn.com/story_of_run.png","aText":"村上村树在他的一书《我在跑步的时候都在想什么》中，完美诠释了他与跑步之间的故事，那么你的故事又是怎样的？","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":0,"aZhanwei":"54545"},{"aid":7,"aTitle":"#时尚#","aImage":"http://ofplk6att.bkt.clouddn.com/feshion.png","aText":"所谓时尚，是时与尚的结合体。所谓时，乃时间，时下，即在一个时间段内；尚，则有崇尚，高尚，高品位，领先。时尚其实在这个时代而言的，不仅仅是为了修饰，甚至演化成了一种追求真善美的意识。","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":0,"aZhanwei":"54545"},{"aid":8,"aTitle":"#一本书,一句话#","aImage":"http://ofplk6att.bkt.clouddn.com/readbook.png","aText":"一本书，一段人生和历史的缩影，一段跨越时间地点的旅行， 一句话，揣测许久，方得之真谛","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":0,"aZhanwei":"54545"},{"aid":9,"aTitle":"#一张照片,一段故事#","aImage":"http://ofplk6att.bkt.clouddn.com/photo_story.png","aText":"你的手机里有没有那么一张照片，即使更换过许多手机，依然保存到现在，即使删除过很多照片，它依然存在于你的相册中？","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":0,"aZhanwei":"54545"},{"aid":10,"aTitle":"#家乡#","aImage":"http://ofplk6att.bkt.clouddn.com/hometown.png","aText":"家乡，离开这个地方后，才知道这里是家，非常想念，想家，家想，谓之家乡。","aPeople":2534,"aStartTime":"2017年03月01日","aStopTime":"2017年03月07日","aAddress":"不丹","aTab":0,"aZhanwei":"54545"}]
     */

    private int error;
    /**
     * aid : 1
     * aTitle : 2017-不丹国际马拉松
     * aImage : http://ofplk6att.bkt.clouddn.com/budan_run.png
     * aText : 白云深处的小国度， 喜马拉雅山脉下的天堂，
     最接近幸福的地方， 这里是最快乐的穷国， 也是世间最神秘的人间净土，
     不丹可以像任何一个国家， 但没有一个国家可以像不丹。
     去不丹跑一场马拉松吧，飞越过喜马拉雅山，
     来到这快不食人间烟火的灵魂之地，
     用一场酣畅淋漓的奔跑， 重新思考生活的意义，
     或许你会在这里找到遗失很久的幸福
     * aPeople : 2534
     * aStartTime : 2017年03月01日
     * aStopTime : 2017年03月07日
     * aAddress : 不丹
     * aTab : 1
     * aZhanwei : 54545
     */

    private List<TalkBean> talk;
    /**
     * aid : 4
     * aTitle : #我和品牌的故事#
     * aImage : http://ofplk6att.bkt.clouddn.com/logo.png
     * aText : 您与您欣欣已久的品牌之间有何爱恨纠缠
     * aPeople : 2534
     * aStartTime : 2017年03月01日
     * aStopTime : 2017年03月07日
     * aAddress : 不丹
     * aTab : 0
     * aZhanwei : 54545
     */

    private List<ActivityBean> activity;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<TalkBean> getTalk() {
        return talk;
    }

    public void setTalk(List<TalkBean> talk) {
        this.talk = talk;
    }

    public List<ActivityBean> getActivity() {
        return activity;
    }

    public void setActivity(List<ActivityBean> activity) {
        this.activity = activity;
    }

    public static class TalkBean implements Serializable,Parcelable{
        private int aid;
        private String aTitle;
        private String aImage;
        private String aText;
        private int aPeople;
        private String aStartTime;
        private String aStopTime;
        private String aAddress;
        private int aTab;
        private String aZhanwei;

        protected TalkBean(Parcel in) {
            aid = in.readInt();
            aTitle = in.readString();
            aImage = in.readString();
            aText = in.readString();
            aPeople = in.readInt();
            aStartTime = in.readString();
            aStopTime = in.readString();
            aAddress = in.readString();
            aTab = in.readInt();
            aZhanwei = in.readString();
        }

        public static final Creator<TalkBean> CREATOR = new Creator<TalkBean>() {
            @Override
            public TalkBean createFromParcel(Parcel in) {
                return new TalkBean(in);
            }

            @Override
            public TalkBean[] newArray(int size) {
                return new TalkBean[size];
            }
        };

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getATitle() {
            return aTitle;
        }

        public void setATitle(String aTitle) {
            this.aTitle = aTitle;
        }

        public String getAImage() {
            return aImage;
        }

        public void setAImage(String aImage) {
            this.aImage = aImage;
        }

        public String getAText() {
            return aText;
        }

        public void setAText(String aText) {
            this.aText = aText;
        }

        public int getAPeople() {
            return aPeople;
        }

        public void setAPeople(int aPeople) {
            this.aPeople = aPeople;
        }

        public String getAStartTime() {
            return aStartTime;
        }

        public void setAStartTime(String aStartTime) {
            this.aStartTime = aStartTime;
        }

        public String getAStopTime() {
            return aStopTime;
        }

        public void setAStopTime(String aStopTime) {
            this.aStopTime = aStopTime;
        }

        public String getAAddress() {
            return aAddress;
        }

        public void setAAddress(String aAddress) {
            this.aAddress = aAddress;
        }

        public int getATab() {
            return aTab;
        }

        public void setATab(int aTab) {
            this.aTab = aTab;
        }

        public String getAZhanwei() {
            return aZhanwei;
        }

        public void setAZhanwei(String aZhanwei) {
            this.aZhanwei = aZhanwei;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(aid);
            parcel.writeString(aTitle);
            parcel.writeString(aImage);
            parcel.writeString(aText);
            parcel.writeInt(aPeople);
            parcel.writeString(aStartTime);
            parcel.writeString(aStopTime);
            parcel.writeString(aAddress);
            parcel.writeInt(aTab);
            parcel.writeString(aZhanwei);
        }
    }

    public static class ActivityBean implements Serializable{
        private int aid;
        private String aTitle;
        private String aImage;
        private String aText;
        private int aPeople;
        private String aStartTime;
        private String aStopTime;
        private String aAddress;
        private int aTab;
        private String aZhanwei;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getATitle() {
            return aTitle;
        }

        public void setATitle(String aTitle) {
            this.aTitle = aTitle;
        }

        public String getAImage() {
            return aImage;
        }

        public void setAImage(String aImage) {
            this.aImage = aImage;
        }

        public String getAText() {
            return aText;
        }

        public void setAText(String aText) {
            this.aText = aText;
        }

        public int getAPeople() {
            return aPeople;
        }

        public void setAPeople(int aPeople) {
            this.aPeople = aPeople;
        }

        public String getAStartTime() {
            return aStartTime;
        }

        public void setAStartTime(String aStartTime) {
            this.aStartTime = aStartTime;
        }

        public String getAStopTime() {
            return aStopTime;
        }

        public void setAStopTime(String aStopTime) {
            this.aStopTime = aStopTime;
        }

        public String getAAddress() {
            return aAddress;
        }

        public void setAAddress(String aAddress) {
            this.aAddress = aAddress;
        }

        public int getATab() {
            return aTab;
        }

        public void setATab(int aTab) {
            this.aTab = aTab;
        }

        public String getAZhanwei() {
            return aZhanwei;
        }

        public void setAZhanwei(String aZhanwei) {
            this.aZhanwei = aZhanwei;
        }
    }
}
