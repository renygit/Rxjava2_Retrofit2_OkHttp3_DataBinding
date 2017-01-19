package com.dsc.databindingdemo.model;

import java.util.List;

/**
 * Created by reny on 2017/1/5.
 */

public class GankData {

    /**
     * error : false
     * results : [{"_id":"586d1ae9421aa93161103d9c","createdAt":"2017-01-04T23:55:21.180Z","desc":"用于做Path动画的自定义View","images":["http://img.gank.io/e4aea783-6471-4448-a7b7-f5a8bc87e915"],"publishedAt":"2017-01-05T13:18:10.185Z","source":"chrome","type":"Android","url":"https://github.com/mcxtzhang/PathAnimView","used":true,"who":"Jason"},{"_id":"586db3b3421aa9316407fb5a","createdAt":"2017-01-05T10:47:15.658Z","desc":"仿网易云音乐 安卓版","images":["http://img.gank.io/5ab7c721-1514-48fe-8d3a-bfeecf030a1c"],"publishedAt":"2017-01-05T13:18:10.185Z","source":"chrome","type":"Android","url":"https://github.com/aa112901/remusic","used":true,"who":"wuzheng"},{"_id":"58676f53421aa94dbbd82bc4","createdAt":"2016-12-31T16:41:55.141Z","desc":"简单易用的TextView装饰库","images":["http://img.gank.io/0412420b-0fa8-41c2-bd37-8007d0388df2"],"publishedAt":"2017-01-04T11:39:01.326Z","source":"chrome","type":"Android","url":"https://github.com/nntuyen/text-decorator","used":true,"who":"蒋朋"},{"_id":"586b8182421aa94dbe2ccdd9","createdAt":"2017-01-03T18:48:34.914Z","desc":"「有干货」\u2014\u2014 体验别具一格的 Gank.io Android 客户端","images":["http://img.gank.io/0d5c83f4-a915-46ec-8803-9dbc2ccc175f"],"publishedAt":"2017-01-04T11:39:01.326Z","source":"web","type":"Android","url":"https://github.com/Bakumon/UGank","used":true,"who":"马飞"},{"_id":"586b8a0c421aa94db821c287","createdAt":"2017-01-03T19:25:00.60Z","desc":"一个可配置的迷你版轻量级 Label 辅助类，支持多种配置效果。","images":["http://img.gank.io/d9a52c61-afc7-463f-83c8-173a1aace2b8"],"publishedAt":"2017-01-04T11:39:01.326Z","source":"web","type":"Android","url":"https://github.com/yanbober/AvatarLabelView","used":true,"who":"yanbo"},{"_id":"586c5ccc421aa94dc1ac0afe","createdAt":"2017-01-04T10:24:12.143Z","desc":"在 Android 上，实现沃漂亮的罗诺伊图。","images":["http://img.gank.io/ba845fe1-c66b-41a2-a791-bfeaeaa354f7","http://img.gank.io/c2bc3704-07ba-47f8-912c-7db44810afbe"],"publishedAt":"2017-01-04T11:39:01.326Z","source":"chrome","type":"Android","url":"https://github.com/Quatja/Vorolay","used":true,"who":"代码家"},{"_id":"586c5d3e421aa94dbbd82be2","createdAt":"2017-01-04T10:26:06.946Z","desc":"一个超漂亮的音乐播放器！尤其是音频的可视化功能，超炫酷！","images":["http://img.gank.io/dfff91b4-3522-47e1-80bc-a65001a616ad","http://img.gank.io/2dbdb3b3-b547-4518-8263-22fd2de316cd"],"publishedAt":"2017-01-04T11:39:01.326Z","source":"chrome","type":"Android","url":"https://github.com/harjot-oberai/MusicStreamer","used":true,"who":"代码家"},{"_id":"586af8d5421aa94dc1ac0aea","createdAt":"2017-01-03T09:05:25.837Z","desc":"Android热补丁之Tinker原理解析","publishedAt":"2017-01-03T11:51:31.742Z","source":"web","type":"Android","url":"http://w4lle.github.io/2016/12/16/tinker/","used":true,"who":"王令龙"},{"_id":"586b08c5421aa94dc1ac0aec","createdAt":"2017-01-03T10:13:25.145Z","desc":"Android Studio 2.2的新鲜事","publishedAt":"2017-01-03T11:51:31.742Z","source":"chrome","type":"Android","url":"http://mp.weixin.qq.com/s?__biz=MzAwODY4OTk2Mg==&mid=2652039482&idx=1&sn=9aa9b204af34baabd2fdbba649e20d79&scene=21#wechat_redirect","used":true,"who":"LHF"},{"_id":"586b0db6421aa94dbe2ccdd2","createdAt":"2017-01-03T10:34:30.138Z","desc":"RxJava 实现 Android 指纹识别","images":["http://img.gank.io/e887b9ec-28ac-4fbb-a18c-93e6ee127535"],"publishedAt":"2017-01-03T11:51:31.742Z","source":"chrome","type":"Android","url":"https://github.com/Zweihui/RxFingerPrinter","used":true,"who":"代码家"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 586d1ae9421aa93161103d9c
         * createdAt : 2017-01-04T23:55:21.180Z
         * desc : 用于做Path动画的自定义View
         * images : ["http://img.gank.io/e4aea783-6471-4448-a7b7-f5a8bc87e915"]
         * publishedAt : 2017-01-05T13:18:10.185Z
         * source : chrome
         * type : Android
         * url : https://github.com/mcxtzhang/PathAnimView
         * used : true
         * who : Jason
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
