package com.frank.controller;

import com.frank.model.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/rabbit")
public class RabbitController {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @ApiOperation(value = "调用此接口发送信息到队列", notes = "这是展示在详情里的信息。根据传入的股票代码查询该股票的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "send", name = "msg", value = "队列内容信息", required = true, dataType = "String")
    })
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public JsonResult send(@RequestParam String msg) {
        log.info("send msg={}", msg);
        rabbitTemplate.convertAndSend(msg);
        return JsonResult.buildSuccessResult(msg);
    }
}
