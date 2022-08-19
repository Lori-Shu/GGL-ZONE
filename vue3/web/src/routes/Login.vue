<template>
  <div id="login">
    <div id="loginArea">
      <h2 style="color: deepskyblue">用户登录</h2><br/>
      <label class="label">用户id:  </label><input v-model="userId" type="text"/><br/><br/>
      <label class="label">密码：   </label><input v-model="password" type="text"/><br/><br/>
      <a-button type="primary" @click="login">登录</a-button>
      &nbsp;&nbsp;&nbsp;
      <router-link to="/registry">
        <a-button type="primary" >注册</a-button>
        </router-link>
    </div>
  </div>
</template>
<script>
import {computed, getCurrentInstance, onMounted, provide, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";
import {router} from "../router/index";


export default {
  name: "Login",
  setup() {
    let userId = ref("")
    let password = ref("")
    // let instance = getCurrentInstance()
    // let friends = ref([])
    
    // const ctx = instance.appContext

   
    
    const login = () => {
        // router.push("/main")
      axios.post("server/login", {
        "userId": userId.value,
        "password": password.value
      }).then(response => {
        if (response.data.code === 200) {
        //   store.dispatch("changeShowLogin")
          window.sessionStorage.setItem("token", response.headers.token)
          window.sessionStorage.setItem("authorized", "1")
          window.sessionStorage.setItem("userId", userId.value)
          store.dispatch("setUserId", userId.value)
          store.dispatch("getUserDetail")
          // console.log(window.sessionStorage.getItem("authorized"))
          // console.log("view---",_this.$route.authorized === "1")
          message.info("登录成功！")
          // console.log(chatWebSocket)
          // chatWebSocket.send(JSON.stringify({"target": "2412045439", "message": "测试", "from": "大神"}))
        //   instance.proxy.$router.push({path: `/`, query: {flush: `1`}})
        router.push("/main")
          return
        }
        message.error("登录失败，请检查用户名和密码")
        console.log(response.data)
        // console.log(response.data)

      }).catch(err => {
        alert(err)
      })
    }

    return {
      userId,
      password,
      login,
    }
  }
}

</script>
<style scoped>
#view {
  height: 90%;
  width: 90%;
  float: right;
  background-image: url("../assets/background.jpg");
  background-size: cover;
  /*background-color: saddlebrown;*/
}

.label {
  display: inline-block;
  width: 5%;
  color: coral;
  font-size: 20px;
}

#loginArea {
  text-align: center;
}
</style>