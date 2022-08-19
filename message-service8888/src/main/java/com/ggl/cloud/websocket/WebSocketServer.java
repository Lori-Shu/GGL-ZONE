package com.ggl.cloud.websocket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonMessage;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@ServerEndpoint(value = "/websocket/{userId}", subprotocols = {"protocol"})
@Slf4j
@Component
public class WebSocketServer {
    /**
     * 记录当前在线连接数，保证线程安全
     */
    private static AtomicInteger onlineNum = new AtomicInteger();
    /**
     * 存放 WebSocketServer 对象
     */
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId)  {
        sessionPools.put(userId, session);
        addOnlineCount();
        log.info("用户： " + userId + " 已连接");
        log.info("目前连接人数：" + onlineNum);
        refreshStatus(userId, session);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        sessionPools.remove(userId);
        subOnlineCount();
        log.info(userId + " 已断开");
        log.info("目前连接人数：" + onlineNum);
        refreshStatus(userId, null);
    }

    @OnMessage
    public void onMessage(String jsonString, Session session)  {
        log.info("收到消息");
        try {
            CommonMessage commonMessage=objectMapper.readValue(jsonString, CommonMessage.class);
            
            if (!commonMessage.getFrom().equals("system")) {
    //        log.info(target.textValue());
                Session targetSession = sessionPools.get(commonMessage.getTarget());
                if (targetSession != null) {
                    log.info("发送消息给" + commonMessage.getTarget());
                    sendMessage(targetSession, commonMessage);
                }
                return;
            }
            refreshStatus(commonMessage.getFrom(), session);
        } catch (Exception e) {
            // 收到消息方法执行异常
            log.warn("接收信息异常");
            e.printStackTrace();
        }
      

    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void refreshStatus(String refreshUserId, Session session) {
        
        try {
            
            for (Map.Entry<String, Session> entry : sessionPools.entrySet()) {
//            log.info("给==="+entry.getKey()+"发送通知");
            if(session==null){
                sendMessage(entry.getValue(), new CommonMessage("offline", refreshUserId, "system",dateTimeFormatter.format( LocalDateTime.now())));
                continue;
            }
                sendMessage(entry.getValue(), new CommonMessage("online", refreshUserId, "system",dateTimeFormatter.format( LocalDateTime.now())));
                sendMessage(session, new CommonMessage("online", entry.getKey(), "system",dateTimeFormatter.format( LocalDateTime.now())));
            }
            log.info("刷新了一次状态");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

//    public void sendInfo(String userName, String message) throws IOException {
//        Session session = sessionPools.get(userName);
//        sendMessage(session, message);
//    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    public void sendMessage(Session session, CommonMessage message)  {
        try {
            session.getBasicRemote().sendText(objectMapper.writeValueAsString(message));
        } catch (IOException e) {
            // 发送消息过程的异常处理
            log.warn("发送信息异常");
            e.printStackTrace();
        }
    }

}
