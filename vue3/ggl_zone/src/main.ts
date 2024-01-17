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
    Table,
    Progress,
    Slider
} from 'ant-design-vue'
import {router} from "./router/index"
import axios from "axios";
import 'animate.css'
import store from "./store/index"
// import Vue3VideoPlay from 'vue3-video-play'
import "vue3-video-play/dist/style.css";

axios.defaults.baseURL = '/api/'
axios.defaults.headers.common['token'] = `Bearer ${window.sessionStorage.getItem('token')}`;


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
app.use(Progress)
app.use(Slider)



app.use(router)
app.use(store)
// app.use(Vue3VideoPlay)
app.mount('#app')
