<template>
    <div>
        <richText :richtext="rich" :style="{width:'750px',height:'200px'}"></richText>
        <tabPager :titledata="datas" style="flex:1;">

        </tabPager>
    </div>
</template>

<script>
const a = '<h3>Test1</h3><img src="http://image.zhangxinxu.com/image/study/s/s128/mm3.jpg"/>';

export default{
  data() {
    return {
      datas: ['Android', 'iOS', '前端'],
      rich: a,
    };
  },
};
</script>