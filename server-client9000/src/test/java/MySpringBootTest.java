



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ggl.cloud.ServerClient9000;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.service.IMusicService;

import jakarta.annotation.Resource;
@SpringBootTest(classes = ServerClient9000.class)
public class MySpringBootTest {
    @Resource
    IMusicService service;
    @Test
    public void testTest() {
        System.out.println("测试一下springboot测试");
    }
    @Test
    public void testMPNul() {
        Music res = service.getById("001");
        System.out.println(res);
    }
    @Test
    public void testDataLombok() {
        System.out.println(new Music());
    }
}
