import { createStore } from "vuex";

const store = createStore({
        // 声明变量
        state: {
            userDetail:{
                userId:window.sessionStorage.getItem("userId")
            },
            showLogin:true,
            existChat:new Map()
        },
        // 修改变量（state不能直接赋值修改，只能通过mutations）
        // mutations的值由actions传入
        actions: {
            // 参数一：自带属性，参数二：新值
            setUserId(context, value) {
                // 修改getUserId的值
                context.commit('setUserId', value)
            },
            changeShowLogin(context){
                context.commit("changeShowLogin")
            },
            setUserDetail(context,value){
                context.commit("setUserDetail",value)
            },
            addChat(context,chatTarget){
                context.commit("addChat",chatTarget)
            },
            addMessage(context ,newMessage){
                context.commit("addMessage",newMessage)
            }
        },
        mutations: {
            // 参数一：state，参数二：新值
            setUserId(state, newValue) {
                state.userDetail.userId = newValue
            },
            changeShowLogin(state){
                state.showLogin=!state.showLogin
            },
            setUserDetail(state, newValue) {
                state.userDetail = newValue
            },
            addChat(state,chatTarget){
                state.existChat.set(chatTarget,[]);
            },
            addMessage(state,newMessage){
                if(newMessage.target!==state.userDetail.userId){
                console.log(newMessage.target)    
                state.existChat.get(newMessage.target).push(newMessage)
                
                return
            }
            console.log("target",newMessage.target)
            state.existChat.get(newMessage.from).push(newMessage)
            }
        }
    })

export default store