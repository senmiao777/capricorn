package com.frank.entity.es;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/27 0027 下午 9:34
 */

/**
 * indexName = "数据库名",type = "表名"
 */
//@Document(indexName = "learn",type = "blinddate")
@Data
public class BlindDate implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 地址
     */
    private String area;

    /**
     * 学历
     */
    private String education;

    /**
     * 职业
     */
    private String age;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 对对方要求
     */
    private String requirement;

    /**
     * 评论原始信息
     */
    private String content;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 用户连接
     */
    private String urlToken;

    /**
     * 头像链接
     */
    private String headImage;

    /**
     * 本次评论所填写性别
     */
    private String gender;

    /**
     * 注册时填写性别
     */
    private String registerGender;

    /**
     * 注册时间
     */
    private Long registerAt;

    /**
     * 自我描述
     */
    private String headline;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;
}
