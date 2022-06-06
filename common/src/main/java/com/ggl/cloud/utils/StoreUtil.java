package com.ggl.cloud.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class StoreUtil {
    public static String storeFile(File uploadFile, File fileToStore) {
        
        try (FileInputStream fis=new FileInputStream(uploadFile); 
        FileChannel fisChannel=fis.getChannel();
        FileOutputStream fos=new FileOutputStream(fileToStore);
        FileChannel fosChannel=fos.getChannel();){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int redaContent = fisChannel.read(byteBuffer);
            while (redaContent > 0) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    fosChannel.write(byteBuffer);
                }
                byteBuffer.clear();
                redaContent = fisChannel.read(byteBuffer);
            }
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
            throw new IOException( "此文件不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除出现异常！";
    }
}
