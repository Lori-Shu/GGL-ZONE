<template>
  <div id="noteView">
    <ol>
      <li v-for="i in responsedata" :key="i.uuid">
        <router-link :to="{path:'/editnote',query:{noteData:JSON.stringify(i)}}">
          <span id="title">标题：{{ i.title }}</span>
          <span id="time">时间：{{ i.year }}年{{ i.month }}月{{ i.day }}日</span>
        </router-link>
        <a-button id="deleteButton" type="primary" @click="deleteNote(i.uuid)">删除</a-button>
        <br/>
        <span>—————————————————————————————————————————————————————————————</span>
      </li>
    </ol>
    <div>
      <a-pagination v-model:current="current" :pageSize="8" :show-total="total => `总 ${total} 条数据`" :total="total"
                    show-quick-jumper @change="onChange"/>
    </div>
  </div>
</template>

<script>
import {inject, nextTick} from "vue";
import axios from "axios";

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
    const onChange = pageNumber => {
      // console.log(props.selected)
      if (selected.value === "0") {
        axios.get("http://user-client.vaiwan.com/user/getpage", {
          params: {
            userId: window.sessionStorage.getItem("userId"),
            pageNumber
          }
        }).then(
            (response) => {
              responsedata.value = response.data.result.pageList
              // console.log(responsedata)
              total.value = response.data.result.total
              // console.log(total.value)
              current.value = pageNumber
              // console.log( response.data["result"])
              // console.log(responsedata)
            }
        ).catch(err => {
          alert(err)
        })
      } else {
        axios.get("http://user-client.vaiwan.com/user/selectpage", {
          params: {
            userId: window.sessionStorage.getItem("userId"),
            year: selectParam.year,
            month: selectParam.month,
            day: selectParam.day,
            title: selectParam.title,
            pageNumber
          }
        }).then((response) => {
          // console.log(response.data.result.pageList)
          responsedata.value = response.data.result.pageList
          total.value = response.data.result.total
          current.value = pageNumber
        }).catch(err => {
          alert(err)
        })
      }
    }
    nextTick(() => {
      if (selected.value === "0") {
        axios.get("http://user-client.vaiwan.com/user/getpage", {
          params: {
            userId: window.sessionStorage.getItem("userId"),
            pageNumber: 1
          }
        }).then(
            (response) => {
              responsedata.value = response.data.result.pageList
              // console.log(responsedata)
              total.value = response.data.result.total
              // console.log(total.value)

              // console.log(responsedata.value)
              current.value = 1
              // console.log( response.data["result"])
              // console.log(responsedata)
            }
        ).catch(err => {
          alert(err)
        })
      }
    })

    let deleteNote = uuid => {
      //confirm返回一个boolean需要自行处理
      if (confirm("确定删除这条笔记吗？")) {
        axios.get("http://user-client.vaiwan.com/user/deletenote", {
          params: {
            uuid
          }
        }).then((response) => {
          if (response.data.detail === "删除成功") {
            alert("删除成功！")
            onChange(current.value)
          } else {
            alert("删除失败")
          }
        }).catch(err => {
          alert(err)
        })
      }
    }
    return {
      responsedata,
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