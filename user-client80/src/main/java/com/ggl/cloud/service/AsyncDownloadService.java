// /**
// *
// *@Date:2022年8月10日
// *
// *@Author:Lori Shu
// *
// */
// package com.ggl.cloud.service;

// import java.nio.charset.StandardCharsets;
// import java.util.List;

// import javax.annotation.Resource;
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletResponse;

// import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
// import org.apache.rocketmq.client.consumer.MessageSelector;
// import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
// import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
// import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
// import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
// import org.apache.rocketmq.common.message.Message;
// import org.apache.rocketmq.common.message.MessageExt;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.scheduling.annotation.EnableAsync;
// import org.springframework.stereotype.Service;
// import org.springframework.web.util.UriUtils;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ggl.cloud.entity.Video;

// import cn.hutool.core.lang.UUID;
// import lombok.extern.slf4j.Slf4j;

// @Service
// @EnableAsync
// @Slf4j
// public class AsyncDownloadService {
//     @Resource
//     private ObjectMapper om;
//     public void downloadVideoDeamonThread(Video video,long length, HttpServletResponse response) throws Exception {
//         // service.download(video);
//         DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
//         // 设置组
//         consumer.setConsumerGroup("video-bytes-consumer");
//         // 设置唯一编号
//         consumer.setInstanceName(UUID.randomUUID().toString());
//         // consumer.setConsumeTimeout(5000);
//         consumer.setNamesrvAddr("localhost:9876");
//         // 订阅topic,初始化tag

//         consumer.subscribe("download-video", MessageSelector.byTag(video.getId()));
//         // 设置消费起始位置
//         consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//         // 设置广播模式，默认为集群模式,广播模式不支持顺序消息!!!!!!!!!!
//         // consumer.setMessageModel(MessageModel.BROADCASTING);
//         response.reset();
//         response.setStatus(HttpServletResponse.SC_OK);
//         response.setContentType("application/x-download");
//         String path = video.getStorePath();
//         int lI = path.lastIndexOf("/");
//         String filename = path.substring(lI + 1);
//         response.setHeader("Content-Disposition", "attachment;filename=" + UriUtils.encode(filename, "UTF-8"));
//         // response.setHeader("Content-Length", "" + length);
//         ServletOutputStream outputStream = response.getOutputStream();

//         // 设置消费监听器
//         consumer.setMessageListener(new MessageListenerOrderly() {

//             @Override
//             public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
//                 // 顺序地接收bytes
//                 // context.setAutoCommit(true);
//                 for (MessageExt me : msgs) {
//                     if (me.getBody().length > 0) {
//                         try {
//                             // log.warn("接收到正常消息大小：" + me.getBody().length);
//                             String jsonMessage = new String(me.getBody(), StandardCharsets.UTF_8);
//                             Message message = om.readValue(jsonMessage,
//                                     Message.class);
//                             outputStream.write(message.getBody());
//                             // log.warn(jsonMessage);
//                             continue;
//                         } catch (Exception e) {
//                             // 转换异常
//                             log.warn(e.getMessage());
//                         }
//                     }

//                     log.warn("接收到空消息");
//                 }
//                 log.warn("已接收所有消息");
//                 // try {

//                 // Thread.sleep(1000);
//                 // } catch (Exception e) {
//                 // // 处理休眠异常
//                 // log.error("消费监听线程休眠异常：" + e.getMessage());
//                 // }
//                 return ConsumeOrderlyStatus.SUCCESS;
//             }

//         });
//         log.warn("consumer已启动");
//         consumer.start();
//         // String fileName = file.getName();
//         // HttpHeaders headers = new HttpHeaders();
//         // headers.add("Content-Disposition", String.format("attachment;filename=\"%s",
//         // fileName));
//         // headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//         // headers.add("Pragma", "no-cache");
//         // headers.add("Expires", "0");
//         // headers.add("Last-Modified", new java.util.Date().toString());
//         // headers.add("ETag", String.valueOf(System.currentTimeMillis()));

//         // return ResponseEntity
//         // .ok()
//         // .headers(headers)
//         // .contentLength(file.length())
//         // .contentType(MediaType.parseMediaType("application/octet-stream"))
//         // .body(new FileSystemResource(file));
//         // Thread.sleep(30 * 1000);
//         // consumer.shutdown();
//         log.warn("下载完成！");
//     }
// }
