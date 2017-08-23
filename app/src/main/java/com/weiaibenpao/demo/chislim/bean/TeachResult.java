package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/8/15.
 */

public class TeachResult implements Serializable{


    /**
     * error : 0
     * teach : [{"teachID":1,"teachName":"健身会所","teachAddress":"http://112.74.28.179:8080/Weiaibenpao/video/lala.mp4","teachmore":0,"teachDel":0,"teachImage":"http://112.74.28.179:8080/Weiaibenpao/Image/test1.PNG"},{"teachID":2,"teachName":"美女宝宝","teachAddress":"http://112.74.28.179:8080/Weiaibenpao/video/girl.mp4","teachmore":0,"teachDel":0,"teachImage":"http://112.74.28.179:8080/Weiaibenpao/Image/test2.PNG"},{"teachID":3,"teachName":"启迈斯之一汽大众","teachAddress":"http://112.74.28.179:8080/Weiaibenpao/video/qimaisi002.mp4","teachmore":0,"teachDel":0,"teachImage":"http://112.74.28.179:8080/Weiaibenpao/Image/test3.PNG"},{"teachID":4,"teachName":"疯狂健身会所","teachAddress":"http://112.74.28.179:8080/Weiaibenpao/video/qimaisi001.mp4","teachmore":0,"teachDel":0,"teachImage":"http://112.74.28.179:8080/Weiaibenpao/Image/test4.PNG"},{"teachID":5,"teachName":"启迈斯水墨画","teachAddress":"http://112.74.28.179:8080/Weiaibenpao/video/qimaisi003.mp4","teachmore":0,"teachDel":0,"teachImage":"http://112.74.28.179:8080/Weiaibenpao/Image/test5.PNG"}]
     */

    private int error;
    /**
     * teachID : 1
     * teachName : 健身会所
     * teachAddress : http://112.74.28.179:8080/Weiaibenpao/video/lala.mp4
     * teachmore : 0
     * teachDel : 0
     * teachImage : http://112.74.28.179:8080/Weiaibenpao/Image/test1.PNG
     */

    private List<TeachBean> teach;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<TeachBean> getTeach() {
        return teach;
    }

    public void setTeach(List<TeachBean> teach) {
        this.teach = teach;
    }

    public static class TeachBean implements Serializable{
        private int teachID;
        private String teachName;
        private String teachAddress;
        private int teachmore;
        private int teachDel;
        private String teachImage;

        public int getTeachID() {
            return teachID;
        }

        public void setTeachID(int teachID) {
            this.teachID = teachID;
        }

        public String getTeachName() {
            return teachName;
        }

        public void setTeachName(String teachName) {
            this.teachName = teachName;
        }

        public String getTeachAddress() {
            return teachAddress;
        }

        public void setTeachAddress(String teachAddress) {
            this.teachAddress = teachAddress;
        }

        public int getTeachmore() {
            return teachmore;
        }

        public void setTeachmore(int teachmore) {
            this.teachmore = teachmore;
        }

        public int getTeachDel() {
            return teachDel;
        }

        public void setTeachDel(int teachDel) {
            this.teachDel = teachDel;
        }

        public String getTeachImage() {
            return teachImage;
        }

        public void setTeachImage(String teachImage) {
            this.teachImage = teachImage;
        }
    }
}
