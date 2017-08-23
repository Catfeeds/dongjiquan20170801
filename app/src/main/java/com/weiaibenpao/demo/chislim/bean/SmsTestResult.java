package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/8/15.
 */

public class SmsTestResult {

    /**
     * returnstatus : Success
     * message : ok
     * remainpoint : -3666459
     * taskID : 9711250
     * successCounts : 1
     */

    private String returnstatus;
    private String message;
    private String remainpoint;
    private String taskID;
    private String successCounts;

    public String getReturnstatus() {
        return returnstatus;
    }

    public void setReturnstatus(String returnstatus) {
        this.returnstatus = returnstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemainpoint() {
        return remainpoint;
    }

    public void setRemainpoint(String remainpoint) {
        this.remainpoint = remainpoint;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getSuccessCounts() {
        return successCounts;
    }

    public void setSuccessCounts(String successCounts) {
        this.successCounts = successCounts;
    }
}
