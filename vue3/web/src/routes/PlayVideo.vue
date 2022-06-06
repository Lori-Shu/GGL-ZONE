<template>
  <div id="playVideo">
    正在播放：{{ playingVideo }}<br/>
    <div id="video">
      <vue3VideoPlay  v-bind="options" ></vue3VideoPlay>
    </div>
  </div>
</template>

<script>

import {getCurrentInstance, ref} from "vue";
export default {
  name: "PlayVideo",
  setup() {
    const instance = getCurrentInstance()
    const _this = instance.appContext.config.globalProperties
    console.log(_this.$route.query.videoSrc)
    let src = ref(_this.$route.query.videoSrc)
    // console.log(src.value)
    let playingVideo = ref(_this.$route.query.playingVideo)
    let options=ref({
      src:src.value,
      control:true
    })
    return {
      src,
      playingVideo,
      options
    }
  }
}
</script>

<style scoped>
#playVideo {
  text-align: center;
}

#video {
  height: 720px;
  width: 1280px;
}
</style>