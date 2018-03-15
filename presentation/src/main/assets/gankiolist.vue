<template>
    <list class="list" @loadmore="loadmore" loadmoreoffset="10">
        <cell class="cell" v-for="(item,index) in lists" @click="itemclick(item,index)">
            <div class="panel1">
                <div class="panel2">
                    <text class="text1" lines="1">{{item.desc}}</text>
                    <text class="text2" lines="1">{{"作者："+isWhoEmpty(item.who)}}</text>
                    <text class="text3" lines="1">{{"发布时间："+getLocalTime(item.publishedAt)}}</text>
                </div>
                <text class="text4">></text>
            </div>
        </cell>
    </list>
</template>

<script>
  const modal = weex.requireModule('modal');
  const stream = weex.requireModule('stream');
  const GANK_IO_BASE_URL = "http://gank.io/api/random/data/";

  export default {
    data () {
      return {
        lists: []
      }
    },
    methods: {
      getLocalTime:function(time){
        var date= new Date(time);
        return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
      },
      isWhoEmpty:function(who){
        if(who){
          return who;
        }
        return "神秘大佬 ";
      },
      itemclick:function(item,index){
        modal.toast({ message: '你点击了'+item.desc+",该项是第"+(index+1), duration: 1 })
      },
      loadmore (event) {
        modal.toast({ message: 'loadmore', duration: 1 })
        const self = this;
        setTimeout(() => {
           stream.fetch({
            method: 'GET',
            url: GANK_IO_BASE_URL,
            type:'json'
          }, function(ret) {
            if(!ret.ok){
              modal.toast({ message: 'request failed', duration: 1 });
            }else{
              const length = ret.data.results.length
              for (let i = 0; i < length; i++) {
                self.lists.push(ret.data.results[i])
              }
            }
          });
        }, 600)
      },
    },
    created: function() {
        const self = this;
        const gank_io_url= GANK_IO_BASE_URL+weex.config.type+"/10";
        modal.toast({ message: gank_io_url, duration: 1 });
          stream.fetch({
            method: 'GET',
            url: gank_io_url,
            type:'json'
          }, function(ret) {
            if(!ret.ok){
              modal.toast({ message: 'request failed', duration: 1 });
            }else{
              self.lists = ret.data.results;
            }
          });
      },
  }

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
    box-shadow:0px 5px 20px 6px #80888888;
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
  .text4 {
    lines: 1;
    margin-top: 20px;
    margin-left: 30px;
    font-size: 40px;
    color: #333333;
  }

</style>