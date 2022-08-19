
<template>

  <div id="app">
    <a-config-provider :locale="zhCN">
      <router-view name="mainView"></router-view>
      <!-- <Title></Title>
      <SwitchPlace></SwitchPlace>
      <View></View> -->
      </a-config-provider>
  </div>
  
</template>

<script>
import zhCN from 'ant-design-vue/es/locale/zh_CN';
export default {
  name: "app",
  components: {},
  setup() {
    return {
      zhCN
    }
  }
}
</script>

<style>
html, body, #app {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
}
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}

.allLayout{
  min-height: 100%;
}
</style>
