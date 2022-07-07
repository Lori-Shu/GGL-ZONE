<template>
  <div>
    <div class="animate__animated animate__backInLeft">
      <a-list :data-source="data" item-layout="horizontal">
        <template #renderItem="{ item }">
          <a-list-item @click="changeMusic(item)">
            <a-list-item-meta :description="`musician:`+item.musician+`\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0`
        +`album:`+item.album">
              <template #title>
                <a id="title" >{{ item.musicName }}</a>
              </template>
            </a-list-item-meta>
            <template #extra>
            <img
                alt="logo"
                height="70"
                src="../assets/音乐图标.jpeg"
                width="100"
            />
            </template>
          </a-list-item>
          <cloud-download-outlined @click="downloadMusic(item)" style="font-size:x-large "/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <DeleteTwoTone id="delete" twoToneColor="#eb2f96" @click="deleteMusic(item)"/>
        </template>
      </a-list>
    </div>
    <div>
      <a-pagination v-model:current="musicCurrent" :pageSize="5" :show-total="musicTotal => `总 ${musicTotal} 首歌`"
                    :total="musicTotal"
                    show-quick-jumper @change="onChange"/>
    </div>
  </div>
</template>

<script>
import {inject, nextTick,ref} from "vue";
import axios from "axios";
import {DeleteTwoTone,CloudDownloadOutlined} from "@ant-design/icons-vue";
import myDownLoad from "@/downLoad";
import { computed } from "@vue/reactivity";
import store from "../store";
import { message } from "ant-design-vue";

export default {
  name: "MusicList",
  components: {
    DeleteTwoTone,CloudDownloadOutlined
  },
  setup() {
    let data = computed(()=>store.state.musicList)
    let selectParam = inject("musicSelectParam")
    let musicCurrent = inject("musicCurrent")
    let musicTotal = inject("musicTotal")
    let selected = inject("musicSelected")
    let userId=ref(window.sessionStorage.getItem("userId"))
    nextTick(() => {
      axios.post("/user/music/select_page", {
        userId:userId.value
      },{
        params: {
          pageNumber: 1,
          pageSize:5
        }
      }).then(response => {
        console.log(response.data)
        if (response.data.code===200) {
          store.dispatch("setMusicList",response.data.result.list)
          console.log(store.state.musicList)
          // console.log(response.data.result.pageList)
          // musicCurrent.value = 1
          musicTotal.value = response.data.result.total
          // console.log(response.data.result.total)
        }
      })
    })

    const onChange = pageNumber => {
      // console.log(props.selected)
        axios.post("/user/music/select_page", {
          userId: userId.value,
          music: selectParam.music,
          musician: selectParam.musician,
          album: selectParam.album,
        },{
          params: {
            pageNumber,
            pageSize:5
          }
        }).then(response => {
          if (response.data.code === 200) {
            store.dispatch("setMusicList", response.data.result.list)
            // console.log(response.data.result.pageList)
            // console.log(responsedata)
            musicTotal.value = response.data.result.total
            // console.log(response.data.result.total)
            // musicCurrent.value = pageNumber
            // console.log( response.data["result"])
            // console.log(responsedata)
          }

        }).catch(err => {
          alert(err)
        })
      
    }
    let nowMusic = computed({
      get:()=>{return store.state.nowMusic},
      set:newVal=>{
        store.dispatch("setnowMusic",newVal)
      }
    })
    let src =  computed({
      get:()=>{return store.state.musicSrc},
      set:newVal=>{
        store.dispatch("setMusicSrc",newVal)
      }
    })
    const changeMusic = item => {
      console.log(item.src)
      store.dispatch("setNowMusic",item)
      store.dispatch("setMusicSrc", item.src)
      store.dispatch("switchAudioPlaying",true)
      let myAudio = store.state.myAudio
      myAudio.load()
      setTimeout(()=>{
        myAudio.play()
        console.log("等一会儿加载")
        },100)
      
    }
    const deleteMusic = music => {
      axios.post("/user/music/delete", 
        music
      ).then(response => {
        if (response.data.code === 200) {
          if (response.data.detail === "删除音乐成功") {
            message.info("删除音乐成功")
          }
        }
      }).catch(err => {
        alert(err)
      })
    }
    const downloadMusic= item=>{
      myDownLoad("user/download/music",item)
    }
    return {
      data,
      onChange,
      musicCurrent,
      musicTotal,
      changeMusic,
      deleteMusic,
      downloadMusic,
    }
  }
}
</script>

<style scoped>
/*#album{*/
/*  color: darkorange;*/
/*  font-size: xx-large;*/
/*  position: fixed;*/
/*  left: 70%;*/
/*}*/
/*#musician{*/
/*  color: darkorange;*/
/*  font-size: xx-large;*/
/*  position: fixed;*/
/*  left: 20%;*/
/*}*/
#title {
  color: deepskyblue;
  font-size: x-large;
}

#delete {
  font-size: x-large;
}
</style>