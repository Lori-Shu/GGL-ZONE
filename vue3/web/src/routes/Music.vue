<template>
  <div id="music">
    <router-link to="/main/upload_music">
      <a-button style="float: right" type="primary">上传音乐</a-button>
    </router-link>
    <br/><br/>
    <div id="selectArea">
      名称：
      <a-input v-model:value="selectParam.music" placeholder="music" style="width:20%"/>
      musician：
      <a-input v-model:value="selectParam.musician" placeholder="musician" style="width:20%"/>
      专辑：
      <a-input-search
          v-model:value="selectParam.album"
          enter-button
          placeholder="album"
          style="width:20%"
          @search="onSearch"
      />
    </div>
    <br/>
    <MusicList></MusicList>
    <div id="audioPlayer"></div>
    <div id="audiodiv">
      <span id="playing">正在播放：{{ now }}</span>
      <audio id="audio" ref="audio" :src="src" autoplay controls
             loop
             preload="auto">该浏览器不支持audio属性
      </audio>
    </div>
  </div>
</template>

<script>
import MusicList from "@/routes/MusicList";
import {nextTick, provide, reactive, ref} from "vue";
import axios from "axios";


export default {
  name: "Music",
  components: {MusicList},
  setup() {
    const audio = ref()
    nextTick(() => {
      audio.value.volume = 0.2
    })
    let data = ref()
    provide("musicData", data)
    let selectParam = reactive({music: '', musician: '', album: ''})
    provide("musicSelectParam", selectParam)
    let current = ref(1)
    provide("musicCurrent", current)
    let total = ref(0)
    provide("musicTotal", total)
    let selected = ref("0")
    provide("musicSelected", selected)
    let now = ref("无")
    provide("nowMusic", now)
    let src = ref("")
    provide("musicSrc", src)
    let userId=ref(window.sessionStorage.getItem("userId"))
    let onSearch = () => {
      axios.post("/user/music/select_page", {
          userId: userId.value,
          music: selectParam.music,
          musician: selectParam.musician,
          album: selectParam.album,
      },{
        params: {
          pageNumber: 1,
          pageSize:5
        }
      }).then(response => {
        // console.log(listdata)
        data.value = response.data.result
        current.value = 1
        if (selectParam.music === "" && selectParam.musician === "" && selectParam.album === "") {
          selected.value = "0"
          alert("请输入查询参数，已查询所有")
        } else {
          selected.value = "1"
        }
      }).catch(err => {
        // console.log(selectParam.album)
        alert(err)
      })
    }
    
    return {
      audio,
      selectParam,
      onSearch,
      src,
      now

    }
  }
}
</script>

<style scoped>
#audio {
  left: 10%;
  bottom: 0;
  position: fixed;
  width: 90%;
}

#selectArea {
  color: chartreuse;
  font-size: x-large;
}

#playing {
  left: 10%;
  bottom: 7%;
  position: fixed;
}
</style>