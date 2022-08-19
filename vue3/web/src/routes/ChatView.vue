<template>
  <div class="chatView">
    <div class="status">
      <a-button block type="dashed">对方当前状态：{{ nowChatTarget.status }}</a-button>
    </div>
    <div class="list">
      <a-list :data-source="thisMessageData" item-layout="horizontal">
        <template #renderItem="{ item }">
          <a-list-item>
            <a-list-item-meta
                description="Ant Design, a design language for background applications, is refined by Ant UED Team"
            >
              <template #title>
                <a href="https://www.antdv.com/">{{ item.message }}</a>
              </template>
              <template #avatar>
                <a-avatar>{{ item.from }}</a-avatar>
              </template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </div>
    <div class="text-area">
      <a-textarea ref="textArea" v-model:value="messageValue" :allowClear="true" :rows="7" :showCount="true"
                  placeholder="按回车键发送" @pressEnter="sendMessage($event)"/>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import {inject, ref, watch} from "vue";
import store from "../store";
import myUtils from "../utils"

export default {
  name: "ChatView",
  setup() {
    let chatWebSocket = inject("chatWebSocket")
    let userId = ref(window.sessionStorage.getItem("userId"))
    let thisMessageData = computed(()=>store.state.existChat.get(nowChatTarget.value.userId))
    let nowChatTarget = inject("nowChatTarget")
    let messageValue = ref()
    let textArea = ref()
    const sendMessage = e => {
      if (messageValue.value !== "") {
          let temp={
            "target": nowChatTarget.value.userId,
            "message": messageValue.value,
            "from": userId.value,
            "createTime":myUtils.formatter("yyyy-MM-dd HH:mm:ss",new Date())
          }
          console.log(myUtils.formatter("yyyy-MM-dd HH:mm:ss",new Date()))
          chatWebSocket.send(JSON.stringify(temp))
          store.dispatch("addMessage",temp)
          console.log(messageValue.value)
          messageValue.value = ""
        console.log("得到dom元素", textArea.value)
        // console.log("传入的参数===",e)
        e.returnValue = false
        return false
      }
    }
    return {
      thisMessageData,
      messageValue,
      nowChatTarget,
      sendMessage,
      textArea
    }
  }
}
</script>

<style scoped>
.chatView {
  float: left;
  width: 70%;
  height: 700px;
  border: deepskyblue solid;
}

.list {
  width: 100%;
  height: 500px;
  border: darkorange solid;
  overflow: auto;
}
</style>