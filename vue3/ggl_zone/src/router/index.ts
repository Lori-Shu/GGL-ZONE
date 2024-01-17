import {createRouter, createWebHistory} from 'vue-router'
import Note from "@/routes/Note.vue";
import Music from "@/routes/Music.vue";
import Chat from "@/routes/Chat.vue";
import Video from "@/routes/Video.vue";
import EditNote from "@/routes/EditNote.vue"
import Login from "@/routes/Login.vue"
import Registry from "@/routes/Registry.vue";
import UploadMusic from "@/routes/UploadMusic.vue";
import UploadVideo from "@/routes/UploadVideo.vue";
import PlayVideo from "@/routes/PlayVideo.vue";
import Main from "@/routes/Main.vue"
import AddNote from "@/routes/AddNote.vue"
import Welcome from "@/routes/Welcome.vue"
import UserDetail from "@/routes/UserDetail.vue"
import UserOrder from "@/routes/UserOrder.vue"
import Statistics from "@/routes/Statistics.vue"

const routes = [
    {
        path: "/",
        components:{
            mainView:Login
        }
    },
    {
        path: "/registry",
        components:{
            mainView:Registry
        }
    },
    {
        path: "/main",
        components:{
            mainView:Main
        },
        children:[
        {
            path:"/main/",
            components:{
                content:Welcome
            },
        
        },
            {
            path:"/main/music",
            components:{
                content:Music
            },
            
        },
        {
            path:"/main/note",
            components:{
                content:Note
            },
            
        },
        {
            path:"/main/video",
            components:{
                content:Video
            },
            
        },
        {
            path:"/main/upload_music",
            components:{
                content:UploadMusic
            },
            
        },
        {
            path:"/main/upload_video",
            components:{
                content:UploadVideo
            },
            
            },
            {
                path: "/main/play_video",
                components: {
                    content: PlayVideo
                },

            },
        {
            path:"/main/add_note",
            components:{
                content:AddNote
            },
            
        },
        {
            path:"/main/edit_note",
            components:{
                content:EditNote
            },
            
        },
        {
            path:"/main/user_detail",
            components:{
                content:UserDetail
            },
            
        },
        {
            path:"/main/user_order",
            components:{
                content:UserOrder
            },
            
        },
        {
            path:"/main/chat",
            components:{
                content:Chat
            },
            
        },
        {
            path:"/main/statistics",
            components:{
                content:Statistics
            },
            
        }]
    }
]
const router = createRouter({
    routes,
    history: createWebHistory()
})
// router.beforeEach((to, from, next) => {
//     if (to.path !== "/" && to.path !== "/regist") {
//         const authorized = window.sessionStorage.getItem("authorized")
//         if (authorized === "1") {
//             next()
//         } else {
//             alert("您是未授权用户")
//         }
//     } else {
//         next()
//     }
// })
export {
    router,
    routes
}