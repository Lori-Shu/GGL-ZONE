<template>
  <div id="addNote">
    <table id="edittable" border="1">
      <tr>
        <td>标题:</td>
        <td><input v-model="thisdata.title" type="text"></td>
      </tr>
      <tr>
        <td>时间：</td>
        <td><input v-model="thisdata.year" type="text">年
          <input v-model="thisdata.month" type="text"/>月
          <input v-model="thisdata.day" type="text"/>日
        </td>
      </tr>
      <tr>
        <td>内容：</td>
        <td><textarea id="textarea" v-model="thisdata.content"></textarea></td>
      </tr>
      <tr>
        <router-link to="/note">
          <td>
            <a-button type="primary" v-on:click="submitForm">提交新增</a-button>
          </td>
        </router-link>
      </tr>
    </table>
  </div>
</template>

<script>
import {getCurrentInstance, reactive} from "vue";
import axios from "axios";

export default {
  name: "AddNote",
  setup() {
    const instance = getCurrentInstance()
    // const _this = instance.appContext.config.globalProperties
    // console.log(JSON.parse(_this.$route.query.data))
    // let data = JSON.parse(_this.$route.query.noteData)
    // let raw =toRaw(data)
    // console.log(raw)
    let thisdata = reactive({})
    // console.log(thisdata)
    // console.log(thisdata.uuid)

    let submitForm = () => {

      if (thisdata.uuid !== undefined) {
        axios.get("http://user-client.vaiwan.com/user/editnote", {
          params: {
            "uuid": thisdata.uuid,
            "userId": thisdata.userId,
            "title": thisdata.title,
            "year": thisdata.year,
            "month": thisdata.month,
            "day": thisdata.day,
            "content": thisdata.content
          }
        }).then(
            (response) => {
              // console.log(thisdata.content)
              alert(response.data.detail)
              // console.log( response.data["result"])
              // console.log(responsedata)
            }
        ).catch(err => {
          alert(err)
        })
      } else {
        axios.get("http://user-client.vaiwan.com/user/createnote", {
          params: {
            "userId": window.sessionStorage.getItem("userId"),
            "title": thisdata.title,
            "year": thisdata.year,
            "month": thisdata.month,
            "day": thisdata.day,
            "content": thisdata.content
          }
        }).then(
            (response) => {
              // console.log(thisdata.content)
              alert(response.data.detail)
              // console.log( response.data["result"])
              // console.log(responsedata)
            }
        ).catch(err => {
          // console.log(thisdata.content)
          alert(err)
        })
      }
    }

    return {
      thisdata,
      submitForm
    }
  }
}
</script>

<style scoped>
#edittable {
  background-color: aliceblue;
  font-size: x-large;
}

#textarea {
  width: 30cm;
  height: 15cm;
}
</style>