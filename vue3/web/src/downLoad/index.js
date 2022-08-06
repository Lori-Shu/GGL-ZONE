import axios from "axios";
import {message} from "ant-design-vue";

export default function myDownLoad(requestUrl,item){
    axios.post(requestUrl, item,
        {
        responseType:"blob"
    }).then(response=>{
        let blob = new Blob([response.data], {
            type: 'application/octet-stream;charset=utf-8'/*application/xlsx // 这里写要下载的文件格式;charset=utf-8*/
        });
        // 获取后端返回的文件名 --> 后端配合response.setHeader("Content-disposition", "attachment; filename=xxxx.xlsx") 设置的文件名
        // 注意: 有时候axios返回的res.headers里面可能不包含content-disposition字段
        //这是因为默认情况下，header只会暴露Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma这6个字段
        // 此时需要后端设置response.setHeader("Access-Control-Expose-Headers", "Content-Disposition")将content-disposition字段暴露出去
        let fileName = response.headers['content-disposition'].split(';')[1];
        fileName = decodeURI(fileName.substring(9))
        let eleLink = document.createElement('a');
        // eleLink.download = '模板.xlsx' // 这里写的是下载文件的名称
        eleLink.download = fileName // 这里是后端返回的文件名称
        eleLink.style.display = 'none';
        // 字符内容转变成blob地址
        // URL.createObjectURL(blob)会创建URL对象，返回一个下载文件的地址
        eleLink.href = URL.createObjectURL(blob);
        // 触发点击
        document.body.appendChild(eleLink);
        eleLink.click();
        // 释放URL对象
        URL.revokeObjectURL(eleLink.href)
        // 然后移除
        document.body.removeChild(eleLink)
        message.info("已开始下载！")
    }).catch(err=>{
        console.log(item.src)
        console.log("网络错误！！",err)
    })
}