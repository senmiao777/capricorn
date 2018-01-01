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
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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
    private static final List<String> EXT = Arrays.asList("mp3", "pdf", "doc", "docx", "xls", "xlsx", "jpg", "png", "bmp", "jpeg", "mpeg", "mpg", "wps", "et", "swf", "flv");

    @RequestMapping("/banner")
    public String banner() {
        //  map.put("hello","hello");
        return "/view/banner";
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public void uploadImg(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        Iterator<String> it = multipartRequest.getFileNames();
        while (it.hasNext()) {
            MultipartFile file = multipartRequest.getFile(it.next());
            // 扩展名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            response.setContentType("text/html;charset=UTF-8");
            // 验证扩展名
            if (!EXT.contains(extension)) {
                if (log.isWarnEnabled()) {
                    log.warn(String.format("upload Filedata extension error %s", extension));
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

                String filePath = "D:/file/image/" + originalFilename;
                File imageFile = new File(filePath);
                //创建输出流
                FileOutputStream outStream = new FileOutputStream(imageFile);
                //写入数据
                outStream.write(file.getBytes());
                //关闭输出流
                outStream.close();
                response.getWriter().write("{\"res\":\"图片上传成功！\",\"path\":\"" + "http://localhost:8080/file/getImg?fileName="+ originalFilename+ "&t="+System.currentTimeMillis()+"\"}");

            } catch (Exception e) {
                e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error(String.format("upload file FastdfsUtil error "), e);
                }
            }
        }
    }

    //@ResponseBody
    @RequestMapping(value = "/getImg", method = RequestMethod.GET)
    public void getImg(String fileName,String t, HttpServletResponse response, Model model) throws Exception {
        String imgPath = "D:/file/image/" + fileName;
        //读取本地图片输入流
        FileInputStream inputStream = new FileInputStream(imgPath);
        int i = inputStream.available();
        //byte数组用于存放图片字节数据
        byte[] buff = new byte[i];
        inputStream.read(buff);
        //记得关闭输入流
        inputStream.close();
        //设置发送到客户端的响应内容类型
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        out.write(buff);
        //关闭响应输出流
        out.close();
        //  return toByteArray2(path);
       /* ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            //String imgPath = Constans.FOLDER_IMAGE + imgName;
            ips = new FileInputStream(new File(imgPath));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
        return null;*/

    }

    public static byte[] toByteArray2(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] toByteArray3(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
