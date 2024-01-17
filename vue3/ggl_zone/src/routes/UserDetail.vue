<template>
    <a-descriptions title="用户信息" layout="vertical" bordered>
        <a-descriptions-item label="用户名">{{userId}}</a-descriptions-item>
        <a-descriptions-item label="头像">
            <a-avatar :src="avatar"></a-avatar>
            <cloud-upload-outlined style="fontSize:30px;position: relative;left: 50px;" @click="ckInput" />
            <input v-show="false" accept="image/*" :multiple="false" type="file" @change="uploadAvatar($event)" ref="uploadInput"/>
        </a-descriptions-item>

        <a-descriptions-item label="本站唯一编号">{{id}}</a-descriptions-item>
        <a-descriptions-item label="注册时间">{{createTime}}</a-descriptions-item>
        <!-- <a-descriptions-item label="Usage Time" :span="2">2019-04-24 18:00:00</a-descriptions-item> -->
        <a-descriptions-item label="权限">{{auth}}</a-descriptions-item>
    </a-descriptions>
</template>
<script>
import { computed } from '@vue/reactivity'
import store from '../store/index'
import { CloudUploadOutlined } from '@ant-design/icons-vue'
import axios from 'axios'
import { ref } from 'vue'
import { message } from 'ant-design-vue'

export default{
    name: "UserDetail",
    components: { CloudUploadOutlined },
    setup() {
        store.dispatch("getUserDetail")
        let uploadInput = ref()
        const ckInput = () => {
            let htmlInput = uploadInput.value 
            htmlInput.click()
        }
        const uploadAvatar = event => {
            let files = event.target.files
            console.log(files[0])
            let formData = new FormData()
            formData.append("avatar",files[0])
            axios.post("user/uploadAvatar", formData, {
                params: {
                    user:{
                        userId: window.sessionStorage.getItem("userId")
                    }
                }
            })
                .then(response => {
                    if (response.data.code === 200) {
                        if (response.data.detail === "头像上传成功") {
                            message.info("头像上传成功")
                    }
                }
            })
            
        }
        return {
            id: computed(() => store.state.userDetail.id),
            userId:computed(()=>store.state.userDetail.userId),
            avatar:computed(()=>store.state.userDetail.avatar),
            createTime:computed(()=>store.state.userDetail.createTime),
            auth: computed(() => store.state.userDetail.auth),
            ckInput,
            uploadAvatar,
            uploadInput
        }
    }
}
</script>