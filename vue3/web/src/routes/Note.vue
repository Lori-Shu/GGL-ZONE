<template>
  <div id="note">
    <router-link to="/main/add_note">
      <a-button style="float: right" type="primary">新建笔记</a-button>
    </router-link>
    <br/><br/>
    <div id="select">
      <form>
        <input v-model="selectParam.year" class="text" type="text"/><label>年</label>
        <input v-model="selectParam.month" class="text" type="text"/><label>月</label>
        <input v-model="selectParam.day" class="text" type="text"/><label>日</label>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <label>标题：</label><input v-model="selectParam.title" class="text" type="text"/>
        <a-button type="primary" @click="select">查询</a-button>
      </form>
    </div>
    <br/><br/>
    <NoteView></NoteView>
  </div>
</template>

<script>
import NoteView from "@/routes/NoteView";
import {provide, reactive, ref} from "vue";
import axios from "axios";

export default {
  name: "Note",
  components: {NoteView},
  setup() {
    //直接给整个对象赋值而不是给对象里的属性赋值时，使用ref，若使用reactive无法起到响应式的作用
    let data = ref({})
    provide("data", data)
    let selectParam = reactive({year: '', month: '', day: ''})
    provide("selectParam", selectParam)
    let selected = ref("0")
    provide("selected", selected)
    let total = ref(0)
    provide("total", total)
    let current = ref(1)
    provide("current", current)
    const select = () => {
      // console.log(data.year)
      if (selectParam.year !== "" && selectParam.month !== "" && selectParam.day !== "") {
        axios.post("/user/note/select_page",{
          userId: window.sessionStorage.getItem("userId"),
            year: selectParam.year,
            month: selectParam.month,
            day: selectParam.day,
            title: selectParam.title,
        }, {
          params: {
            pageNumber: 1,
            pageSize: 8
          }
        }).then((response) => {
          data.value = response.data.result.list
          total.value = response.data.result.total
          selected.value = "1"
          // current.value = 1
          alert("查询成功")
        }).catch(err => {
          alert(err)
        })
      } else if (selectParam.year === "" && selectParam.month === "" && selectParam.day === "") {
        alert("已查询所有结果")
        axios.post("/user/note/select_page", {
          userId: window.sessionStorage.getItem("userId")
        },{
          params: {
            pageNumber: 1,
            pageSize : 8
          }
        }).then(
            (response) => {
              data.value = response.data.result.list
              // console.log(data)
              // console.log(response.data.result.pageInfo)
              total.value = response.data.result.total
              // console.log(total.value)
              current.value = 1
              selected.value = "0"
              // console.log( response.data["result"])
              // console.log(responsedata)
            }
        ).catch(err => {
          alert(err)
        })
      } else {
        alert("请合理填写查询条件，标题可选填!")
      }
    }
    return {
      data,
      select,
      selected,
      selectParam
    }
  }
}
</script>

<style scoped>
#select {
  font-size: x-large;
  color: crimson;
}

.text {
  background-color: azure;
}
</style>