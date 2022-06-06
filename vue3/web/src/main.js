import {createApp} from 'vue'
import App from './App.vue'
import {
    Avatar,
    Button,
    Card,
    Collapse,
    ConfigProvider,
    Drawer,
    Image,
    Input,
    List,
    Menu,
    Modal,
    Pagination,
    Tooltip,
    Upload,
    Layout,
    Descriptions,
    Tag,
    Divider,
    Table
} from 'ant-design-vue'
import {router} from "./router/index"
import axios from "axios";
import 'animate.css'
import store from "./store/index"
import Vue3VideoPlay from 'vue3-video-play'
import "vue3-video-play/dist/style.css";

axios.defaults.baseURL = '/api/'
axios.interceptors.request.use((config) => {
    // console.log("--",config);
    let token = window.sessionStorage.getItem("token")
    // console.log(token)
    if (token !== null) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
        config.headers.token = token
        // config.headers['Authorization'] = token;
    }
    return config;
}, error => {
    console.log(error)
    // 发生错误做的一些事情
    return Promise.reject(error);
})


const app = createApp(App)
app.use(Button)
app.use(Pagination)
app.use(Image)
app.use(ConfigProvider)
app.use(List)
app.use(Input)
app.use(Tooltip)
app.use(Avatar)
app.use(Upload)
app.use(Card)
app.use(Collapse)
app.use(Descriptions)
app.use(Menu)
app.use(Drawer)
app.use(Modal)
app.use(Layout)
app.use(Tag)
app.use(Divider)
app.use(Table)



app.use(router)
app.use(store)
app.use(Vue3VideoPlay)
app.mount('#app')
