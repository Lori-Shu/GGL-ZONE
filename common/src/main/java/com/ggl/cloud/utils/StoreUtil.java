package com.ggl.cloud.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:25:35
 *
 */
@Slf4j
public class StoreUtil {
    public static String storeFile(byte[] uploadBytes, File fileToStore) {
        
        try (ByteArrayInputStream bais = new ByteArrayInputStream(uploadBytes);
                FileOutputStream fos = new FileOutputStream(fileToStore);) {
            byte[] bytes = new byte[1024];
            for (int readCount = 1; readCount > 0;) {
                if ((readCount = bais.read(bytes)) > 0) {
                    fos.write(bytes, 0, readCount);
                }
            }
            log.warn("储存成功");
            return "储存成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "储存文件发生错误";
    }

    public static String deleteFile(String storePath)  {
        try {
            File file = new File(storePath);
//            System.out.println(path);
            if (file.isFile()) {
//                Thread.sleep(5000);
//                System.out.println("删除结果"+deleteRes);
                if (file.delete()) {
                    return "删除成功";
                }
                throw new IOException( "删除失败");
            }
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除出现异常！";
    }
}
