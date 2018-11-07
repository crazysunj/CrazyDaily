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
package com.crazysunj.domain.entity.zhihu;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: sunjian
 * created on: 2017/8/31 上午9:31
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ZhihuNewsEntity {


    /**
     * date : 20170830
     * stories : [{"images":["https://pic3.zhimg.com/v2-2c4233255c4749048bf27d09f04b3e56.jpg"],"type":0,"id":9594997,"ga_prefix":"083019","title":"作为一名人民警察，我来讲讲如何让孩子真正远离性侵的「魔爪」"},{"images":["https://pic2.zhimg.com/v2-bd37a70da57631af81d1c27092a53041.jpg"],"type":0,"id":9593371,"ga_prefix":"083018","title":"初音未来十岁了，我第一次知道她，还是因为那首甩葱歌"},{"images":["https://pic3.zhimg.com/v2-29fa1e9def2c25b03f66956db9c4784e.jpg"],"type":0,"id":9595075,"ga_prefix":"083017","title":"- 走，排位，带你上分\r\n- 不和你组，你是带我送分"},{"images":["https://pic3.zhimg.com/v2-acaabca3c17989d2e7bcbd87afc76e12.jpg"],"type":0,"id":9593785,"ga_prefix":"083016","title":"终于，一张「叫停令」，共享单车到了要变个玩法的时候"},{"images":["https://pic2.zhimg.com/v2-428af8feb112042c9d25560b76799e49.jpg"],"type":0,"id":9594584,"ga_prefix":"083015","title":"一条蚯蚓被切断后再生为两条蚯蚓，它们的「思维」一样吗？"},{"images":["https://pic2.zhimg.com/v2-039d2e1e69422f4068addecfc7ad9c51.jpg"],"type":0,"id":9593142,"ga_prefix":"083014","title":"男朋友一直把我想成了这样，现在该怎么跟他解释啊\u2026\u2026"},{"images":["https://pic4.zhimg.com/v2-a8db67801331e1342ffd3c1a1bc90fff.jpg"],"type":0,"id":9594153,"ga_prefix":"083012","title":"大误 · 这叫时尚，不懂了吧"},{"images":["https://pic3.zhimg.com/v2-d659cf6b9550e98afca87531c22bf986.jpg"],"type":0,"id":9592718,"ga_prefix":"083011","title":"为什么大部分药片都是白色的？"},{"images":["https://pic3.zhimg.com/v2-2bb744714726d611be3f78bc29cfeade.jpg"],"type":0,"id":9594236,"ga_prefix":"083010","title":"生重病了，公司不要我怎么办？"},{"images":["https://pic3.zhimg.com/v2-90f905f99622321267d6459b637c81ea.jpg"],"type":0,"id":9593160,"ga_prefix":"083009","title":"当你背井离乡，他们在家门口训练、比赛，成为欧洲冠军"},{"images":["https://pic2.zhimg.com/v2-b5bedc3f452970fa4d4c348eefc4809d.jpg"],"type":0,"id":9593439,"ga_prefix":"083008","title":"一碟小小的渍菜，微酸中的发酵香气开胃至极"},{"images":["https://pic4.zhimg.com/v2-a1facccb8e445bb6fba41c27cb8e21d7.jpg"],"type":0,"id":9594201,"ga_prefix":"083007","title":"朋友圈被「一元购画」刷屏，别误会了艺术和自闭症的联系"},{"images":["https://pic4.zhimg.com/v2-69163504dfe0655e09a1632228588747.jpg"],"type":0,"id":9594013,"ga_prefix":"083007","title":"见过秀包秀鞋，你见过在朋友圈秀自己天天很忙的吗？"},{"images":["https://pic2.zhimg.com/v2-6eba53cb943910d381264dfdc8a54cf5.jpg"],"type":0,"id":9594130,"ga_prefix":"083007","title":"为什么男孩普遍喜欢漂亮的女孩？"},{"images":["https://pic1.zhimg.com/v2-7591d6592c15c8fe5e0e89d19b90fcd8.jpg"],"type":0,"id":9445905,"ga_prefix":"083006","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic4.zhimg.com/v2-161dca9a30a58d3dd195321d7be45baf.jpg","type":0,"id":9593371,"ga_prefix":"083018","title":"初音未来十岁了，我第一次知道她，还是因为那首甩葱歌"},{"image":"https://pic1.zhimg.com/v2-d70cee21627521f2016b0b9b4b0435f0.jpg","type":0,"id":9595075,"ga_prefix":"083017","title":"- 走，排位，带你上分\r\n- 不和你组，你是带我送分"},{"image":"https://pic3.zhimg.com/v2-69816fc616dc128cf9506f935ad94c52.jpg","type":0,"id":9593785,"ga_prefix":"083016","title":"终于，一张「叫停令」，共享单车到了要变个玩法的时候"},{"image":"https://pic3.zhimg.com/v2-c48f84051f6dcced21fbb5eb53db52fe.jpg","type":0,"id":9594201,"ga_prefix":"083007","title":"朋友圈被「一元购画」刷屏，别误会了艺术和自闭症的联系"},{"image":"https://pic1.zhimg.com/v2-f969a29b5a79bbb0f4b136c53fab0740.jpg","type":0,"id":9586188,"ga_prefix":"082506","title":"这里是广告 · 为什么 RAP\r\n 要唱那么快？"}]
     */

    private String date;
    private List<StoriesEntity> stories;
    private List<TopStoriesEntity> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesEntity extends MultiTypeIdEntity {
        /**
         * images : ["https://pic3.zhimg.com/v2-2c4233255c4749048bf27d09f04b3e56.jpg"]
         * type : 0
         * id : 9594997
         * ga_prefix : 083019
         * title : 作为一名人民警察，我来讲讲如何让孩子真正远离性侵的「魔爪」
         */
        public static final int TYPE_ZHIHU_NEWS = 0;
        public static final String HEADER_TITLE = "知乎日报";
        public static final String HEADER_OPTIONS = "最新";
        private int type;
        private long id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public int getItemType() {
            return TYPE_ZHIHU_NEWS;
        }

        @Override
        public String getStringId() {
            return String.valueOf(id);
        }

        @Override
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesEntity implements Serializable {
        /**
         * image : https://pic4.zhimg.com/v2-161dca9a30a58d3dd195321d7be45baf.jpg
         * type : 0
         * id : 9593371
         * ga_prefix : 083018
         * title : 初音未来十岁了，我第一次知道她，还是因为那首甩葱歌
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
