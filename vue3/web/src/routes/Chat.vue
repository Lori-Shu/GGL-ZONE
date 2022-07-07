<template>
  <div id="chat" class="animate__animated animate__bounceInLeft">
    <div class="menu">
      <a-menu v-model:openKeys="openKeys" v-model:selectedKeys="selectedKeys" mode="inline" style="width: 300px">
        <a-sub-menu key="myFriend">
          <template #icon>
            <MailOutlined />
          </template>
          <template #title>我的站友&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

          </template>
          <a-button type="primary" @click="refresh">刷新状态</a-button>
          <a-menu-item-group key="g1" title="全部人">
            <template #icon>
              <QqOutlined />
            </template>
            <!--          <template #title>Item 1</template>-->
            <a-menu-item v-for="item in friends" :key="item" @click="handleClick(item)">
              {{ item.userId }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态：{{ item.status }}
            </a-menu-item>
          </a-menu-item-group>
          <!--        <a-menu-item-group key="g2" title="Item 2">-->
          <!--          <a-menu-item key="3">Option 3</a-menu-item>-->
          <!--          <a-menu-item key="4">Option 4</a-menu-item>-->
          <!--        </a-menu-item-group>-->
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <template #icon>
            <AppstoreOutlined />
          </template>
          <template #title>好友管理</template>
          <a-menu-item key="add" @click="showDrawer=!showDrawer">添加好友</a-menu-item>
          <a-menu-item key="delete" @click="deleteFriend">删除好友</a-menu-item>
          <!--        <a-sub-menu key="sub3" title="Submenu">-->
          <!--          <a-menu-item key="7">Option 7</a-menu-item>-->
          <!--          <a-menu-item key="8">Option 8</a-menu-item>-->
          <!--        </a-sub-menu>-->
        </a-sub-menu>
        <!--      <a-sub-menu key="sub4">-->
        <!--        <template #icon>-->
        <!--          <SettingOutlined />-->
        <!--        </template>-->
        <!--        <template #title>Navigation Three</template>-->
        <!--        <a-menu-item key="9">Option 9</a-menu-item>-->
        <!--        <a-menu-item key="10">Option 10</a-menu-item>-->
        <!--        <a-menu-item key="11">Option 11</a-menu-item>-->
        <!--        <a-menu-item key="12">Option 12</a-menu-item>-->
        <!--      </a-sub-menu>-->
      </a-menu>
    </div>
    <ChatView v-if="showChatView"></ChatView>
    <a-drawer v-model:visible="showDrawer" :closable="true" placement="right" :title="drawerTitle">
      <a-input-search v-model:value="searchValue" placeholder="请输入要查询用户的id" style="width: 200px" @search="onSearch"
        v-if="showSearch" />
      <a-menu v-if="showFriendList" mode="inline" style="width: 300px">
        <a-menu-item-group key="g1" title="全部人">
          <template #icon>
            <QqOutlined />
          </template>
          <a-menu-item v-for="item in friends" :key="item" @click="handleDelete(item)">
            {{ item.userId }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态：{{ item.status }}
          </a-menu-item>
        </a-menu-item-group>
      </a-menu>
      <a-modal v-model:visible="showConfirm" :title="myConfirmTitle" @ok="handleOk">
        <p>{{ friendMsg }}</p>
      </a-modal>
    </a-drawer>
  </div>
</template>

<script>
import {AppstoreOutlined, MailOutlined, QqOutlined,} from '@ant-design/icons-vue';
import {inject, provide, reactive, ref, watch} from "vue";
import ChatView from "@/routes/ChatView";
import useWebSocket from "@/ws";
import axios from "axios";
import {message} from "ant-design-vue";
import { computed } from '@vue/reactivity';
import store from '../store';

export default {
  name: "Chat",
  components: {MailOutlined, QqOutlined, AppstoreOutlined, ChatView},
  setup() {
    const selectedKeys = ref([]);
    const openKeys = ref([]);
    let showChatView = ref(false)
    let nowChatTarget = ref()
    provide("nowChatTarget", nowChatTarget)
    let friends = ref([])
    let userId = ref(window.sessionStorage.getItem("userId"))
    let token = window.sessionStorage.getItem("token")
    const url = "ws://localhost:8999/websocket/" + userId.value + "?token=" + token
    const handleMessage = e => {
      console.log(e)
      let commonMessage = JSON.parse(e.data)
      console.log(commonMessage)
      console.log(commonMessage.target)
      if (commonMessage.from === "system") {
        console.log("friends在获得message后的状态===", friends.value)
        for (let i = 0; i < friends.value.length; ++i) {
          let item = friends.value[i]
          // console.log(item)
          if (item.userId === commonMessage.message) {
            if(commonMessage.target==="online"){
            friends.value[i].status = "在线"
            }
            if(commonMessage.target==="offline"){
            friends.value[i].status = "离线"
            }
            console.log("更改了status", friends.value[i])
          }
        }
        // friends.value.forEach((item,index)=>{
        //
        // })
        // chatWebSocket.send(JSON.stringify({"target":"大神","message":"hello world"}))
        return
      }
      store.dispatch("addMessage",commonMessage)
    }
    let chatWebSocket = useWebSocket(url, handleMessage)
    provide("chatWebSocket", chatWebSocket)
      // console.log("进不来？")
        
    const handleClick = item => {
      // console.log('click', e);
      // const (messageData+e.)=ref([])
      // provide("messageData",messageData)
      nowChatTarget.value = item
      
      store.dispatch("addChat",item.userId)
      // console.log(store.state.existChat)
      showChatView.value = true
    }
    const getFriends = () => {
      return new Promise((resolve,reject)=>{
        let formData=new FormData()
        formData.append("userId",userId.value)
      axios.post("/user/chat/select_friends",formData).then(response => {
        console.log("搜索朋友的userId", userId)
        if (response.data.code === 200) {
          resolve(response.data.result)
          console.log("friends初始化", friends.value)
        }
      }).catch(err => {
        reject(err)
        alert(err)
      })
      })
    }
    const getFriendsPromise=()=>{
        Promise.all([getFriends()]).then(([result])=>{
          friends.value.length=0
          result.forEach(element => {
            friends.value.push(reactive({"id":element.id,"userId":element.friendId,"status":"离线"}))
          })
          refresh()
        })
    }
    const refresh = () => {
      // console.log('titleClick', e);
        chatWebSocket.send(JSON.stringify({target: "online", message: userId.value, from: "system"}))
        console.log("刷新消息已发送")
    }
    watch(
        () => openKeys.value,
        (newVal,preVal) => {
          console.log(preVal)
          console.log(newVal)
          for(let i=0;i<preVal.length;++i){
            if(preVal[i]==="myFriend"){
              return
            }
          }
          newVal.forEach(ele=>{
            if(ele==="myFriend"){
              getFriendsPromise()
            }
          })
        }
          )
    let showDrawer = ref(false)
    let searchValue = ref()
    let friendMsg = ref()
    const onSearch = () => {
      let form=new FormData()
      form.append("userId",searchValue.value)
      axios.post("/user/chat/select_stranger",form).then(response => {
        friendMsg.value = response.data.detail
        // message.info(response.data)
        if (response.data.code === 200) {
          showConfirm.value = true
          myConfirmTitle.value="添加好友提示"
          return
        }
        message.info(response.data.detail)

      }).catch(err => {
        alert(err)
      })
    }
    let showConfirm = ref(false)
    const handleOk = () => {
      if (friendMsg.value === "查询用户存在,是否添加为好友") {
        axios.post("/user/chat/add", {
            userId: userId.value,
            friendId: searchValue.value
          }).then(response => {
          showConfirm.value = false
          // message.info(response.data)
          if (response.data.code === 200) {
            message.info("添加好友成功！")
            friends.value.push({userId: searchValue.value, status: "离线"})
            chatWebSocket.send(JSON.stringify({target: "online", message: userId, from: "system"}))
            console.log("刷新消息已发送")
            return
          }
          message.info("添加好友失败，请联系攻城狮或等待修复")
        }).catch(err => {
          alert(err)
        })
        return
      }
      if(myConfirmTitle.value==="删除好友提示"){
        axios.post("/user/chat/delete",{
          id:friendToDelete.value.id,
          userId:userId.value,
          friendId:friendToDelete.value.userId
        })
        .then(res=>{
          if(res.data.code===200){
            showConfirm.value=false
            message.info("删除成功")
          }
        })
      }
    }
    let drawerTitle=ref("添加好友")
    let showSearch=ref(true)
    let showFriendList=ref(false)
    const deleteFriend=()=>{
      showDrawer.value=true
      drawerTitle.value="选择要删除好友"
      showSearch.value=false
      showFriendList.value=true
    }
    let myConfirmTitle=ref()
    let friendToDelete=ref()
    const handleDelete=item=>{
      friendToDelete.value=item
      showConfirm.value=true;
      myConfirmTitle.value="删除好友提示"
      friendMsg.value="确定？"

    }
    return {
      selectedKeys,
      openKeys,
      handleClick,
      friends,
      showChatView,
      showDrawer,
      searchValue,
      onSearch,
      showConfirm,
      friendMsg,
      handleOk,
      refresh,
      deleteFriend,
      showSearch,
      drawerTitle,
      showFriendList,
      handleDelete,
      myConfirmTitle
    }
  }
}
</script>

<style scoped>
.menu {
  width: 20%;
  float: left;
}
</style>