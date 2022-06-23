<template>
  <a-layout class="allLayout" >
    <a-layout-header class="header">
      <Title></Title>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="200" height="100%" style="background: #fff ">
        <a-menu mode="inline" :style="{ height: '100%', borderRight: 0 }">
          <a-sub-menu key="sub1">
            <template #title>
              <span>
                <user-outlined />
                用户管理
              </span>
            </template>
            <router-link to="/main/user_detail">
              <a-menu-item key="1">用户信息</a-menu-item>
            </router-link>
            <router-link to="/main/user_order">
              <a-menu-item key="2">用户订单</a-menu-item>
            </router-link>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <template #title>
              <span>
                <laptop-outlined />
                个人空间
              </span>
            </template>
            <router-link to="/main/music">
              <a-menu-item key="5">音乐</a-menu-item>
            </router-link>
            <router-link to="/main/note">
              <a-menu-item key="6">日志</a-menu-item>
            </router-link>
            <router-link to="/main/video">
              <a-menu-item key="7">视频</a-menu-item>
            </router-link>
            <router-link to="/main/chat">
              <a-menu-item key="8">在线聊天</a-menu-item>
            </router-link>
          </a-sub-menu>
          <a-sub-menu key="sub3">
            <template #title>
              <span>
                <notification-outlined />
                管理模块（仅管理员可见）
              </span>
            </template>
            <router-link to="/main/statistics">
              <a-menu-item key="9">统计数据</a-menu-item>
            </router-link>
            <!-- <a-menu-item key="10">option10</a-menu-item>
            <a-menu-item key="11">option11</a-menu-item>
            <a-menu-item key="12">option12</a-menu-item> -->
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }" ref="layoutContent">
          <router-view name="content"  ></router-view>
                                <a-drawer 
    v-model:visible="showMusicPane" 
    placement="bottom" 
    title="Basic Drawer"
    :getContainer="layoutContent"
    :destroyOnClose="false"
    >       
    <h1>测试通不过</h1>
       <div id="audioDiv">
      <span id="playing">正在播放：{{ nowMusic }}</span>
      <!-- <audio id="audio" ref="audio" :src="musicSrc" autoplay controls
             loop
             preload="auto"
             volume="0.2">该浏览器不支持audio属性
      </audio> -->
      </div>
    </a-drawer>
    
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>

</template>
<script>
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import { ref, computed,onMounted} from "vue";
import Title from "../routes/Title"
import store from '../store';

export default {
  name: "Main",
  components: { UserOutlined, LaptopOutlined, NotificationOutlined, Title },
  setup() {
    let showMusicPane = computed({get:()=> store.state.showMusicPane,
    set:newV=>{
      store.dispatch("switchMusicPane")

    }
    })
    // let allLayout=document.getElementById("allLayout")
    let layoutContent=ref()
    // const audio = ref()
    let nowMusic = computed({
      get:()=>{return store.state.nowMusic},
      set:newVal=>{
        store.dispatch("setnowMusic",newVal)
      }
    })
    return {
      // selectedKeys1: ref(['1']),
      // selectedKeys2: ref(['2']),
      // collapsed: ref(false),
      // openKeys: ref(['sub1']),
      showMusicPane,
      // allLayout
      layoutContent,
      nowMusic,
      // musicSrc
    }
  }
}
</script>
<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>