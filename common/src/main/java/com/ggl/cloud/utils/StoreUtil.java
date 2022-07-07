package com.ggl.cloud.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class StoreUtil {
    public static String storeFile(byte[] uploadBytes, File fileToStore) {
        
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(uploadBytes);
        FileOutputStream fos=new FileOutputStream(fileToStore);
        ) {
            byte[] bytes= new byte[1024];
            int readCount=0;
            do{
                if(inputStream.available()>1024){
                    readCount = inputStream.read(bytes, 0, 1024);
                    fos.write(bytes, 0, 1024);
                } else {
                    readCount = inputStream.read(bytes, 0, inputStream.available());
                    fos.write(bytes, 0, inputStream.available());
                }
                
            } while (readCount > 0);
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
