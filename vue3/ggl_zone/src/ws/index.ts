export default function useWebSocket( url:string, handleMessage:(e:Event) => void) {
    let webSocket = new WebSocket(url)
    const init = () => {
        bindEvent()
    }
    const bindEvent = () => {
        const handleOpen = () => {
            console.log("ws连接已成功建立")
        }
        webSocket.addEventListener("open", handleOpen, false)
        const handleClose = () => {
            webSocket.close()
            console.log("ws连接已关闭")
        }
        webSocket.addEventListener("close", handleClose, false)
        const handleError = (ev:Event) => {
            // alert(ev.target.message)
        }
        webSocket.addEventListener("error", handleError, false)
        webSocket.addEventListener("message", handleMessage, false)
    }
    init()
    return webSocket
}