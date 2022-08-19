<template>
<div id="statistics">
  <suspense>
     <div id="chart" style="width: 100%; height: 400px"></div>
     </suspense>
</div>
</template>
<script>
import { message } from "ant-design-vue";
import axios from "axios";
import * as echarts from "echarts";
import {ref} from "vue"


export default{
    name:"Statistics",
    setup(){
      let musicUploadData=ref([])
      let musicDeleteData=ref([])
      let time=ref([])
      const getStatistics=()=>{
        return  new Promise((resolve,reject)=>{
        const uri="/statistics/selectPage"
        let formData= new FormData()
        formData.append("pageNumber",1)
        formData.append("pageSize",7)
        formData.append("from","2022-5-1")
        formData.append("to","2022-5-20")
        axios.post(uri,formData).then(response=>{
            if(response.data.code===200){
                message.info("查询统计数据成功")
                resolve(response.data.result)
                return
            }
            message.error("查询数据出现错误")
        }).catch(err=>{
            message.error("网络错误")
        })
        })
      }
      const initEcharts=()=>{
                    // 基于准备好的dom，初始化echarts实例
let myChart = echarts.init(document.getElementById("chart"));

// 绘制图表
myChart.setOption({
  title: {
    text: '音乐模块统计表'
  },
  tooltip: {},
  xAxis: {
    data: time.value
  },
  yAxis: {},
  series: [
    {
      name: '上传数量',
    //   type: 'bar',
      data: musicUploadData.value,
      type: 'line',
      areaStyle: {
        color: '#ff0',
        opacity: 0.5
      }
    },
    {
      name: '删除数量',
    //   type: 'bar',
      data: musicUploadData.value,
      type: 'line',
      areaStyle: {
        color: '#ff1',
        opacity: 0.5
      }
    }
  ]
});
console.log(musicUploadData.value)
console.log(musicUploadData.value)
        }
      Promise.all([getStatistics()]).then(([result])=>{
        // console.log(result)
      result.forEach(element => {
                  console.log(element)
                    musicUploadData.value.push(element.musicUploadCount)
                    musicDeleteData.value.push(element.musicDeleteCount)
                    time.value.push(element.createTime.substring(0,10))
                })
                initEcharts()
      })
        return{

        }
    }
}
</script>
<style>
</style>