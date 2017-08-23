package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/11/1.
 */

public class NewTeachResult implements Serializable{

    /**
     * error : 0
     * newTeachBean : [{"teach_id":2,"teachName":"腹肌","teach_tab1":0,"teach_tab2":2,"teach_grade":3,"teach_userhad":4586,"teach_image":"teach2.jpg","teach_text":"有人说我很简单易读懂，我想是我很真；有些人说我很复杂，我想是他读不懂我丰富的感情和思想，也或者是我没想向他袒露我的内心。因而，年轻时的我背着重重的壳生活着。","teach_word":"在我的生命中，运动就像温柔地吹拂着我，带我走出自我的暖暖春风；我最难忘、最刻骨铭心的记忆都和它相关；因为我爱运动，而运动是我对待生活的一种态度。"},{"teach_id":9,"teachName":"腹肌","teach_tab1":0,"teach_tab2":2,"teach_grade":2,"teach_userhad":0,"teach_image":"teach8.jpg","teach_text":"踏上跑道是一种选择，是一种勇气，是一种敢于拼搏，敢于释放青春活力的魄力；驰骋赛场是一种执着，是一种骄傲，是一种敢为人先，舍我其谁的枭雄魄力。","teach_word":"在我的生命中，运动就像温柔地吹拂着我，带我走出自我的暖暖春风；我最难忘、最刻骨铭心的记忆都和它相关；因为我爱运动，而运动是我对待生活的一种态度。"},{"teach_id":14,"teachName":"臂力","teach_tab1":0,"teach_tab2":2,"teach_grade":0,"teach_userhad":0,"teach_image":"teach13.jpg","teach_text":"每个人都有自己的青春，每个人都能在这场运动会中找到自己的青春角色，给自己一个难忘的运动会","teach_word":"在我的生命中，运动就像温柔地吹拂着我，带我走出自我的暖暖春风；我最难忘、最刻骨铭心的记忆都和它相关；因为我爱运动，而运动是我对待生活的一种态度。"},{"teach_id":20,"teachName":"臂力","teach_tab1":0,"teach_tab2":2,"teach_grade":3,"teach_userhad":0,"teach_image":"teach18.jpg","teach_text":"从毛毛虫蜕变成蝴蝶，是一个艰难的、痛苦的过程，但它并没有因此而放弃，而是凭着坚持不懈的精神，最终赢得了美丽；蚌壳里钻进了一粒细小的沙粒，使它不断地分秘汁液，这种过程是一种折磨，是一种煎熬，但它并没有向困难低头，而是凭着坚持不懈的精神，一层一层地包裹着这粒细小的沙，最终它孕育出了绚丽夺目的珍珠。","teach_word":"在我的生命中，运动就像温柔地吹拂着我，带我走出自我的暖暖春风；我最难忘、最刻骨铭心的记忆都和它相关；因为我爱运动，而运动是我对待生活的一种态度。"}]
     */

    private int error;
    /**
     * teach_id : 2
     * teachName : 腹肌
     * teach_tab1 : 0
     * teach_tab2 : 2
     * teach_grade : 3
     * teach_userhad : 4586
     * teach_image : teach2.jpg
     * teach_text : 有人说我很简单易读懂，我想是我很真；有些人说我很复杂，我想是他读不懂我丰富的感情和思想，也或者是我没想向他袒露我的内心。因而，年轻时的我背着重重的壳生活着。
     * teach_word : 在我的生命中，运动就像温柔地吹拂着我，带我走出自我的暖暖春风；我最难忘、最刻骨铭心的记忆都和它相关；因为我爱运动，而运动是我对待生活的一种态度。
     */

    private List<NewTeachBeanBean> newTeachBean;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<NewTeachBeanBean> getNewTeachBean() {
        return newTeachBean;
    }

    public void setNewTeachBean(List<NewTeachBeanBean> newTeachBean) {
        this.newTeachBean = newTeachBean;
    }

    public static class NewTeachBeanBean implements Serializable{
        private int teach_id;
        private String teachName;
        private int teach_tab1;
        private int teach_tab2;
        private int teach_grade;
        private int teach_userhad;
        private String teach_image;
        private String teach_text;
        private String teach_word;

        public int getTeach_id() {
            return teach_id;
        }

        public void setTeach_id(int teach_id) {
            this.teach_id = teach_id;
        }

        public String getTeachName() {
            return teachName;
        }

        public void setTeachName(String teachName) {
            this.teachName = teachName;
        }

        public int getTeach_tab1() {
            return teach_tab1;
        }

        public void setTeach_tab1(int teach_tab1) {
            this.teach_tab1 = teach_tab1;
        }

        public int getTeach_tab2() {
            return teach_tab2;
        }

        public void setTeach_tab2(int teach_tab2) {
            this.teach_tab2 = teach_tab2;
        }

        public int getTeach_grade() {
            return teach_grade;
        }

        public void setTeach_grade(int teach_grade) {
            this.teach_grade = teach_grade;
        }

        public int getTeach_userhad() {
            return teach_userhad;
        }

        public void setTeach_userhad(int teach_userhad) {
            this.teach_userhad = teach_userhad;
        }

        public String getTeach_image() {
            return teach_image;
        }

        public void setTeach_image(String teach_image) {
            this.teach_image = teach_image;
        }

        public String getTeach_text() {
            return teach_text;
        }

        public void setTeach_text(String teach_text) {
            this.teach_text = teach_text;
        }

        public String getTeach_word() {
            return teach_word;
        }

        public void setTeach_word(String teach_word) {
            this.teach_word = teach_word;
        }
    }
}
