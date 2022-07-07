import axios from "axios";
import {message} from "ant-design-vue";

export default function myDownLoad(requestUrl,item){
    axios.post(requestUrl, item,
        {
        responseType:"blob"
    }).then(response=>{
        let eleLink = document.createElement('a');
        eleLink.download = item.musicName+".flac";
        eleLink.style.display = 'none';
        // 字符内容转变成blob地址
        let blob = new Blob([response.data]);
        eleLink.href = window.URL.createObjectURL(blob);
        // 触发点击
        document.body.appendChild(eleLink);
        eleLink.click();
        // 然后移除
        document.body.removeChild(eleLink);
        window.URL.revokeObjectURL(eleLink.href)
        message.info("已开始下载！")
    }).catch(err=>{
        console.log(item.src)
        console.log("网络错误！！",err)
    })
}