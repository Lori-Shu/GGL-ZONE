<template>
  <router-link to="/main/upload_video">
    <a-button style="float: right" type="primary">上传视频</a-button>
  </router-link>
  <br />
  <div id="selectArea">
    视频名：
    <a-input-search v-model:value="selectName" enter-button placeholder="video_name" style="width:20%"
      @search="onSearch" />
  </div>
  <br />
  <div class="animate__animated animate__zoomInLeft">
    <a-list id="list" :data-source="data" :grid="{gutter: 16, column: 5}">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-card :title="item.videoName" hoverable>
            <template #cover>
              <img height="100" src='../assets/视频图标.jpeg' @click="playVideo(item)">
            </template>
            <span>作者：{{ item.videoAuthor }}</span><br />
            <a-collapse :bordered="true">
              <template #expandIcon="{ isActive }">
                <caret-right-outlined :rotate="isActive ? 100 : 0" />
              </template>
              <a-collapse-panel :style="customStyle" header="视频简介">
                <p>{{ item.introduction }}</p>
              </a-collapse-panel>
              <DeleteTwoTone id="delete" twoToneColor="#eb2f96" @click="deleteVideo(item.uuid,item.src)" />
              <cloud-download-outlined @click="downloadVideo(item)" style="font-size:x-large " />
            </a-collapse>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </div>
  <div>
    <a-pagination v-model:current="current" :pageSize="12" :show-total="total => `总 ${total} 个视频`" :total="total"
      show-quick-jumper @change="onChange" />
  </div>
</template>
<script>
import { nextTick, ref} from "vue";
import axios from "axios";
import {CaretRightOutlined, DeleteTwoTone,CloudDownloadOutlined} from "@ant-design/icons-vue";
import "animate.css"
import { router } from "../router/index"
import { downloadMovie } from "../downLoad/index";

export default {
  name: 'Video',
  components: {
    DeleteTwoTone,
    CaretRightOutlined,
    CloudDownloadOutlined
  },
  setup() {
    let data = ref([])
    let selectName = ref('')
    let total = ref(0)
    let current = ref(1)
    nextTick(() => {
      axios.post("/user/video/select_page",{
        userId: window.sessionStorage.getItem("userId"),
          videoName: selectName.value,
      }, {
        params: {
          pageNumber: 1,
          pageSize:5
        }
      }).then(response => {
        console.log(response.data)
        if(response.data.code===200){
          console.log(response.data.result)
        data.value = response.data.result.list
        total.value = response.data.result.total
        }
      }).catch(err => {
        alert(err)
      })
    })
    const onSearch = () => {
      axios.post("/user/video/select_page",{
        userId: window.sessionStorage.getItem("userId"),
        videoName: selectName.value,
      }, {
        params: {
          pageNumber: 1,
          pageSize:5
        }
      }).then(response => {
        data.value = response.data.result.list
        total.value = response.data.result.total
        // current.value = 1
      }).catch(err => {
        alert(err)
      })
    }
    const onChange = pageNumber => {
      axios.post("/user/video/select_page", {
        params: {
          userId: window.sessionStorage.getItem("userId"),
          videoName: selectName.value,
          pageNumber
        }
      }).then(response => {
        data.value = response.data.result.list
        total.value = response.data.result.total
        // current.value = pageNumber
      }).catch(err => {
        alert(err)
      })
    }
    const playVideo = video => {
      router.push({path: '/main/play_video', query: {playingVideo: video.videoName, videoSrc: video.src}})
    }
    const customStyle =
        'background: #f7f7f7;border-radius: 4px;margin-bottom: 24px;border: 0;overflow: hidden';
    const deleteVideo = (uuid, src) => {
      if (confirm("确定要删除此条视频吗")) {
        axios.get("/user/delete_video", {
          params: {
            src,
            uuid
          }
        }).then(response => {
          if (response.data.detail === "删除成功") {
            alert("删除成功")
            return
          }
          alert(response.data.detail)
        }).catch(err => {
          alert(err)
        })
      }
    }
    const downloadVideo = item => {
      downloadMovie(item)
    }
    return {
      data,
      selectName,
      onSearch,
      onChange,
      current,
      total,
      playVideo,
      customStyle,
      deleteVideo,
      downloadVideo
    }
  }
}
</script>

<style scoped>


#delete {
  font-size: x-large;
}
</style>