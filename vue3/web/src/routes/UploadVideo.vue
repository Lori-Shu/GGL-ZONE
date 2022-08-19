<template>
  <div>
    <label class="label">视频名：</label>
    <a-input v-model:value="videoName" class="input" placeholder="videoName"/>
    <br/>
    <label class="label">作者:</label>
    <a-input v-model:value="videoAuthor" class="input" placeholder="author此项为必填项"/>
    <br/>
    <label class="label">主要内容简介:</label>
    <a-input v-model:value="introduction" class="input" placeholder="introduction"/>
    <br/>
    请先填写视频信息再点击上传
    <WarningTwoTone/>
  </div>
  <a-upload
      :customRequest="customRequest"
      :multiple="false"
      name="file"
      @change="handleChange"
      :beforeUpload="file=>beforeUpload(file)"
      accept="video/*"
  >
    <a-button>
      <UploadOutlined/>
      点这里上传
    </a-button>
  </a-upload>
</template>

<script>
import {UploadOutlined, WarningTwoTone} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";
import {ref} from "vue";
import axios from "axios";

export default {
  name: "UploadVideo",
  components: {
    WarningTwoTone,
    UploadOutlined
  },
  setup() {
    const handleChange = info => {
      if (info.file.status === 'done') {
        message.success(`${info.file.name} 上传成功`);
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} 上传失败`);
      }
    };

    let videoName = ref("")
    let videoAuthor = ref("")
    let introduction = ref("")
    let url = "/user/video/upload"
    const customRequest = file => {
      // file 是上传的文件 其内容会在放在下面截图中
      // 后端需要接受的参数是 formData数据，
      // if(videoAuthor.value===""){
      //   message.error("必填项未填写！")
      //   file.status='error'
      //   return
      // }
      const form = new FormData()
      form.append('uploadVideo', file.file)
      // uploadFile 我自己的接口
      axios.post(url, form, {
        params: {
          video:{
          userId: window.sessionStorage.getItem("userId"),
          videoName: videoName.value,
          videoAuthor: videoAuthor.value,
            introduction: introduction.value
          }
        }
      }).then(response => {
        if (response.data.code === 200) {
          if (response.data.detail === "上传视频成功") {
            // console.log(form)
            // 调用组件内方法, 设置为成功状态
            file.onSuccess(response, file.file)
            file.status = 'done'
          } else {
            file.onError()
            file.status = 'error'
          }
        }
      })
    }
    const beforeUpload = (file) => {
      if (videoAuthor.value === "") {
        message.error("必填项未填写！")
        file.status = 'error'
        return false
      }
      return true
    }
    return {
      handleChange,
      videoName,
      videoAuthor,
      introduction,
      customRequest,
      beforeUpload,
    }
  }
}
</script>

<style scoped>
.label {
  display: inline-block;
  width: 10%;
}

.input {
  width: 20%;
}
</style>