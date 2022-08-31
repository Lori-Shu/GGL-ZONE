import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
}
