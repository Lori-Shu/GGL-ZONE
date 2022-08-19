<template>
  <a-layout class="allLayout">
    <a-layout-header class="header">
      <Title></Title>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="200" height="100%" style="background: #fff ">
        <a-menu mode="inline" :style="{ height: '100%', borderRight: 0 }">
          <a-sub-menu key="sub1">
            <template #title>
              <span>
                <user-outlined />
                用户管理
              </span>
            </template>
            <router-link to="/main/user_detail">
              <a-menu-item key="1">用户信息</a-menu-item>
            </router-link>
            <router-link to="/main/user_order">
              <a-menu-item key="2">用户订单</a-menu-item>
            </router-link>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <template #title>
              <span>
                <laptop-outlined />
                个人空间
              </span>
            </template>
            <router-link to="/main/music">
              <a-menu-item key="5">音乐</a-menu-item>
            </router-link>
            <router-link to="/main/note">
              <a-menu-item key="6">日志</a-menu-item>
            </router-link>
            <router-link to="/main/video">
              <a-menu-item key="7">视频</a-menu-item>
            </router-link>
            <router-link to="/main/chat">
              <a-menu-item key="8">在线聊天</a-menu-item>
            </router-link>
          </a-sub-menu>
          <a-sub-menu key="sub3">
            <template #title>
              <span>
                <notification-outlined />
                管理模块（仅管理员可见）
              </span>
            </template>
            <router-link to="/main/statistics">
              <a-menu-item key="9">统计数据</a-menu-item>
            </router-link>
            <!-- <a-menu-item key="10">option10</a-menu-item>
            <a-menu-item key="11">option11</a-menu-item>
            <a-menu-item key="12">option12</a-menu-item> -->
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
          ref="layoutContent">
          <router-view name="content"></router-view>
          <a-drawer v-model:visible="showMusicPane" placement="bottom" title="音乐台" :getContainer="layoutContent"
            :destroyOnClose="false">
            <!-- <h1>测试通不过</h1> -->
            <div class="audioDiv">
              <span id="playing">正在播放：{{ nowMusic.musicName }}</span>
              <div>
                <left-circle-two-tone class="audioController" two-tone-color="#52c41a" @click="preMusic" />
                <play-circle-two-tone class="audioController" two-tone-color="#52c41a" v-show="!audioPlaying"
                  @click="switchAuidoPlaying('play')" />
                <pause-circle-two-tone class="audioController" two-tone-color="#52c41a" v-show="audioPlaying"
                  @click="switchAuidoPlaying('pause')" />
                <right-circle-two-tone class="audioController" two-tone-color="#52c41a" @click="nextMusic" />
                <a-progress :stroke-color="{
                  from: '#108ee9',
                  to: '#87d068',
                }" status="active" :strokeWidth="20" style="width: 800px; border: solid;" :percent="musicPercent"
                  :format="percent=>musicProgress" />
                <SoundTwoTone class="soundIcon" @click="setShowVolumn"></SoundTwoTone>
                <a-slider v-show="showVolumn" v-model:value="volumn" :range="false" :min="0" :max="100" 
                class="volumSlider" />


              </div>
            </div>
          </a-drawer>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>

</template>
<script>
import {
  UserOutlined, LaptopOutlined, NotificationOutlined,
  LeftCircleTwoTone, PlayCircleTwoTone, RightCircleTwoTone,
  PauseCircleTwoTone,SoundTwoTone
} from '@ant-design/icons-vue';
import { ref, computed, watch } from "vue";
import Title from "../routes/Title"
import store from '../store';
import myutils from "../utils/index"

export default {
  name: "Main",
  components: {
    UserOutlined, LaptopOutlined, NotificationOutlined, Title, LeftCircleTwoTone, PlayCircleTwoTone, RightCircleTwoTone,
    PauseCircleTwoTone,SoundTwoTone
  },
  setup() {
    let showMusicPane = computed({
      get: () => store.state.showMusicPane,
      set: newV => {
        store.dispatch("switchMusicPane")

      }
    })
    // let allLayout=document.getElementById("allLayout")
    let layoutContent = ref()
    // const audio = ref()
    let nowMusic = computed({
      get: () => { return store.state.nowMusic },
      set: newVal => {
        store.dispatch("setnowMusic", newVal)
      }
    })
    let audioPlaying = computed(() => store.state.audioPlaying)
    let myAudio = store.state.myAudio
    myAudio.volume=0.2
    const switchAuidoPlaying = (command) => {
      
      if (command === "play") {
        store.dispatch("switchAudioPlaying",true)
        myAudio.play()
      } else {
        store.dispatch("switchAudioPlaying",false)
        myAudio.pause()
      }
    }
    let musicPercent = ref(0)
    let musicProgress=ref("")
    setInterval(() => {
      console.log("触发时间更新事件")
      musicPercent.value = myAudio.currentTime*100 / myAudio.duration
      let cur = myutils.getTime(myAudio.currentTime)
      let dur = myutils.getTime(myAudio.duration)
      musicProgress.value = cur[0] + ":" + cur[1] + ":" + cur[2] + "/" + dur[0] + ":" + dur[1] + ":" + dur[2]
      // console.log("volumn:",volumn.value)
    }, 1000)
    const preMusic = () => {
      let list = store.state.musicList
      let index = list.indexOf(store.state.nowMusic)
      if (index > 0) {
        store.dispatch("setNowMusic", list[index - 1])
        store.dispatch("setMusicSrc", list[index - 1].src)
        store.dispatch("switchAudioPlaying", true)
        let myAudio = store.state.myAudio
        myAudio.load()
        console.log("切换上一曲")
        setTimeout(() => {
          myAudio.play()
        }, 100)
      }
    }
    const nextMusic = () => {
      let list = store.state.musicList
      let index = list.indexOf(store.state.nowMusic)
      if (index < list.length - 1) {
        store.dispatch("setNowMusic", list[index + 1])
        store.dispatch("setMusicSrc", list[index + 1].src)
        store.dispatch("switchAudioPlaying", true)
        let myAudio = store.state.myAudio
        myAudio.load()
        console.log("切换下一曲")
        setTimeout(() => {
          myAudio.play()
        }, 100)
      }
    }
    let showVolumn=computed(()=>store.state.showVolumn)
    const setShowVolumn = () => {
      if (showVolumn.value) {
        store.dispatch("setShowVolumn", false)
        return
      }
      store.dispatch("setShowVolumn",true)
    }
    let volumn = ref(20)
    watch(volumn,
      (newVal, preVal) => {
        myAudio.volume = newVal / 100
        console.log("volumn", myAudio.volume)
    })

     
    return {
      // selectedKeys1: ref(['1']),
      // selectedKeys2: ref(['2']),
      // collapsed: ref(false),
      // openKeys: ref(['sub1']),
      showMusicPane,
      // allLayout
      layoutContent,
      nowMusic,
      // musicSrc
      audioPlaying,
      switchAuidoPlaying,
      musicPercent,
      musicProgress,
      preMusic,
      nextMusic,
      setShowVolumn,
      showVolumn,
      volumn
    }
  }
}
</script>
<style>



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

.audioController {
  font-size: 70px;
  border: solid;

}
.soundIcon{
  border: solid;
  /* float: left; */
  position: relative;
  left: 150px;
  font-size: 20px;
}
.volumSlider{
  position: relative;
  left: 1170px;
  border: solid;
  width: 200px;
}
.audioDiv{
  border: solid;
}
</style>