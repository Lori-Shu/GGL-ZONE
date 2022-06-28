<template>
  <div id="playVideo">
    正在播放：{{ playingVideo }}<br/>
    <div id="video">
      <vue3VideoPlay  v-bind="options" ></vue3VideoPlay>
    </div>
  </div>
</template>

<script>

import { ref } from "vue";
import { router} from "../router/index"
export default {
  name: "PlayVideo",
  setup() {

    console.log(router.currentRoute.value.query.videoSrc)
    let src = ref(router.currentRoute.value.query.videoSrc)
    // console.log(src.value)
    let playingVideo = ref(router.currentRoute.value.query.playingVideo)
    let options = ref({
      width: "1280px",
      height:"720px",
      src: src.value,
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
  border: solid;
  text-align: center;
}

#video {
  border: solid;
  position: relative;
  left: 200px;
  /* height: 720px;
  width: 1280px; */
}
</style>