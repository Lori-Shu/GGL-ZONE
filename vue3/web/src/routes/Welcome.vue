<template>
  <div id="welcome">
    <span >欢迎使用GGL——ZONE</span>
  </div>
</template>

<script>
import {computed, ref} from "vue";
import store from "@/store";

export default {
  name: "Welcome",
  setup() {
    // const instance = getCurrentInstance()
    // const _this = instance.appContext.config.globalProperties
    // const flush = _this.$route.query.flush
    // console.log(flush)
    // let showLogin = computed(()=>store.state.showLogin)
    // let show = computed(()=>!showLogin.value)
    // console.log(_this.$route.authorized === "1")
    // nextTick(()=>{
    //   console.log("welcome---",window.sessionStorage.getItem("authorized"))
    // })
    // if (window.sessionStorage.getItem("authorized") === "1") {
    //   // console.log("welcome----",window.sessionStorage.getItem("authorized"))
    //   show.value = true
    // }
    return {
      // show,
    }
  }
}
</script>

<style scoped>
#welcome {
  color: darkorange;
  text-align: center;
  font-size: xx-large;
}
</style>