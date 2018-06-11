<template>
    <div>
        <web :src="url" style="flex:1;"/>
    </div>
</template>

<script>
export default{
  data() {
    return {
      url: 'http://www.baidu.com/',
    };
  },
};
</script>