/*
 * @Author: Lori Shu
 * @Date: 2022-04-23 14:57:24
 * @LastEditors: Lori Shu
 * @LastEditTime: 2022-08-11 17:28:07
 */
package com.ggl.cloud.controller;

import java.io.ByteArrayInputStream;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.feignservice.ResourceFeign;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 *@Author Lori Shu
 *@Date 2022/4/23
 */
@Controller
@RequestMapping("user/download")
@Slf4j
public class DownloadController {
    @Resource
    private ResourceFeign service;

    @Resource
    private ObjectMapper om;


    @PostMapping("music")
    public void downloadMusic(@RequestBody Music music, HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        // log.info(src);
        // try {
        // File file = service.download(music);
        // String fileName = file.getName();
        // log.warn(fileName);
        // HttpHeaders headers = new HttpHeaders();
        // headers.add("Content-Disposition", String.format("attachment;filename=\"%s",
        // fileName));
        // headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        // headers.add("Pragma", "no-cache");
        // headers.add("Expires", "0");
        // headers.add("Last-Modified", new java.util.Date().toString());
        // headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        // return ResponseEntity
        // .ok()
        // .headers(headers)
        // .contentLength(file.length())
        // .contentType(MediaType.parseMediaType("application/octet-stream"))
        // .body(new FileSystemResource(file));
        // }catch (Exception e){
        // e.printStackTrace();
        // }
        // log.error("出现问题");
        // return null;

        // 读到流中
        // InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        byte[] bytes = service.download(music);
        log.warn("数组长度：" + bytes.length);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);) {
            ServletOutputStream outputStream = response.getOutputStream();
            response.reset();
            response.setContentType("application/octet-stream");
            String path = music.getStorePath();
            int lI = path.lastIndexOf("/");
            String filename = path.substring(lI + 1);
            response.addHeader("Content-Disposition", "attachment;filename=" + UriUtils.encode(filename, "UTF-8"));
            byte[] b = new byte[1024];
            int len;
            // 从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
            log.warn("下载完成！");
        } catch (Exception e) {
            // 处理IO异常
            throw e;
        }

    }

    // @PostMapping("video")
    // public void downloadVideo(@RequestParam("video") String videoString,
    // HttpServletResponse response)
    // throws Exception {
    // // log.info(src);
    // Video video = om.readValue(videoString, Video.class);
    // String jobTag = UUID.randomUUID().toString();
    // video.setId(jobTag);
    // // video.setStorePath("C:\\Users\\shuguanglin\\Desktop\\Test.java");
    // Long length = service.download(video);
    // asyncDownloadService.downloadVideoDeamonThread(video, length, response);

    // }

    // 无法实现通过feign传输httpservlet只能通过重定向或者中间件实现较大文件io，如果文件非常非常大（电影）直接前端通过src下载
    // @PostMapping("testHS")
    // public void testHS(@RequestBody Video video, HttpServletRequest request,
    // HttpServletResponse response)
    // throws IOException, ServletException {
    // log.warn("进入测试方法");
    // CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    // HttpPost httpPost = new
    // HttpPost("http://localhost:9992/resource/download/testHS");

    // Enumeration<String> headerNames = request.getHeaderNames();
    // for (; headerNames.hasMoreElements();) {
    // String headerName = headerNames.nextElement();
    // if (headerName.equals("Content-Length")) {
    // continue;
    // }
    // String header = request.getHeader(headerName);
    // httpPost.setHeader(headerName, header);
    // }

    // StringEntity stringEntity = new StringEntity(om.writeValueAsString(video));
    // httpPost.setEntity(stringEntity);
    // httpPost.removeHeaders("Content-Length");
    // CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
    // InputStream content = httpResponse.getEntity().getContent();
    // response.reset();
    // response.setContentType("application/octet-stream");
    // // String path = video.getStorePath();
    // // int lI = path.lastIndexOf("/");
    // // String filename = path.substring(lI + 1);
    // response.addHeader("Content-Disposition", "attachment;filename=" +
    // UriUtils.encode("java.mp4", "UTF-8"));
    // ServletOutputStream os = response.getOutputStream();
    // byte[] bytes = new byte[1024];
    // for (int readCount = 1; readCount > 0;) {
    // readCount = content.read(bytes);
    // if (readCount > 0) {
    // os.write(bytes, 0, readCount);
    // }
    // }
    // log.warn("io结束");
    // }

}
