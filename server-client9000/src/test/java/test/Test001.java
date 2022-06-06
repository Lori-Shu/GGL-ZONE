package test;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

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
    public void testDelete(){
        File file = new File("E:/GGL-ZONE/server-client/target/classes/static/video/彼得·杰克逊/指环王2.mp4");
        System.out.println(file.delete());
    }
}
