<template>
    <div :interceptBack="canGoBack" @onBack="onBack">
        <web :src="url" style="flex:1;" ref="webview" @pagefinish="onPageFinish"
        @receivedtitle="onReceivedtitle"/>
    </div>
</template>

<script>
const webview = weex.requireModule('webview');
const crazyDaily = weex.requireModule('crazyDaily');

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
    onReceivedtitle(data) {
      crazyDaily.setTitle(data.title);
    },
  },
};
</script>