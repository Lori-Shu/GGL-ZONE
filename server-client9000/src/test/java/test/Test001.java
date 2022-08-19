package test;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test001 {
    @Test
    public void t() {
//        String s = MD5.create().digestHex("1234569");
//        System.out.println(s);
//        String hex = MD5.create().digestHex("1234569");
//        System.out.println(hex);
//        String paw= MD5.create().digestHex("guang20010427");
//        System.out.println(paw);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("7777777");
        System.out.println(encode);
    }
    @Test
    public void testDelete() {
        File file = new File("E:/GGL-ZONE/server-client/target/classes/static/video/彼得·杰克逊/指环王2.mp4");
        System.out.println(file.delete());
    }
    @Test
    public void fileFormatIO() {
        try (FileInputStream fr = new FileInputStream("C:\\Users\\shuguanglin\\Desktop\\test.txt");
                InputStreamReader isr = new InputStreamReader(fr, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);) {
            // char[] str=new char[1024];
            // int count = fr.read(str);
            String str = br.readLine();
            // String s = new String(str);
            int a = 0, b = 0;
            char c = '#';
            StringBuffer sb = new StringBuffer();
            StringBuffer sb1 = new StringBuffer();
            for (int i = 0; i < str.length(); ++i) {
                char c1 = str.charAt(i);
                if (c1 >= 48 && c1 <= 57) {
                    sb.append(String.valueOf(c1));
                } else if (c1 == '=') {
                    b = Integer.valueOf(sb.toString());
                    sb.setLength(0);
                    sb1.append(b);
                    sb1.append("=");
                    int res = 0;
                    switch (c) {
                        case '+':
                            res = a + b;
                            break;
                        case '-':
                            res = a - b;
                            break;
                        case '*':
                            res = a * b;
                            break;
                        case '/':
                            res = a / b;
                            break;
                    }
                    sb1.append(res);
                    System.out.println(sb1.toString());
                    // System.out.println(""+a+c+b+"="+(a+b));
                } else {
                    a = Integer.valueOf(sb.toString());
                    c = c1;
                    sb1.append(a);
                    sb1.append(c);
                    sb.setLength(0);
                }
            }
            // System.out.println(s);

        } catch (Exception e) {
            System.err.println("读取发生异常");
        }
    }
    @Test
    public void testFilea() {
        // System.out.println('随');
        // System.out.println(Charset.defaultCharset().name());
        // List<String> list = new ArrayList<>();
        // System.out.println("你好世界".length());
        // Season s = Season.WINTER;
        // switch (s) {
        //     case SUMMER:
        //     case SPRING:
        //     case AUTUMN:
        //     case WINTER:
        //         System.out.println("原来是冬天啊！");
        //         break;
        // }
        // switch ("hello") {
        //     case "hello":
        //         System.out.println("helloworld");
        //         break;

        //     default:
        //         break;
        // }
    }

    @Test
    public void testNio() {
        try (FileInputStream fis = new FileInputStream("F:\\视频\\指环王1.mp4");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            // byte[] bytes = new byte[] { 1, 5, 6, 3, 9, 7, 5, 3, 2 };
            // String str = "hello nio";
            // ByteBuffer buff = ByteBuffer.allocate(1024);
            // buff.put(str.getBytes());
            // channel.write(buff);
            byte[] bytes = new byte[1024];
            for (int readCount = 1; readCount > 0;) {
                if ((readCount = fis.read(bytes)) > 0) {
                    baos.write(bytes, 0, readCount);
                }
            }
            System.out.println(baos.toByteArray().length);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }

    // public static void main(String[] args) {
        // try (FileInputStream fis = new FileInputStream("F:\\视频\\指环王2.mp4");
        //         BufferedInputStream bis = new BufferedInputStream(fis);
        //         ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
        //     // byte[] bytes = new byte[] { 1, 5, 6, 3, 9, 7, 5, 3, 2 };
        //     // String str = "hello nio";
        //     // ByteBuffer buff = ByteBuffer.allocate(1024);
        //     // buff.put(str.getBytes());
        //     // channel.write(buff);
        //     byte[] bytes = new byte[1024];
        //     for (int readCount = 1; readCount > 0;) {
        //         if ((readCount = bis.read(bytes)) > 0) {
        //             baos.write(bytes, 0, readCount);
        //             // System.out.println(readCount);
        //         }
        //     }
        //     System.out.println("总字节数："+baos.toByteArray().length);

        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }
        // Path movie = Paths.get("F:\\视频\\指环王2.mp4");
        // File movie=new File("F:\\视频\\指环王2.mp4");
        // System.out.println(movie.length());
    // }
}
