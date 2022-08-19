<template>
  <div>
    <label class="label">歌曲名：</label>
    <a-input v-model:value="music" class="input" placeholder="music" />
    <br />
    <label class="label">musician:</label>
    <a-input v-model:value="musician" class="input" placeholder="musician此项为必填项" />
    <br />
    <label class="label">专辑:</label>
    <a-input v-model:value="album" class="input" placeholder="album" />
    <br />
    请先填写歌曲信息再点击上传
    <WarningTwoTone two-tone-color="#eb2f96" style="fontSize:30px;" />
  </div>
  <a-upload :customRequest="customRequest" :multiple="false" name="file" @change="handleChange" accept="audio/*">
    <a-button>
      <UploadOutlined />
      点这里上传
    </a-button>
  </a-upload>
</template>

<script>
import {message} from 'ant-design-vue'
import {UploadOutlined, WarningTwoTone} from '@ant-design/icons-vue'
import {ref} from 'vue'
import axios from "axios";

export default {
  name: "UploadMusic",
  components: {
    UploadOutlined,
    WarningTwoTone
  },
  setup() {
    const handleChange = info => {
      if (info.file.status === 'done') {
        message.success(`${info.file.name} 上传成功`);
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} 上传失败`);
      }
    };

    let music = ref("")
    let musician = ref("")
    let album = ref("")
    const customRequest = file => {
      // file 是上传的文件 其内容会在放在下面截图中
      // 后端需要接受的参数是 formData数据，
      const form = new FormData()

      form.append('uploadMusic', file.file)
      // uploadFile 我自己的接口
      axios.post("/user/music/upload", form, {
        params: {
        music: {
          userId: window.sessionStorage.getItem("userId"),
          musicName: music.value,
          musician: musician.value,
          album: album.value
      }
        }
      }).then(response => {
        if (response.data.code === 200) {
          // console.log(form)
          // 调用组件内方法, 设置为成功状态
          file.onSuccess(response, file.file)
          file.status = 'done'
        } else {
          file.onError()
          file.status = 'error'
        }
      })
    }


    return {
      handleChange,
      customRequest,
      music,
      musician,
      album
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