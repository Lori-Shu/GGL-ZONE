package com.ggl.cloud;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.annotation.AdviceName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.testentity.Colleague;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

// @SpringBootTest(classes = UserClient80.class)
@Slf4j
public class MyTest {
    
    @Test
    public void ioTest() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("C:\\Users\\shuguanglin\\Desktop\\Test.txt");
            fos = new FileOutputStream("C:\\Users\\shuguanglin\\Desktop\\Test1.txt");
            byte[] bytes = new byte[1024];
            Integer read;
            while ((read = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void ioTest1() {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader("C:\\Users\\shuguanglin\\Desktop\\Test1.txt");
            fw = new FileWriter("C:\\Users\\shuguanglin\\Desktop\\Test.txt");
            char[] chars = new char[1024];
            Integer read;
            while ((read = fr.read(chars)) != -1) {
                fw.write(chars, 0, read);
//                System.out.println(chars);
            }
//            fw.write("嘿嘿额哈哈");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void nioTest0() {
        FileInputStream fis = null;
        FileChannel fisChannel = null;
        FileOutputStream fos = null;
        FileChannel fosChannel = null;
        try {
            fis = new FileInputStream("C:\\Users\\shuguanglin\\Desktop\\Test.txt");
            fos = new FileOutputStream("C:\\Users\\shuguanglin\\Desktop\\Test1.txt");
            fisChannel = fis.getChannel();
            fosChannel = fos.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //            Long flag=0L;
            int i = fisChannel.read(byteBuffer);
            while (i > 0) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    fosChannel.write(byteBuffer);
                }
                byteBuffer.clear();
                i = fisChannel.read(byteBuffer);
            }
            System.out.println("完成！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fosChannel.close();
                fisChannel.close();
                fos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @Test
    public void testSyncConsumer() {
        // DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        // String s="sd";
        // switch (s) {
        //     case "sd":
        //         System.out.println("可以使用");
        //         break;
        //     default :System.out.println("不可以使用");
        // }
        // 所有浮点数存在误差，不精准
        System.out.println(3 *0.1 == 0.3);
    }
    @Test
    public void testWait() {
        synchronized (this) {
            try {
                // wait等待时间结束自动醒来不会报异常
                this.wait(1000);
            } catch (InterruptedException e) {
                //处理超时
                System.out.println("wait报错");
            }
        }
    }
    @Test
    public void testReflex(){
        MyTest myTest = new MyTest();
        try {
            Method method = myTest.getClass().getMethod("testSyncConsumer");
            Object invoke = method.invoke(myTest);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void testStreamSort(){
        ArrayList<Integer> al = new ArrayList<>();
        al.add(12);
        al.add(5);
        al.add(9);
        al.add(-96);
        al.stream().sorted((i1,i2)->{
            return i1-i2;
        }).forEach((o)->{
            System.out.println(o);
        });
    }
    @Resource
    private RedisTemplate<String,Object> redisTemplate; 

    @Test
    public void testRedisTemplate(){
        Music music = new Music();
        music.setMusicName("青花瓷");
        music.setMusician("房文山&周杰伦");
        redisTemplate.opsForValue().set("青花瓷",music );
        try {
            Thread.sleep(1000);
            Music sm = (Music)redisTemplate.opsForValue().get("青花瓷");
            log.warn(sm.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisPub(){
        stringRedisTemplate.convertAndSend("testTopic", "hello!world!");
        log.warn("消息已经发布到redis");
        try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testJsonConvert(){
        ObjectMapper om = new ObjectMapper();
        String s="{\"name\":\"Lucy\",\"salary\":\"1024\"}";
        try {
            Colleague c = om.readValue(s, Colleague.class);
            System.out.println(c.getSalary().doubleValue());
            System.out.println(c);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
