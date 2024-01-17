<template>
  <div id="title">
    <!-- <div id="des">
    <h2>GGL-ZONE--{{ description }}</h2>
    </div> -->
    <div id="userController">
      <a-avatar size="large" :src="avatar">
    </a-avatar>
    {{userId}}
      <a-button type="primary" shape="round" style="left: 100px" @click="logout">登出</a-button>
    </div>
          &nbsp;
      <a-button type="primary" style="left: 1100px" @click="siwtchMusicPane">打开音乐台</a-button>
  </div>
</template>

<script>
import {UserOutlined} from "@ant-design/icons-vue";
import {computed, ref} from "vue";
import store from "@/store";
import {message} from "ant-design-vue";
import axios from "axios";
import { router } from "../router";


export default {
  name: "Title",
  components:{UserOutlined},
  setup() {
    // let description = "我的第一个网站"
    // let userId=ref("测试用户")
    let userId = computed(() => {
      return store.state.userDetail.userId
    })
    let avatar = computed(() => {
      return store.state.userDetail.avatar
    })
    // console.log("title测试avatar",user.value)
    // console.log("原数据", store.state.userId)
    let url="/server/logout"
    // let instance = getCurrentInstance()
    const logout = () => {
    if (userId.value !== "未登录") {
      axios.post(url).then(res=>{
        console.log("请求状态",res.status)
        if(res.data.code===200){
          window.sessionStorage.removeItem("token")
          window.sessionStorage.removeItem("authorized")
          window.sessionStorage.removeItem("userId")
          store.dispatch("setUserId","未登录")
          // console.log(window.sessionStorage.getItem("authorized"))
          // console.log("view---",_this.$route.authorized === "1")
          // message.info("登录成功！")
          // console.log(chatWebSocket)
          // chatWebSocket.send(JSON.stringify({"target": "2412045439", "message": "测试", "from": "大神"}))
          // store.dispatch("changeShowLogin")
          // instance.proxy.$router.push({path: `/`, query: {flush: `1`}})
          router.push("/")
          message.info("成功登出")
          return
        }
        message.info("服务端出现登出问题")
      }).catch(err=>{
      message.error(err)
      })
    }
    message.warn("未登录用户无法登出")
  }
  // let showMusicPane=computed(()=>state.showMusicPane)
    const siwtchMusicPane=()=>{
      store.dispatch("switchMusicPane")
      console.log("switch -visiable")
  }

    return {
      userId,
      avatar,
      // description,
      logout,
      siwtchMusicPane
    }
  }
}
</script>

<style scoped>
#userController{
  float: left;
}
</style>