import axios from "axios";
import { message } from "ant-design-vue";
import { saveAs } from 'file-saver'

function myDownLoad(requestUrl, item) {
    let formData = new FormData()
    formData.append("video",JSON.stringify(item))
    axios.post(requestUrl, formData,
        {
        responseType:"blob"
    }).then(response=>{
        // let blob = new Blob([response.data], {
        //     type: 'application/x-download;charset=utf-8'/*application/xlsx // 这里写要下载的文件格式;charset=utf-8*/
        // });
        let blob = new Blob([response.data])
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

//     var xhr = new XMLHttpRequest();
//     xhr.open('post', "http://localhost:8080/api/" + requestUrl);
//     //设置请求头
//     xhr.setRequestHeader("token", window.sessionStorage.getItem("token"));
//     xhr.setRequestHeader("content-type", "application/json");
//     //设置响应类型
//     xhr.responseType = 'blob';
//     xhr.onload = function (e) {
//         if (this.status == 200) {
//             var filename = xhr.getResponseHeader("Content-disposition").substring(9);
//             var blob = this.response;
//             var a = document.createElement('a');
//             var url = URL.createObjectURL(blob);
//             a.href = url;
//             a.download = filename;
//             document.body.appendChild(a);
//             a.click();
//             window.URL.revokeObjectURL(url);
//         }
//     };
//     xhr.send(JSON.stringify(item));
}
// const downloadPiece=(url,start,end,contentLength)=> {
//     let xhr = new XMLHttpRequest()
//     xhr.open('POST', url)
//     xhr.responseType = 'arraybuffer'
//     // arraybuffer blob，指定arraybuffer类型便于进行数据处理，具体看使用情况
//     xhr.setRequestHeader('Range', `bytes=${start}-${end}`)
//     xhr.onload = function () {
//         if (xhr.status === 200) {
//             //将arraybuffer转成blob
//             saveAs(new Blob([xhr.response]), filename)
//         }

//         if (xhr.status === 206) {
//             let endLength = end + partLength
//             if (endLength > contentLength) {
//                 endLength = contentLength
//             }
//             downloadPiece(url, end + 1, endLength, contentLength)
//         }

//         if (xhr.status === 416) {
//             console.log('416', xhr.response)
//         }
//     }
//     xhr.onerror = function () { }
//     xhr.send({})
// }
// const getContentLength=(url)=> {
//     return new Promise(resolve => {
//         const xhr = new XMLHttpRequest()
//         xhr.open('HEAD', url)
//         xhr.onload = function () {
//             resolve(xhr.getResponseHeader('content-length') || 0)
//         }
//         xhr.send()
//     })
// }
const downloadMovie = (video) => {
    // let link = document.createElement('a') // 创建a标签
    // link.style.display = 'none'
    // let src=video.src
    // link.href = src
    // const lastaIndex=src.lastIndexOf('/')
    // let name = src.substring(lastaIndex+1)
    // link.setAttribute('download', src)
    // document.body.appendChild(link)
    // link.click()
    // document.body.removeChild(link)
    saveAs(video.src)
}
export {myDownLoad,downloadMovie}