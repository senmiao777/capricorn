package com.frank.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/banner")
    public String banner(){
        //  map.put("hello","hello");
        return"/view/banner";
    }
}
