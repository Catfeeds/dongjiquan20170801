package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/11/10.
 */

public class LunBoTuBeanResult implements Serializable{

    /**
     * error : 0
     * lunboTuBean : [{"pic_id":6,"pic_url":"http://img.easthardware.com/upload/z/z2/zjqms/picture/2016/10/24/ab0fdafe-979f-4942-baed-3a686e09d096.jpg","tab":1,"pic_title":"这是轮播图","pic_del":0},{"pic_id":5,"pic_url":"http://img.easthardware.com/upload/z/z2/zjqms/picture/2016/09/07/7b9b8aff-c4ae-4f86-ba7e-3717002a9ffe.jpg","tab":1,"pic_title":"这是轮播图","pic_del":0},{"pic_id":4,"pic_url":"http://img.easthardware.com/upload/z/z2/zjqms/picture/2016/08/30/95668bef-1a61-4bfa-81d4-c45674e8b1f3.jpg","tab":1,"pic_title":"这是轮播图","pic_del":0}]
     */

    private int error;
    /**
     * pic_id : 6
     * pic_url : http://img.easthardware.com/upload/z/z2/zjqms/picture/2016/10/24/ab0fdafe-979f-4942-baed-3a686e09d096.jpg
     * tab : 1
     * pic_title : 这是轮播图
     * pic_del : 0
     */

    private List<LunboTuBeanBean> lunboTuBean;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<LunboTuBeanBean> getLunboTuBean() {
        return lunboTuBean;
    }

    public void setLunboTuBean(List<LunboTuBeanBean> lunboTuBean) {
        this.lunboTuBean = lunboTuBean;
    }

    public static class LunboTuBeanBean implements Serializable{
        private int pic_id;
        private String pic_url;
        private int tab;
        private String pic_title;
        private int pic_del;

        public int getPic_id() {
            return pic_id;
        }

        public void setPic_id(int pic_id) {
            this.pic_id = pic_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getTab() {
            return tab;
        }

        public void setTab(int tab) {
            this.tab = tab;
        }

        public String getPic_title() {
            return pic_title;
        }

        public void setPic_title(String pic_title) {
            this.pic_title = pic_title;
        }

        public int getPic_del() {
            return pic_del;
        }

        public void setPic_del(int pic_del) {
            this.pic_del = pic_del;
        }
    }
}
