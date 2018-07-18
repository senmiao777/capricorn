package com.frank.controller;

import com.frank.repository.mysql.IncomeStatementRepository;
import com.frank.repository.mysql.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * 基本增删改查操作
 * 缺少权限验证
 * 以及各种校验
 */
@Slf4j
@Controller
@RequestMapping(value = "/h")
public class HtmlController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IncomeStatementRepository incomeStatementRepository;

    @Autowired
    private StockRepository stockRepository;

    @RequestMapping("/hello")
    public String helloHtml() {
        //  map.put("hello","hello");
        return "/view/firstEchart";
    }





}
