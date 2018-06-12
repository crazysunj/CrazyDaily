<template>
    <div :interceptBack="canGoBack" @onBack="onBack">
        <web :src="url" style="flex:1;" ref="webview" @pagefinish="onPageFinish"/>
    </div>
</template>

<script>
const webview = weex.requireModule('webview');

export default{
  data() {
    return {
      url: 'https://www.baidu.com/',
      canGoBack: true,
    };
  },
  methods: {
    onBack() {
      const self = this;
      webview.goBack(self.$refs.webview);
    },
    onPageFinish(data) {
      const self = this;
      self.canGoBack = data.canGoBack;
    },
  },
};
</script>