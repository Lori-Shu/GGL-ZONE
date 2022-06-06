import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
}
