export default function useWebSocket(url, handleMessage) {
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
        const handleError = (err) => {
            alert(err.message)
        }
        webSocket.addEventListener("error", handleError, false)
        webSocket.addEventListener("message", handleMessage, false)
    }
    init()
    return webSocket
}