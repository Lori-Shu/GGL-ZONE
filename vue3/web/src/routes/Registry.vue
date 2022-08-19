<template>
  <div id="registry">
    <label class="label">用户Id：</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input v-model="userId" type="text"/><br/>
    <label class="label">密码：</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input v-model="password"
                                                                                           type="text"/><br/>
    <label class="label">确认密码：</label>&nbsp;<input v-model="confirm" type="text"/><br/>
    <h5 v-if="!same" class="tips">确认密码和密码不相同</h5>
    <a-button type="primary" @click="registry">确认注册</a-button>
  </div>
</template>

<script>
import {ref} from "vue";
import axios from "axios";
import { computed } from "@vue/reactivity";
import { message } from "ant-design-vue";
import {router} from "../router";
import { use } from "echarts";

export default {
  name: "Registry",
  setup() {
    // const instance = getCurrentInstance()
    let userId = ref("")
    let password = ref("")
    let confirm = ref("")

    const registry=()=> {
      // console.log(user.userId.toString())
      // console.log(user.password.toString())
      // console.log(user.confirm.toString())
      // console.log(userId.value)
      // console.log(password.value)
      // console.log(confirm.value)
      router.push("/")
      if (userId.value !== "" && same && password.value !== "") {
        axios.post("/user/registry",{
          "userId":userId.value,
          "password":password.value
        }).then(response => {
          if (response.data.code === 200) {
            message.info("注册成功!请尝试登录")
            router.push("/")
          } else {
            message.error("注册失败",response.data.detail)
            console.log(response)
          }
        }).catch(err => {
          alert(err)
        })
      } else {
        alert("密码或用户名有问题，请修改后重试")
      }

    }
    const same=computed(()=>confirm.value===password.value)

    return {
      userId,
      password,
      confirm,
      registry,
      same
    }
  }
}
</script>

<style scoped>
#registry {
  color: black;
  font-size: x-large;
  text-align: center;
}
.tips{
  color: red;
}
</style>