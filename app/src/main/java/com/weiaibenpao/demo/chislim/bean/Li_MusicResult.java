package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/4/14.
 */

public class Li_MusicResult implements Serializable {


    /**
     * code : 0
     * msg : 成功
     * data : {"musicTypelist":[{"extra":{"totalSize":2,"size":10,"totalPage":1,"page":0},"musicType":"伤感","musicList":[{"musicSinger":"张碧晨","musicUrl":"http://ofplk6att.bkt.clouddn.com/diyichangxue.mp3","id":6,"musicName":"歌曲名6","musicType":"伤感","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon1.png"},{"musicSinger":"周杰伦","musicUrl":"http://ofplk6att.bkt.clouddn.com/xihaiqingge.mp3","id":1,"musicName":"歌曲名1","musicType":"伤感","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon1.png"}],"musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon1.png"},{"extra":{"totalSize":2,"size":10,"totalPage":1,"page":0},"musicType":"乡村","musicList":[{"musicSinger":"小刚","musicUrl":"http://ofplk6att.bkt.clouddn.com/xihaiqingge.mp3","id":7,"musicName":"歌曲名8","musicType":"乡村","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon2.png"},{"musicSinger":"刀郎","musicUrl":"http://ofplk6att.bkt.clouddn.com/xihaiqingge.mp3","id":2,"musicName":"歌曲名5","musicType":"乡村","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon2.png"}],"musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon2.png"},{"extra":{"totalSize":2,"size":10,"totalPage":1,"page":0},"musicType":"流行","musicList":[{"musicSinger":"王非","musicUrl":"http://ofplk6att.bkt.clouddn.com/diyichangxue.mp3","id":5,"musicName":"歌曲名2","musicType":"流行","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon3.png"},{"musicSinger":"那英","musicUrl":"http://ofplk6att.bkt.clouddn.com/diyichangxue.mp3","id":3,"musicName":"歌曲名4","musicType":"流行","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon3.png"}],"musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon3.png"},{"extra":{"totalSize":2,"size":10,"totalPage":1,"page":0},"musicType":"沙滩","musicList":[{"musicSinger":"小宝","musicUrl":"http://ofplk6att.bkt.clouddn.com/xihaiqingge.mp3","id":8,"musicName":"歌曲名7","musicType":"沙滩","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon4.png"},{"musicSinger":"刘德华","musicUrl":"http://ofplk6att.bkt.clouddn.com/xihaiqingge.mp3","id":4,"musicName":"歌曲名3","musicType":"沙滩","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon4.png"}],"musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon4.png"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * extra : {"totalSize":2,"size":10,"totalPage":1,"page":0}
         * musicType : 伤感
         * musicList : [{"musicSinger":"张碧晨","musicUrl":"http://ofplk6att.bkt.clouddn.com/diyichangxue.mp3","id":6,"musicName":"歌曲名6","musicType":"伤感","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon1.png"},{"musicSinger":"周杰伦","musicUrl":"http://ofplk6att.bkt.clouddn.com/xihaiqingge.mp3","id":1,"musicName":"歌曲名1","musicType":"伤感","musicTypeImg":"http://ofplk6att.bkt.clouddn.com/music_icon1.png"}]
         * musicTypeImg : http://ofplk6att.bkt.clouddn.com/music_icon1.png
         */

        private List<MusicTypelistBean> musicTypelist;

        public List<MusicTypelistBean> getMusicTypelist() {
            return musicTypelist;
        }

        public void setMusicTypelist(List<MusicTypelistBean> musicTypelist) {
            this.musicTypelist = musicTypelist;
        }

        public static class MusicTypelistBean implements Serializable{
            /**
             * totalSize : 2
             * size : 10
             * totalPage : 1
             * page : 0
             */

            private ExtraBean extra;
            private String musicType;
            private String musicTypeImg;
            /**
             * musicSinger : 张碧晨
             * musicUrl : http://ofplk6att.bkt.clouddn.com/diyichangxue.mp3
             * id : 6
             * musicName : 歌曲名6
             * musicType : 伤感
             * musicTypeImg : http://ofplk6att.bkt.clouddn.com/music_icon1.png
             */

            private List<MusicListBean> musicList;

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }

            public String getMusicType() {
                return musicType;
            }

            public void setMusicType(String musicType) {
                this.musicType = musicType;
            }

            public String getMusicTypeImg() {
                return musicTypeImg;
            }

            public void setMusicTypeImg(String musicTypeImg) {
                this.musicTypeImg = musicTypeImg;
            }

            public List<MusicListBean> getMusicList() {
                return musicList;
            }

            public void setMusicList(List<MusicListBean> musicList) {
                this.musicList = musicList;
            }

            public static class ExtraBean implements Serializable{
                private int totalSize;
                private int size;
                private int totalPage;
                private int page;

                public int getTotalSize() {
                    return totalSize;
                }

                public void setTotalSize(int totalSize) {
                    this.totalSize = totalSize;
                }

                public int getSize() {
                    return size;
                }

                public void setSize(int size) {
                    this.size = size;
                }

                public int getTotalPage() {
                    return totalPage;
                }

                public void setTotalPage(int totalPage) {
                    this.totalPage = totalPage;
                }

                public int getPage() {
                    return page;
                }

                public void setPage(int page) {
                    this.page = page;
                }
            }

            public static class MusicListBean implements Serializable{
                private String musicSinger;
                private String musicUrl;
                private int id;
                private String musicName;
                private String musicType;
                private String musicTypeImg;

                public String getMusicSinger() {
                    return musicSinger;
                }

                public void setMusicSinger(String musicSinger) {
                    this.musicSinger = musicSinger;
                }

                public String getMusicUrl() {
                    return musicUrl;
                }

                public void setMusicUrl(String musicUrl) {
                    this.musicUrl = musicUrl;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getMusicName() {
                    return musicName;
                }

                public void setMusicName(String musicName) {
                    this.musicName = musicName;
                }

                public String getMusicType() {
                    return musicType;
                }

                public void setMusicType(String musicType) {
                    this.musicType = musicType;
                }

                public String getMusicTypeImg() {
                    return musicTypeImg;
                }

                public void setMusicTypeImg(String musicTypeImg) {
                    this.musicTypeImg = musicTypeImg;
                }
            }
        }
    }
}
