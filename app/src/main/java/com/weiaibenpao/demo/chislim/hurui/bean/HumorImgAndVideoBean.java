package com.weiaibenpao.demo.chislim.hurui.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/4/20.
 */

public class HumorImgAndVideoBean implements Serializable{
    public String fileName =""; //文件名
    public String path =""; // 本地路径
    public String urlPath ="" ; //网络路径
    public String compresPath="" ; //图片压缩过后的路径
    public boolean isVideo ;//判断是否为视频
    public int proess = 0 ; //上传的进度

    public HumorImgAndVideoBean() {
    }

    /**
     *
     * @param path
     * @param compresPath
     */
    public HumorImgAndVideoBean(String path, String compresPath) {
        this.fileName = fileName;
        this.path = path;
        this.urlPath = urlPath;
        this.compresPath = compresPath;
    }
}
