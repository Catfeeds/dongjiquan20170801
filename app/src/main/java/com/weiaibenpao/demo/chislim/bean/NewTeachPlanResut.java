package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2016/11/1.
 */

public class NewTeachPlanResut {

    /**
     * error : 0
     * newTeachMenu : [{"teach_menu_id":1,"teach_id":1,"teach_menu_name":"减脂1","teach_menu_time":"20小时"},{"teach_menu_id":2,"teach_id":1,"teach_menu_name":"瘦腿2","teach_menu_time":"20小时"},{"teach_menu_id":3,"teach_id":1,"teach_menu_name":"减脂3","teach_menu_time":"20小时"},{"teach_menu_id":4,"teach_id":1,"teach_menu_name":"减脂4","teach_menu_time":"20小时"}]
     */

    private int error;
    /**
     * teach_menu_id : 1
     * teach_id : 1
     * teach_menu_name : 减脂1
     * teach_menu_time : 20小时
     */

    private List<NewTeachMenuBean> newTeachMenu;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<NewTeachMenuBean> getNewTeachMenu() {
        return newTeachMenu;
    }

    public void setNewTeachMenu(List<NewTeachMenuBean> newTeachMenu) {
        this.newTeachMenu = newTeachMenu;
    }

    public static class NewTeachMenuBean {
        private int teach_menu_id;
        private int teach_id;
        private String teach_menu_name;
        private String teach_menu_time;

        public int getTeach_menu_id() {
            return teach_menu_id;
        }

        public void setTeach_menu_id(int teach_menu_id) {
            this.teach_menu_id = teach_menu_id;
        }

        public int getTeach_id() {
            return teach_id;
        }

        public void setTeach_id(int teach_id) {
            this.teach_id = teach_id;
        }

        public String getTeach_menu_name() {
            return teach_menu_name;
        }

        public void setTeach_menu_name(String teach_menu_name) {
            this.teach_menu_name = teach_menu_name;
        }

        public String getTeach_menu_time() {
            return teach_menu_time;
        }

        public void setTeach_menu_time(String teach_menu_time) {
            this.teach_menu_time = teach_menu_time;
        }
    }
}
