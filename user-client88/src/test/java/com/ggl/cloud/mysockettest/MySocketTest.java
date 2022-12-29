package com.ggl.cloud.mysockettest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * description
 *
 * @author lori
 * @createTime 2022年10月05日-20:03:47
 */
public class MySocketTest {
    // @Test
    // public void launchServer() {

    // try (ServerSocket ss = new ServerSocket(7825);
    // Socket s = ss.accept();
    // InputStream is = s.getInputStream();) {
    // byte[] buffer = new byte[1024];
    // for (int readNameCount = is.read(buffer); readNameCount != -1;) {
    // System.out.println("已读文件名字节数："+readNameCount);
    // readNameCount = is.read(buffer);
    // }
    // String string = new String(buffer).trim();
    // System.out.println(string + "|||");

    // try (Socket sFile = ss.accept();
    // InputStream isFile = sFile.getInputStream();
    // FileOutputStream foFile = new FileOutputStream(string);
    // OutputStream osFile=sFile.getOutputStream();) {
    // for (int readFileCount = isFile.read(buffer); readFileCount != -1;) {
    // System.out.println("已读字节数："+readFileCount);
    // foFile.write(buffer, 0, readFileCount);
    // readFileCount = isFile.read(buffer);
    // }
    // osFile.write("已成功接收客户端上传的文件".getBytes());
    // // sFile.shutdownOutput();

    // } catch (Exception e) {
    // e.printStackTrace();
    // System.out.println("exception:"+e.getMessage());
    // }

    // } catch (Exception e) {
    // e.printStackTrace();
    // System.out.println("exception:"+e.getMessage());
    // }
    // }
    // @Test
    // public void launchClient(){
    // try (Socket s=new Socket(InetAddress.getByName("127.0.0.1"), 7825);
    // OutputStream os=s.getOutputStream();) {
    // ClassPathResource resource = new ClassPathResource("testsocket.txt");
    // System.out.println("resourcePath:"+resource.getPath());
    // os.write(resource.getFilename().getBytes());
    // s.shutdownOutput();

    // try (Socket sFile=new Socket(InetAddress.getByName("127.0.0.1"), 7825);
    // OutputStream osFile=sFile.getOutputStream();
    // InputStream fileInputStream = resource.getInputStream();
    // InputStream isFile=sFile.getInputStream();) {
    // byte[] buffer=new byte[1024];
    // for(int readCount=fileInputStream.read(buffer);readCount!=-1;){
    // System.out.println("客户端已写入字节数："+readCount);
    // osFile.write(buffer, 0, readCount);
    // readCount=fileInputStream.read(buffer);

    // }
    // sFile.shutdownOutput();
    // // Thread.sleep(10*1000);
    // int read = isFile.read(buffer);
    // System.out.println(new String(buffer).trim());
    // // sFile.shutdownOutput();

    // } catch (Exception e) {
    // e.printStackTrace();
    // System.out.println("exception:"+e.getMessage());
    // }

    // } catch (Exception e) {
    // e.printStackTrace();
    // System.out.println("exception:"+e.getMessage());
    // }
    // }
    // 一般把读过程放在循环里反复执行由客户端主动关闭output来退出循环
    // 下面两个方法测试一个socket重复打开输出流
    @Test
    public void launchServer2() {
        try (ServerSocket ss = new ServerSocket(5825);
                Socket s = ss.accept();
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();) {
                ReentrantLock reentrantLock = new ReentrantLock();
                Condition readWriteCondition = reentrantLock.newCondition();
            new Thread(() -> {
                try {
                    reentrantLock.lock();
                    for (int j = 0; j < 1024; ++j) {
                        byte[] buffer = new byte[1024];
                        for (int readBytes = is.read(buffer), i = 0; readBytes != -1; ++i) {
                            if (readBytes == 1024) {
                                System.out.println("第" + i + "次读到信息：" + new String(buffer).trim());
                                break;
                            }
                            readBytes = is.read(buffer);
                        }
                        readWriteCondition.signal();
                        readWriteCondition.await();
                    }

                } catch (Exception e) {
                    System.out.println("exmsg:" + e.getMessage());
                }finally{
                    reentrantLock.unlock();
                }
            }, "readThread").start();
            new Thread(() -> {
                try {
                    reentrantLock.lock();
                    for (int j = 0; j < 1024; ++j) {
                        System.out.println("第"+j+"组消息接收完毕");
                        byte[] packagedBytes = Arrays.copyOf(("第"+j+"组消息接收完毕\n").getBytes(), 1024);
                        os.write(packagedBytes);
                        readWriteCondition.signal();
                        readWriteCondition.await();
                    }

                } catch (Exception e) {
                    System.out.println("exmsg:" + e.getMessage());
                }finally{
                    reentrantLock.unlock();
                }
            }, "writeThread").start();
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("exmessage:" + e.getMessage() + "--excause:" + e.getCause().getMessage());
        }
    }

    @Test
    public void launchClient2() {
        OutputStream os =null;
        try (Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 5825);
                InputStream is = s.getInputStream();
                ) {
            os = s.getOutputStream();
            byte[] buffer = new byte[1024];
            byte[] packagedBytes = Arrays.copyOf("这是第一组消息\n".getBytes(), 1024);
            os.write(packagedBytes);
            for (int readBytes = is.read(buffer); ;) {
                if (readBytes == 1024) {
                    System.out.println("第一次读到确认信息：" + new String(buffer).trim());
                    break;
                }
                readBytes = is.read(buffer);
            }
            byte[] packagedBytes2 = Arrays.copyOf("这是第二组消息\n".getBytes(), 1024);
            os.write(packagedBytes2);
            for (int readBytes = is.read(buffer); readBytes != -1;) {
                if(readBytes==1024){
                    System.out.println("第二次读到确认信息：" + new String(buffer).trim());
                    break;
                }
                readBytes = is.read(buffer);
            }
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("exmessage:" + e.getMessage());
        }finally{
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
