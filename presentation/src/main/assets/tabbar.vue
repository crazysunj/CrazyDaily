<template>
    <tabbar id="tabbar" bgopacity="0.9"  tabs="{{tabItems}}" selected="{{page}}" class="tabbar">

    </tabbar>
</template>

<style>
.tabbar{
  width:750;
  z-index: 10;}
</style>

<script>
require("weex-tabbar");
module.exports = {
  data:{
    page:0,//默认选择的是第一个页面
    tabItems: [//配置每个tabbar
      {
        title: "首页",
        defaultTitleColor: "#000",
        image: "http://gtms01.alicdn.com/tps/i1/TB1qw.hMpXXXXagXXXX9t7RGVXX-46-46.png",
        selectedImage: "http://gtms04.alicdn.com/tps/i4/TB16jjPMpXXXXazXVXX9t7RGVXX-46-46.png",
        activeTitleColor:"#db3652"
      },
      {
        title: "最新",
        defaultTitleColor: "#000",
        image: "http://gtms03.alicdn.com/tps/i3/TB1LEn9MpXXXXaUXpXX9t7RGVXX-46-46.png",
        selectedImage: "http://gtms02.alicdn.com/tps/i2/TB1qysbMpXXXXcnXXXX9t7RGVXX-46-46.png",
        activeTitleColor:"#db3652"
      },
      {
        title: "购物车",
        defaultTitleColor: "#000",
        image: "http://gtms01.alicdn.com/tps/i1/TB1B0v5MpXXXXcvXpXX9t7RGVXX-46-46.png",
        selectedImage: "http://gtms04.alicdn.com/tps/i4/TB1NxY5MpXXXXcrXpXX9t7RGVXX-46-46.png",
        activeTitleColor:"#db3652",
        haslabel:true,
        labelName:"cart",
        label:5,
      },
      {
        title: "个人中心",
        defaultTitleColor: "#000",
        image: "http://gtms01.alicdn.com/tps/i1/TB1B0v5MpXXXXcvXpXX9t7RGVXX-46-46.png",
        selectedImage: "http://gtms04.alicdn.com/tps/i4/TB1NxY5MpXXXXcrXpXX9t7RGVXX-46-46.png",
        activeTitleColor:"#db3652"
      }
    ],
  }
}
</script>