<template>
  <list class="list" @loadmore="loadmore" loadmoreoffset="10">
      <cell class="cell" v-for="item in lists" @click="itemclick(item)">
          <div class="panel1">
              <div class="panel2">
                  <text class="text1" lines="1">{{item.desc}}</text>
                  <text class="text2" lines="1">{{'作者：'+isWhoEmpty(item.who)}}</text>
                  <text class="text3" lines="1">{{'发布时间：'+getLocalTime(item.publishedAt)}}</text>
              </div>
              <image class="icon" src="mipmap://ic_go.png"/>
          </div>
      </cell>

      <loading class="loading" :display="loadinging ? 'show' : 'hide'">
            <loading-indicator class="indicator"></loading-indicator>
            <text class="indicator-text">正在加载中...</text>
          </loading>
  </list>
</template>

<script>
  const modal = weex.requireModule('modal');
  const stream = weex.requireModule('stream');
  const GANK_IO_BASE_URL = 'http://gank.io/api/random/data/';
  const gankIoUrl = `${GANK_IO_BASE_URL}${weex.config.type}/10`;

  export default {
    data() {
      return {
        loadinging: false,
        lists: [],
      };
    },
    methods: {
      getLocalTime: (time) => {
        const date = new Date(time);
        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
      },
      isWhoEmpty: (who) => {
        if (who) {
          return who;
        }
        return '神秘大佬';
      },
      itemclick: (item) => {
        weex.requireModule('crazyDaily').router(item.url);
      },
      loadmore() {
        const self = this;
        self.loadinging = true;
        setTimeout(() => {
          stream.fetch({
            method: 'GET',
            url: gankIoUrl,
            type: 'json',
          }, (ret) => {
            if (!ret.ok) {
              modal.toast({ message: 'request failed', duration: 1 });
            } else {
              ret.data.results.forEach(value => self.lists.push(value));
            }
            self.loadinging = false;
          });
        }, 200);
      },
    },
    created() {
      const self = this;
      stream.fetch({
        method: 'GET',
        url: gankIoUrl,
        type: 'json',
      }, (ret) => {
        if (!ret.ok) {
          modal.toast({ message: 'request failed', duration: 1 });
        } else {
          ret.data.results.forEach(value => self.lists.push(value));
        }
      });
    },
  };
</script>

<style scoped>
.panel1 {
  width: 690px;
  height: 220px;
  margin-left: 30px;
  margin-top: 20px;
  margin-bottom: 20px;
  align-items:center;
  flex-direction: row;
  box-shadow:0px 3px 12px 4px #80888888;
  border-radius:20px;
}

.panel2 {
  width: 600px;
  height: 220px;
  flex-direction: column;
}
.text1 {
  margin-top: 20px;
  lines: 1;
  margin-left: 40px;
  font-size: 46px;
  color: #333333;
  text-overflow:ellipsis;
}
.text2 {
  lines: 1;
  margin-top: 20px;
  margin-left: 40px;
  font-size: 36px;
  color: #666666;
}
.text3 {
  lines: 1;
  margin-top: 35px;
  margin-left: 40px;
  font-size: 26px;
  color: #999999;
}
.icon {
  margin-top: 20px;
  margin-left: 30px;
  width:20px;
  height:26px;
}

.loading {
    width: 750;
    display: -ms-flex;
    display: -webkit-flex;
    display: flex;
    -ms-flex-align: center;
    -webkit-align-items: center;
    -webkit-box-align: center;
    align-items: center;
  }

.indicator-text {
    margin-bottom: 16px;
    color: #666666;
    font-size: 42px;
    text-align: center;
  }
.indicator {
  margin-bottom: 16px;
  height: 40px;
  width: 40px;
  color: #FF4081;
}
</style>