/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.domain.entity.neihan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2017/9/23 下午11:20
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NeihanEntity {

    /**
     * message : success
     * data : {"has_more":true,"tip":"1条新内容","has_new_message":false,"max_time":1489235731,"min_time":1489235731,"data":[{"group":{"360p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=1&is_gif=0&device_platform=android"}],"uri":"360p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"mp4_url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android.mp4","text":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","720p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=1&is_gif=0&device_platform=android"}],"uri":"720p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"digg_count":288,"duration":297.672,"480p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=1&is_gif=0&device_platform=android"}],"uri":"480p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"create_time":1489234877,"keywords":"","id":57656588474,"favorite_count":0,"go_detail_count":738,"m3u8_url":"","large_cover":{"url_list":[{"url":"http://p1.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"}],"uri":"large/18b50004f8e236297f69"},"user_favorite":0,"share_type":1,"title":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","user":{"user_id":6623500329,"name":"大胃王密子君","followings":0,"user_verified":true,"ugc_count":41,"avatar_url":"http://p3.pstatp.com/medium/e5900153ca3fe711770","followers":270098,"is_following":false,"is_pro_user":false},"is_can_share":1,"category_type":1,"share_url":"http://m.neihanshequ.com/share/group/57656588474/?iid=3216590132&app=joke_essay","label":4,"content":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","video_height":360,"comment_count":28,"cover_image_uri":"18b50004f8e236297f69","id_str":"57656588474","media_type":3,"share_count":65,"type":2,"category_id":65,"status":102,"has_comments":0,"publish_time":"","user_bury":0,"activity":{},"status_desc":"已发表，粉丝第一时间可见","dislike_reason":[{"type":1,"id":321,"title":"美女"},{"type":1,"id":326,"title":"美食"},{"type":1,"id":340,"title":"猎奇"},{"type":1,"id":581,"title":"PGC原创"},{"type":2,"id":65,"title":"吧:搞笑视频"},{"type":4,"id":0,"title":"内容重复"},{"type":3,"id":6623500329,"title":"作者:大胃王密子君"}],"neihan_hot_start_time":"00-00-00","play_count":9024,"user_repin":0,"quick_comment":false,"medium_cover":{"url_list":[{"url":"http://p1.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"}],"uri":"medium/18b50004f8e236297f69"},"neihan_hot_end_time":"00-00-00","user_digg":0,"video_width":640,"online_time":1489234877,"category_name":"搞笑视频","flash_url":"","category_visible":true,"bury_count":38,"is_anonymous":false,"repin_count":0,"is_neihan_hot":false,"uri":"d2a0fc9bb4784a6c983cd4a341df6d76","is_public_url":1,"has_hot_comments":0,"allow_dislike":true,"origin_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=1&is_gif=0&device_platform=android"}],"uri":"origin/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"cover_image_url":"","neihan_hot_link":{},"group_id":57656588474,"is_video":1,"display_type":0},"comments":[],"type":1,"display_time":1489235731,"online_time":1489235731}]}
     */

    private String message;
    private DataEntityX data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntityX getData() {
        return data;
    }

    public void setData(DataEntityX data) {
        this.data = data;
    }

    public static class DataEntityX {
        /**
         * has_more : true
         * tip : 1条新内容
         * has_new_message : false
         * max_time : 1489235731
         * min_time : 1489235731
         * data : [{"group":{"360p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=1&is_gif=0&device_platform=android"}],"uri":"360p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"mp4_url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android.mp4","text":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","720p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=1&is_gif=0&device_platform=android"}],"uri":"720p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"digg_count":288,"duration":297.672,"480p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=1&is_gif=0&device_platform=android"}],"uri":"480p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"create_time":1489234877,"keywords":"","id":57656588474,"favorite_count":0,"go_detail_count":738,"m3u8_url":"","large_cover":{"url_list":[{"url":"http://p1.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"}],"uri":"large/18b50004f8e236297f69"},"user_favorite":0,"share_type":1,"title":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","user":{"user_id":6623500329,"name":"大胃王密子君","followings":0,"user_verified":true,"ugc_count":41,"avatar_url":"http://p3.pstatp.com/medium/e5900153ca3fe711770","followers":270098,"is_following":false,"is_pro_user":false},"is_can_share":1,"category_type":1,"share_url":"http://m.neihanshequ.com/share/group/57656588474/?iid=3216590132&app=joke_essay","label":4,"content":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","video_height":360,"comment_count":28,"cover_image_uri":"18b50004f8e236297f69","id_str":"57656588474","media_type":3,"share_count":65,"type":2,"category_id":65,"status":102,"has_comments":0,"publish_time":"","user_bury":0,"activity":{},"status_desc":"已发表，粉丝第一时间可见","dislike_reason":[{"type":1,"id":321,"title":"美女"},{"type":1,"id":326,"title":"美食"},{"type":1,"id":340,"title":"猎奇"},{"type":1,"id":581,"title":"PGC原创"},{"type":2,"id":65,"title":"吧:搞笑视频"},{"type":4,"id":0,"title":"内容重复"},{"type":3,"id":6623500329,"title":"作者:大胃王密子君"}],"neihan_hot_start_time":"00-00-00","play_count":9024,"user_repin":0,"quick_comment":false,"medium_cover":{"url_list":[{"url":"http://p1.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"}],"uri":"medium/18b50004f8e236297f69"},"neihan_hot_end_time":"00-00-00","user_digg":0,"video_width":640,"online_time":1489234877,"category_name":"搞笑视频","flash_url":"","category_visible":true,"bury_count":38,"is_anonymous":false,"repin_count":0,"is_neihan_hot":false,"uri":"d2a0fc9bb4784a6c983cd4a341df6d76","is_public_url":1,"has_hot_comments":0,"allow_dislike":true,"origin_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=1&is_gif=0&device_platform=android"}],"uri":"origin/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"cover_image_url":"","neihan_hot_link":{},"group_id":57656588474,"is_video":1,"display_type":0},"comments":[],"type":1,"display_time":1489235731,"online_time":1489235731}]
         */

        private boolean has_more;
        private String tip;
        private boolean has_new_message;
        private String max_time;
        private String min_time;
        private List<DataEntity> data;

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public boolean isHas_new_message() {
            return has_new_message;
        }

        public void setHas_new_message(boolean has_new_message) {
            this.has_new_message = has_new_message;
        }

        public String getMax_time() {
            return max_time;
        }

        public void setMax_time(String max_time) {
            this.max_time = max_time;
        }

        public String getMin_time() {
            return min_time;
        }

        public void setMin_time(String min_time) {
            this.min_time = min_time;
        }

        public List<DataEntity> getData() {
            return data;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public static class DataEntity {

            /**
             * group : {"360p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=1&is_gif=0&device_platform=android"}],"uri":"360p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"mp4_url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android.mp4","text":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","720p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=1&is_gif=0&device_platform=android"}],"uri":"720p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"digg_count":288,"duration":297.672,"480p_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=1&is_gif=0&device_platform=android"}],"uri":"480p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"create_time":1489234877,"keywords":"","id":57656588474,"favorite_count":0,"go_detail_count":738,"m3u8_url":"","large_cover":{"url_list":[{"url":"http://p1.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"}],"uri":"large/18b50004f8e236297f69"},"user_favorite":0,"share_type":1,"title":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","user":{"user_id":6623500329,"name":"大胃王密子君","followings":0,"user_verified":true,"ugc_count":41,"avatar_url":"http://p3.pstatp.com/medium/e5900153ca3fe711770","followers":270098,"is_following":false,"is_pro_user":false},"is_can_share":1,"category_type":1,"share_url":"http://m.neihanshequ.com/share/group/57656588474/?iid=3216590132&app=joke_essay","label":4,"content":"大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！","video_height":360,"comment_count":28,"cover_image_uri":"18b50004f8e236297f69","id_str":"57656588474","media_type":3,"share_count":65,"type":2,"category_id":65,"status":102,"has_comments":0,"publish_time":"","user_bury":0,"activity":{},"status_desc":"已发表，粉丝第一时间可见","dislike_reason":[{"type":1,"id":321,"title":"美女"},{"type":1,"id":326,"title":"美食"},{"type":1,"id":340,"title":"猎奇"},{"type":1,"id":581,"title":"PGC原创"},{"type":2,"id":65,"title":"吧:搞笑视频"},{"type":4,"id":0,"title":"内容重复"},{"type":3,"id":6623500329,"title":"作者:大胃王密子君"}],"neihan_hot_start_time":"00-00-00","play_count":9024,"user_repin":0,"quick_comment":false,"medium_cover":{"url_list":[{"url":"http://p1.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"}],"uri":"medium/18b50004f8e236297f69"},"neihan_hot_end_time":"00-00-00","user_digg":0,"video_width":640,"online_time":1489234877,"category_name":"搞笑视频","flash_url":"","category_visible":true,"bury_count":38,"is_anonymous":false,"repin_count":0,"is_neihan_hot":false,"uri":"d2a0fc9bb4784a6c983cd4a341df6d76","is_public_url":1,"has_hot_comments":0,"allow_dislike":true,"origin_video":{"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=1&is_gif=0&device_platform=android"}],"uri":"origin/d2a0fc9bb4784a6c983cd4a341df6d76","height":360},"cover_image_url":"","neihan_hot_link":{},"group_id":57656588474,"is_video":1,"display_type":0}
             * comments : []
             * type : 1
             * display_time : 1489235731
             * online_time : 1489235731
             */

            private GroupEntity group;
            private int type;
            private String display_time;
            private String online_time;
            private List<CommonEntity> comments;

            public GroupEntity getGroup() {
                return group;
            }

            public void setGroup(GroupEntity group) {
                this.group = group;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDisplay_time() {
                return display_time;
            }

            public void setDisplay_time(String display_time) {
                this.display_time = display_time;
            }

            public String getOnline_time() {
                return online_time;
            }

            public void setOnline_time(String online_time) {
                this.online_time = online_time;
            }

            public List<CommonEntity> getComments() {
                return comments;
            }

            public void setComments(List<CommonEntity> comments) {
                this.comments = comments;
            }

            public static class CommonEntity {
                private String text;
                private String user_name;
                private String comment_count;
                private String digg_count;
            }

            public static class GroupEntity {
                /**
                 * 360p_video : {"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=1&is_gif=0&device_platform=android"}],"uri":"360p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360}
                 * mp4_url : http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android.mp4
                 * text : 大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！
                 * 720p_video : {"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=1&is_gif=0&device_platform=android"}],"uri":"720p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360}
                 * digg_count : 288
                 * duration : 297.672
                 * 480p_video : {"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=1&is_gif=0&device_platform=android"}],"uri":"480p/d2a0fc9bb4784a6c983cd4a341df6d76","height":360}
                 * create_time : 1489234877
                 * keywords :
                 * id : 57656588474
                 * favorite_count : 0
                 * go_detail_count : 738
                 * m3u8_url :
                 * large_cover : {"url_list":[{"url":"http://p1.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"}],"uri":"large/18b50004f8e236297f69"}
                 * user_favorite : 0
                 * share_type : 1
                 * title : 大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！
                 * user : {"user_id":6623500329,"name":"大胃王密子君","followings":0,"user_verified":true,"ugc_count":41,"avatar_url":"http://p3.pstatp.com/medium/e5900153ca3fe711770","followers":270098,"is_following":false,"is_pro_user":false}
                 * is_can_share : 1
                 * category_type : 1
                 * share_url : http://m.neihanshequ.com/share/group/57656588474/?iid=3216590132&app=joke_essay
                 * label : 4
                 * content : 大胃王密子君（异国风情餐）芝士就是力量，所以我要多吃一点！
                 * video_height : 360
                 * comment_count : 28
                 * cover_image_uri : 18b50004f8e236297f69
                 * id_str : 57656588474
                 * media_type : 3
                 * share_count : 65
                 * type : 2
                 * category_id : 65
                 * status : 102
                 * has_comments : 0
                 * publish_time :
                 * user_bury : 0
                 * activity : {}
                 * status_desc : 已发表，粉丝第一时间可见
                 * dislike_reason : [{"type":1,"id":321,"title":"美女"},{"type":1,"id":326,"title":"美食"},{"type":1,"id":340,"title":"猎奇"},{"type":1,"id":581,"title":"PGC原创"},{"type":2,"id":65,"title":"吧:搞笑视频"},{"type":4,"id":0,"title":"内容重复"},{"type":3,"id":6623500329,"title":"作者:大胃王密子君"}]
                 * neihan_hot_start_time : 00-00-00
                 * play_count : 9024
                 * user_repin : 0
                 * quick_comment : false
                 * medium_cover : {"url_list":[{"url":"http://p1.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"}],"uri":"medium/18b50004f8e236297f69"}
                 * neihan_hot_end_time : 00-00-00
                 * user_digg : 0
                 * video_width : 640
                 * online_time : 1489234877
                 * category_name : 搞笑视频
                 * flash_url :
                 * category_visible : true
                 * bury_count : 38
                 * is_anonymous : false
                 * repin_count : 0
                 * is_neihan_hot : false
                 * uri : d2a0fc9bb4784a6c983cd4a341df6d76
                 * is_public_url : 1
                 * has_hot_comments : 0
                 * allow_dislike : true
                 * origin_video : {"width":640,"url_list":[{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=origin&line=1&is_gif=0&device_platform=android"}],"uri":"origin/d2a0fc9bb4784a6c983cd4a341df6d76","height":360}
                 * cover_image_url :
                 * neihan_hot_link : {}
                 * group_id : 57656588474
                 * is_video : 1
                 * display_type : 0
                 */

                @SerializedName("360p_video")
                private Video360pEntity video_360p;
                private String mp4_url;
                private String text;
                @SerializedName("720p_video")
                private Video720pEntity video_720p;
                private int digg_count;
                private double duration;
                @SerializedName("480p_video")
                private Video480pEntity video_480p;
                private int create_time;
                private String keywords;
                private long id;
                private int favorite_count;
                private int go_detail_count;
                private String m3u8_url;
                private LargeCoverEntity large_cover;
                private int user_favorite;
                private int share_type;
                private String title;
                private UserEntity user;
                private int is_can_share;
                private int category_type;
                private String share_url;
                private int label;
                private String content;
                private int video_height;
                private int comment_count;
                private String cover_image_uri;
                private String id_str;
                private int media_type;
                private int share_count;
                private int type;
                private int category_id;
                private int status;
                private int has_comments;
                private String publish_time;
                private int user_bury;
                private ActivityEntity activity;
                private String status_desc;
                private String neihan_hot_start_time;
                private int play_count;
                private int user_repin;
                private boolean quick_comment;
                private MediumCoverEntity medium_cover;
                private String neihan_hot_end_time;
                private int user_digg;
                private int video_width;
                private int online_time;
                private String category_name;
                private String flash_url;
                private boolean category_visible;
                private int bury_count;
                private boolean is_anonymous;
                private int repin_count;
                private boolean is_neihan_hot;
                private String uri;
                private int is_public_url;
                private int has_hot_comments;
                private boolean allow_dislike;
                private OriginVideoEntity origin_video;
                private String cover_image_url;
                private NeihanHotLinkEntity neihan_hot_link;
                private long group_id;
                private int is_video;
                private int display_type;
                private List<DislikeReasonEntity> dislike_reason;

                public Video360pEntity getVideo_360p() {
                    return video_360p;
                }

                public void setVideo_360p(Video360pEntity video_360p) {
                    this.video_360p = video_360p;
                }

                public String getMp4_url() {
                    return mp4_url;
                }

                public void setMp4_url(String mp4_url) {
                    this.mp4_url = mp4_url;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public Video720pEntity getVideo720pEntity() {
                    return video_720p;
                }

                public void setVideo720pEntity(Video720pEntity video_720p) {
                    this.video_720p = video_720p;
                }

                public int getDigg_count() {
                    return digg_count;
                }

                public void setDigg_count(int digg_count) {
                    this.digg_count = digg_count;
                }

                public double getDuration() {
                    return duration;
                }

                public void setDuration(double duration) {
                    this.duration = duration;
                }

                public Video480pEntity getVideo480pEntity() {
                    return video_480p;
                }

                public void setVideo480pEntity(Video480pEntity video_480p) {
                    this.video_480p = video_480p;
                }

                public int getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(int create_time) {
                    this.create_time = create_time;
                }

                public String getKeywords() {
                    return keywords;
                }

                public void setKeywords(String keywords) {
                    this.keywords = keywords;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public int getFavorite_count() {
                    return favorite_count;
                }

                public void setFavorite_count(int favorite_count) {
                    this.favorite_count = favorite_count;
                }

                public int getGo_detail_count() {
                    return go_detail_count;
                }

                public void setGo_detail_count(int go_detail_count) {
                    this.go_detail_count = go_detail_count;
                }

                public String getM3u8_url() {
                    return m3u8_url;
                }

                public void setM3u8_url(String m3u8_url) {
                    this.m3u8_url = m3u8_url;
                }

                public LargeCoverEntity getLarge_cover() {
                    return large_cover;
                }

                public void setLarge_cover(LargeCoverEntity large_cover) {
                    this.large_cover = large_cover;
                }

                public int getUser_favorite() {
                    return user_favorite;
                }

                public void setUser_favorite(int user_favorite) {
                    this.user_favorite = user_favorite;
                }

                public int getShare_type() {
                    return share_type;
                }

                public void setShare_type(int share_type) {
                    this.share_type = share_type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public UserEntity getUser() {
                    return user;
                }

                public void setUser(UserEntity user) {
                    this.user = user;
                }

                public int getIs_can_share() {
                    return is_can_share;
                }

                public void setIs_can_share(int is_can_share) {
                    this.is_can_share = is_can_share;
                }

                public int getCategory_type() {
                    return category_type;
                }

                public void setCategory_type(int category_type) {
                    this.category_type = category_type;
                }

                public String getShare_url() {
                    return share_url;
                }

                public void setShare_url(String share_url) {
                    this.share_url = share_url;
                }

                public int getLabel() {
                    return label;
                }

                public void setLabel(int label) {
                    this.label = label;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getVideo_height() {
                    return video_height;
                }

                public void setVideo_height(int video_height) {
                    this.video_height = video_height;
                }

                public int getComment_count() {
                    return comment_count;
                }

                public void setComment_count(int comment_count) {
                    this.comment_count = comment_count;
                }

                public String getCover_image_uri() {
                    return cover_image_uri;
                }

                public void setCover_image_uri(String cover_image_uri) {
                    this.cover_image_uri = cover_image_uri;
                }

                public String getId_str() {
                    return id_str;
                }

                public void setId_str(String id_str) {
                    this.id_str = id_str;
                }

                public int getMedia_type() {
                    return media_type;
                }

                public void setMedia_type(int media_type) {
                    this.media_type = media_type;
                }

                public int getShare_count() {
                    return share_count;
                }

                public void setShare_count(int share_count) {
                    this.share_count = share_count;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getHas_comments() {
                    return has_comments;
                }

                public void setHas_comments(int has_comments) {
                    this.has_comments = has_comments;
                }

                public String getPublish_time() {
                    return publish_time;
                }

                public void setPublish_time(String publish_time) {
                    this.publish_time = publish_time;
                }

                public int getUser_bury() {
                    return user_bury;
                }

                public void setUser_bury(int user_bury) {
                    this.user_bury = user_bury;
                }

                public ActivityEntity getActivity() {
                    return activity;
                }

                public void setActivity(ActivityEntity activity) {
                    this.activity = activity;
                }

                public String getStatus_desc() {
                    return status_desc;
                }

                public void setStatus_desc(String status_desc) {
                    this.status_desc = status_desc;
                }

                public String getNeihan_hot_start_time() {
                    return neihan_hot_start_time;
                }

                public void setNeihan_hot_start_time(String neihan_hot_start_time) {
                    this.neihan_hot_start_time = neihan_hot_start_time;
                }

                public int getPlay_count() {
                    return play_count;
                }

                public void setPlay_count(int play_count) {
                    this.play_count = play_count;
                }

                public int getUser_repin() {
                    return user_repin;
                }

                public void setUser_repin(int user_repin) {
                    this.user_repin = user_repin;
                }

                public boolean isQuick_comment() {
                    return quick_comment;
                }

                public void setQuick_comment(boolean quick_comment) {
                    this.quick_comment = quick_comment;
                }

                public MediumCoverEntity getMedium_cover() {
                    return medium_cover;
                }

                public void setMedium_cover(MediumCoverEntity medium_cover) {
                    this.medium_cover = medium_cover;
                }

                public String getNeihan_hot_end_time() {
                    return neihan_hot_end_time;
                }

                public void setNeihan_hot_end_time(String neihan_hot_end_time) {
                    this.neihan_hot_end_time = neihan_hot_end_time;
                }

                public int getUser_digg() {
                    return user_digg;
                }

                public void setUser_digg(int user_digg) {
                    this.user_digg = user_digg;
                }

                public int getVideo_width() {
                    return video_width;
                }

                public void setVideo_width(int video_width) {
                    this.video_width = video_width;
                }

                public int getOnline_time() {
                    return online_time;
                }

                public void setOnline_time(int online_time) {
                    this.online_time = online_time;
                }

                public String getCategory_name() {
                    return category_name;
                }

                public void setCategory_name(String category_name) {
                    this.category_name = category_name;
                }

                public String getFlash_url() {
                    return flash_url;
                }

                public void setFlash_url(String flash_url) {
                    this.flash_url = flash_url;
                }

                public boolean isCategory_visible() {
                    return category_visible;
                }

                public void setCategory_visible(boolean category_visible) {
                    this.category_visible = category_visible;
                }

                public int getBury_count() {
                    return bury_count;
                }

                public void setBury_count(int bury_count) {
                    this.bury_count = bury_count;
                }

                public boolean isIs_anonymous() {
                    return is_anonymous;
                }

                public void setIs_anonymous(boolean is_anonymous) {
                    this.is_anonymous = is_anonymous;
                }

                public int getRepin_count() {
                    return repin_count;
                }

                public void setRepin_count(int repin_count) {
                    this.repin_count = repin_count;
                }

                public boolean isIs_neihan_hot() {
                    return is_neihan_hot;
                }

                public void setIs_neihan_hot(boolean is_neihan_hot) {
                    this.is_neihan_hot = is_neihan_hot;
                }

                public String getUri() {
                    return uri;
                }

                public void setUri(String uri) {
                    this.uri = uri;
                }

                public int getIs_public_url() {
                    return is_public_url;
                }

                public void setIs_public_url(int is_public_url) {
                    this.is_public_url = is_public_url;
                }

                public int getHas_hot_comments() {
                    return has_hot_comments;
                }

                public void setHas_hot_comments(int has_hot_comments) {
                    this.has_hot_comments = has_hot_comments;
                }

                public boolean isAllow_dislike() {
                    return allow_dislike;
                }

                public void setAllow_dislike(boolean allow_dislike) {
                    this.allow_dislike = allow_dislike;
                }

                public OriginVideoEntity getOrigin_video() {
                    return origin_video;
                }

                public void setOrigin_video(OriginVideoEntity origin_video) {
                    this.origin_video = origin_video;
                }

                public String getCover_image_url() {
                    return cover_image_url;
                }

                public void setCover_image_url(String cover_image_url) {
                    this.cover_image_url = cover_image_url;
                }

                public NeihanHotLinkEntity getNeihan_hot_link() {
                    return neihan_hot_link;
                }

                public void setNeihan_hot_link(NeihanHotLinkEntity neihan_hot_link) {
                    this.neihan_hot_link = neihan_hot_link;
                }

                public long getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(long group_id) {
                    this.group_id = group_id;
                }

                public int getIs_video() {
                    return is_video;
                }

                public void setIs_video(int is_video) {
                    this.is_video = is_video;
                }

                public int getDisplay_type() {
                    return display_type;
                }

                public void setDisplay_type(int display_type) {
                    this.display_type = display_type;
                }

                public List<DislikeReasonEntity> getDislike_reason() {
                    return dislike_reason;
                }

                public void setDislike_reason(List<DislikeReasonEntity> dislike_reason) {
                    this.dislike_reason = dislike_reason;
                }

                public static class Video360pEntity {
                    /**
                     * width : 640
                     * url_list : [{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=1&is_gif=0&device_platform=android"}]
                     * uri : 360p/d2a0fc9bb4784a6c983cd4a341df6d76
                     * height : 360
                     */

                    private int width;
                    private String uri;
                    private int height;
                    private List<UrlListEntity> url_list;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<UrlListEntity> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListEntity> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListEntity {
                        /**
                         * url : http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=360p&line=0&is_gif=0&device_platform=android
                         */

                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }

                public static class Video720pEntity {
                    /**
                     * width : 640
                     * url_list : [{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=1&is_gif=0&device_platform=android"}]
                     * uri : 720p/d2a0fc9bb4784a6c983cd4a341df6d76
                     * height : 360
                     */

                    private int width;
                    private String uri;
                    private int height;
                    private List<UrlListEntityX> url_list;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<UrlListEntityX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListEntityX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListEntityX {
                        /**
                         * url : http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=720p&line=0&is_gif=0&device_platform=android
                         */

                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }

                public static class Video480pEntity {
                    /**
                     * width : 640
                     * url_list : [{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android"},{"url":"http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=1&is_gif=0&device_platform=android"}]
                     * uri : 480p/d2a0fc9bb4784a6c983cd4a341df6d76
                     * height : 360
                     */

                    private int width;
                    private String uri;
                    private int height;
                    private List<UrlListEntityXX> url_list;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<UrlListEntityXX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListEntityXX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListEntityXX {
                        /**
                         * url : http://ic.snssdk.com/neihan/video/playback/1489235732.1/?video_id=d2a0fc9bb4784a6c983cd4a341df6d76&quality=480p&line=0&is_gif=0&device_platform=android
                         */

                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }

                public static class LargeCoverEntity {
                    /**
                     * url_list : [{"url":"http://p1.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/large/18b50004f8e236297f69.webp"}]
                     * uri : large/18b50004f8e236297f69
                     */

                    private String uri;
                    private List<UrlListEntityXXX> url_list;

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public List<UrlListEntityXXX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListEntityXXX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListEntityXXX {
                        /**
                         * url : http://p1.pstatp.com/large/18b50004f8e236297f69.webp
                         */

                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }

                public static class UserEntity {
                    private String user_id;
                    private String name;
                    private int followings;
                    private boolean user_verified;
                    private int ugc_count;
                    private String avatar_url;
                    private int followers;
                    private boolean is_following;
                    private boolean is_pro_user;

                    public String getUser_id() {
                        return user_id;
                    }

                    public String getName() {
                        return name;
                    }

                    public int getFollowings() {
                        return followings;
                    }

                    public boolean isUser_verified() {
                        return user_verified;
                    }

                    public int getUgc_count() {
                        return ugc_count;
                    }

                    public String getAvatar_url() {
                        return avatar_url;
                    }

                    public int getFollowers() {
                        return followers;
                    }

                    public boolean is_following() {
                        return is_following;
                    }

                    public boolean is_pro_user() {
                        return is_pro_user;
                    }
                }

                public static class ActivityEntity {
                }

                public static class MediumCoverEntity {
                    /**
                     * url_list : [{"url":"http://p1.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"},{"url":"http://pb3.pstatp.com/w202/18b50004f8e236297f69.webp"}]
                     * uri : medium/18b50004f8e236297f69
                     */

                    private String uri;
                    private List<UrlListEntityXXXX> url_list;

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public List<UrlListEntityXXXX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListEntityXXXX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListEntityXXXX {
                        /**
                         * url : http://p1.pstatp.com/w202/18b50004f8e236297f69.webp
                         */

                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }

                public static class OriginVideoEntity {
                    private String uri;
                    private List<UrlListEntityXXXXX> url_list;

                    public String getUri() {
                        return uri;
                    }

                    public void setUri(String uri) {
                        this.uri = uri;
                    }

                    public List<UrlListEntityXXXXX> getUrl_list() {
                        return url_list;
                    }

                    public void setUrl_list(List<UrlListEntityXXXXX> url_list) {
                        this.url_list = url_list;
                    }

                    public static class UrlListEntityXXXXX {
                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }

                public static class NeihanHotLinkEntity {
                }

                public static class DislikeReasonEntity {
                    /**
                     * type : 1
                     * id : 321
                     * title : 美女
                     */

                    private int type;
                    private long id;
                    private String title;

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public long getId() {
                        return id;
                    }

                    public void setId(long id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }
            }
        }
    }
}
