<template>
  <div id="noteView">
    <ol>
      <li v-for="i in responsedata" :key="i.uuid">
        <router-link :to="{path:'/main/edit_note',query:{noteData:JSON.stringify(i)}}">
          <span id="title">标题：{{ i.title }}</span>
          <span id="time">时间：{{ i.year }}年{{ i.month }}月{{ i.day }}日</span>
        </router-link>
        <a-button id="deleteButton" type="primary" @click="deleteNote(i)">删除</a-button>
        <br/>
        <span>—————————————————————————————————————————————————————————————</span>
      </li>
    </ol>
    <div>
      <a-pagination v-model:current="current" :pageSize="pageSize" :show-total="total => `总 ${total} 条数据`" :total="total"
                    show-quick-jumper @change="onChange"/>
    </div>
  </div>
</template>

<script>
import {inject, nextTick,ref} from "vue";
import axios from "axios";
import { message } from "ant-design-vue";

export default {
  name: "NoteView",
  setup() {
    // console.log("打印this",_this)
    let responsedata = inject("data")
    let selectParam = inject("selectParam")
    // console.log(props.data.pagelist)

    // function showi(i){
    //   // console.log(JSON.stringify(i))
    //   console.log("showi方法",i)
    // }
    let current = inject("current")
    let total = inject("total")
    let selected = inject("selected")
    let pageSize=ref(8)
    const onChange = pageNumber => {
      // console.log(props.selected)
      if (selected.value === "0") {
        axios.post("/user/note/select_page", {
          userId : window.sessionStorage.getItem("userId")
        },{
          params: {
            pageNumber,
            pageSize: pageSize.value
          }
        }).then(
            (response) => {
              responsedata.value = response.data.result.list
              // console.log(responsedata)
              total.value = response.data.result.total
              // console.log(total.value)
              // current.value = pageNumber
              // console.log( response.data["result"])
              // console.log(responsedata)
            }
        ).catch(err => {
          alert(err)
        })
      } else {
        axios.post("/user/note/select_page",{
          userId: window.sessionStorage.getItem("userId"),
            year: selectParam.year,
            month: selectParam.month,
            day: selectParam.day,
            title: selectParam.title,
        }, {
          params: {
            pageSize: pageSize.value,
            pageNumber
          }
        }).then((response) => {
          // console.log(response.data.result.pageList)
          if(response.data.code===200){
          responsedata.value = response.data.result.list
          total.value = response.data.result.total
          // current.value = pageNumber
          }
        }).catch(err => {
          alert(err)
        })
      }
    }
    nextTick(() => {
      if (selected.value === "0") {
        axios.post("/user/note/select_page",{
          userId: window.sessionStorage.getItem("userId"),
        }, {
          params: {
            pageNumber: 1,
            pageSize: pageSize.value
          }
        }).then(
            (response) => {
              responsedata.value = response.data.result.list
              // console.log(responsedata)
              total.value = response.data.result.total
              // console.log(total.value)

              // console.log(responsedata.value)
              // current.value = 1
              // console.log( response.data["result"])
              // console.log(responsedata)
            }
        ).catch(err => {
          alert(err)
        })
      }
    })

    let deleteNote = note => {
      //confirm返回一个boolean需要自行处理
      if (confirm("确定删除这条笔记吗？")) {
        axios.post("/user/note/delete", 
        note
        ).then((response) => {
          if(response.data.code===200){
            message.info(response.data.detail)
            onChange(current.value)
            }
        }).catch(err => {
          alert(err)
        })
      }
    }
    return {
      responsedata,
      pageSize,
      current,
      onChange,
      total,
      deleteNote
    }
  }
}
</script>

<style scoped>
#noteView {
  float: left;
  color: azure;
  font-size: x-large;
}

#title {
  position: fixed;
  left: 20%;
}

#time {
  position: fixed;
  left: 50%;
}

#deleteButton {
  position: fixed;
  right: 20%;
}
</style>