package com.frank.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2017/12/17 0017 下午 10:04
 * 文件上传，图片展示，流相关操作记录
 */
@Slf4j
@Controller
@RequestMapping(value = "/file")
public class FileController {

    // 支持上传的文件名
    private static final List<String> EXT = Arrays.asList("mp3","pdf","doc","docx","xls","xlsx","jpg","png","bmp","jpeg","mpeg","mpg","wps","et","swf","flv");

    @RequestMapping("/banner")
    public String banner(){
        //  map.put("hello","hello");
        return"/view/banner";
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public void uploadImg(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        Iterator<String> it=multipartRequest.getFileNames();
        while(it.hasNext()){
            MultipartFile file=multipartRequest.getFile(it.next());
            // 扩展名
            String originalFilename =file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            response.setContentType("text/html;charset=UTF-8");
            // 验证扩展名
            if (!EXT.contains(extension)) {
                if (log.isWarnEnabled()) {
                    log.warn(String.format("upload Filedata extension error %s",extension));
                    try {
                        response.getWriter().write("{\"res\":\"不支持的文件格式\"}");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            FileOutputStream fileOut = null;
            try {

                String filePath = "F:/file/image/"+originalFilename;
                File imageFile = new File(filePath);
                //创建输出流
                FileOutputStream outStream = new FileOutputStream(imageFile);
                //写入数据
                outStream.write(file.getBytes());
                //关闭输出流
                outStream.close();
                response.getWriter().write("{\"res\":\"图片上传成功！\",\"path\":\"" +filePath+ "\"}");

            } catch (Exception e) {
                e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error(String.format("upload file FastdfsUtil error "), e);
                }
            }
        }
    }
}
