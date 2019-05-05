package com.frank.designpattern.command;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: somebody
 * @time: 2019-04-29 13:51
 * 为了简单直接就写空调类了，不写接口了
 */
@Slf4j
public class Airconditioner {
    public Airconditioner(String brand) {
        this.brand = brand;
    }

    /**
     * brand 	英 [brænd] 美 [brænd]
     * n. 品牌; 类型; 烙印(尤指农场牲畜身上表示所属的印记)
     */
    private String brand;

    public void on() {
        log.info("{}-空调开启", this.brand);
    }
}
