package com.ggl.cloud.service.impl;
/*
 *@Author Lori Shu
 *@Date 2022/4/23
 */

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.feignservice.ServerFeign;
import com.ggl.cloud.service.DownloadService;
import com.ggl.cloud.service.IResourceService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@EnableAsync
public class DownloadServiceImpl implements DownloadService {
    @Resource
    private IResourceService service;
    @Resource
    private ServerFeign serverFeign;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public byte[] downloadMusic(Music music) throws IOException {
        log.warn("进入download...service");
        QueryWrapper<com.ggl.cloud.entity.Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_name", music.getMusicName());
        com.ggl.cloud.entity.Resource one = service.getOne(queryWrapper);
        if (one != null) {
            service.updateById(one);
        } else {
            com.ggl.cloud.entity.Resource resource = new com.ggl.cloud.entity.Resource();
            resource.setRequestType("download");
            resource.setResourceName(music.getMusicName());
            resource.setResourceType("music");
            service.save(resource);
        }
        serverFeign.plusDownload(music);
        // File file = new File(music.getStorePath());

        return getFileBytes(music.getStorePath());
        // throw new FileNotFoundException("文件未找到！！");
    }


    // @Async
    // @Override
    // public void downloadVideo(Video video) {
    //     try (FileInputStream fis = new FileInputStream("C:\\Users\\shuguanglin\\Desktop\\Test.java");) {
    //         // DefaultMQProducer videoMQProducer = new
    //         // DefaultMQProducer("transaction-download-video-group");
    //         // videoMQProducer.setNamesrvAddr("localhost:9876");
    //         // videoMQProducer.setInstanceName(UUID.randomUUID().toString());
    //         // // 设置超时时间
    //         // videoMQProducer.setSendMsgTimeout(30000);
    //         // // producer.start();
    //         // // for(int index=0;index<10;++index) {
    //         // // Message message = new Message();
    //         // // message.setKeys(String.valueOf(index));
    //         // // message.setTopic("myTopic");
    //         // // String messageBody = "我是一条消息体！！！" + index;
    //         // // message.setBody(messageBody.getBytes(StandardCharsets.UTF_8));
    //         // // message.setDelayTimeLevel(3);
    //         // // 设定回调的发送为异步发送，不设定默认为同步发送

    //         // // 异步发送
    //         // // producer.send(message, new SendCallback() {
    //         // // @Override
    //         // // public void onSuccess(SendResult sendResult) {
    //         // // System.out.println("发送消息成功!!!!"+sendResult);
    //         // // }
    //         // //
    //         // // @Override
    //         // // public void onException(Throwable throwable) {
    //         // // System.out.println("发送消息失败！！！");
    //         // // }
    //         // // });
    //         // // 实现顺序发送顺序消费（分区有序）
    //         // // 此时send方法的第三个参数会传入MessageQueueSelector实现的方法select（）中的第三个参数
    //         // videoMQProducer.start();

    //         for (int readCount = 1; readCount > 0;) {
    //             byte[] bytes = new byte[1024];
    //             if ((readCount = fis.read(bytes)) > 0) {
    //                 Message videoByteMessage = new Message();
    //                 videoByteMessage.setKeys("byteMsg:" + video.getId());
    //                 videoByteMessage.setTopic("download-video");
    //                 videoByteMessage.setTags(video.getId());
    //                 videoByteMessage.setBody(Arrays.copyOf(bytes, readCount));
    //                 rocketMQTemplate.asyncSendOrderly("download-video:" + video.getId(), videoByteMessage, "0",
    //                         new SendCallback() {

    //                             @Override
    //                             public void onSuccess(SendResult sendResult) {
    //                                 // 成功了啥也不干

    //                             }

    //                             @Override
    //                             public void onException(Throwable e) {
    //                                 // 异常日志打印异常
    //                                 log.warn(e.getMessage());

    //                             }

    //                         });
    //                 Thread.sleep(100);
    //                 // rocketMQTemplate. (videoByteMessage, new MessageQueueSelector() {
    //                 // @Override
    //                 // public MessageQueue select(List<MessageQueue> list, Message message, Object
    //                 // o) {
    //                 // // 要使message按顺序被消费，只要使message的key被队列数取余后相等，即会被放入同一条队列中
    //                 // // int key = Integer.valueOf(message.getKeys());
    //                 // // int id = key % list.size();
    //                 // return list.get(0);
    //                 // }
    //                 // }, null);
    //             }
    //         }
    //         log.warn("消息发送完成");
    //     } catch (Exception e) {
    //         // 使用另一个线程发送数据
    //         log.warn("发送数据线程异常");
    //     }

    //     // // 设置线程池
    //     // // ExecutorService threadPoolExecutor = new ThreadPoolExecutor(2, 5, 100,
    //     // TimeUnit.SECONDS,
    //     // // new ArrayBlockingQueue<Runnable>(2000),
    //     // // new ThreadFactory() {
    //     // // @Override
    //     // // public Thread newThread(Runnable r) {
    //     // // Thread myThread = new Thread(r);
    //     // // myThread.setName("transaction-test-thread");
    //     // // return myThread;
    //     // // }
    //     // // });
    //     // // transactionMQProducer.setExecutorService(threadPoolExecutor);
    //     // // transactionMQProducer.setTransactionListener(new MyTransactionListener());

    //     // // transactionMQProducer.sendMessageInTransaction(message1, null);

    //     // //// Thread.sleep(10);
    //     // // transactionMQProducer.sendMessageInTransaction(message3, null);
    //     // LocalDateTime now = LocalDateTime.now();
    //     // System.out.println("视频下载完成时间===》》》" +
    //     // DateTimeFormatter.ofPattern("HH:mm:ss").format(now));
    //     // videoMQProducer.shutdown();
    // }

    @Override
    public byte[] getFileBytes(String path) throws IOException {
        // 获得文件字节数组用于传输
        try (FileInputStream fis = new FileInputStream(path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            byte[] bytes = new byte[1024];
            for (int readCount = 1; readCount > 0;) {
                if ((readCount = fis.read(bytes)) > 0) {
                    baos.write(bytes, 0, readCount);
                }
            }
            return baos.toByteArray();
        } catch (Exception e) {
            // 处理io异常
            throw e;
        }
    }
}
