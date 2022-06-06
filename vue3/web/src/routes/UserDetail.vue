<template>
 <a-descriptions title="用户信息" layout="vertical" bordered>
    <a-descriptions-item label="用户名">{{userId}}</a-descriptions-item>
    <a-descriptions-item label="头像"><a-avatar :src="avatar"></a-avatar></a-descriptions-item>
    <a-descriptions-item label="Automatic Renewal">YES</a-descriptions-item>
    <a-descriptions-item label="注册时间">{{createTime}}</a-descriptions-item>
    <a-descriptions-item label="Usage Time" :span="2">2019-04-24 18:00:00</a-descriptions-item>
    <a-descriptions-item label="auth" >{{auth}}</a-descriptions-item>
  </a-descriptions>
</template>
<script>
import { computed } from '@vue/reactivity'
import store from '../store/index'

export default{
    name:"UserDetail",
    setup(){
        return{
            userId:computed(()=>store.state.userDetail.userId),
            avatar:computed(()=>store.state.userDetail.avatar),
            createTime:computed(()=>store.state.userDetail.createTime),
            auth:computed(()=>store.state.userDetail.auth),
        }
    }
}
</script>