package com.frank.entity.mysql;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/18 0018 下午 4:31
 */
public class BlindDateComment {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 地址
     */
    @Column(name = "area")
    private String area;

    /**
     * 职业
     */
    @Column(name = "occupation")
    private String occupation;

    /**
     * 对对方要求
     */
    @Column(name = "requirement")
    private String requirement;

    /**
     * 评论原始信息
     */
    @Column(name = "content")
    private String content;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 昵称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户连接
     */
    private String url_token;

    /**
     * 头像链接
     */
    @Column(name = "head_image")
    private String headImage;

    /**
     * 本次评论所填写性别
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 注册时填写性别
     */
    @Column(name = "register_gender")
    private Integer registerGender;

    /**
     * 注册时间
     */
    @Column(name = "register_at")
    private Long registerAt;

    /**
     * 自我描述
     */
    @Column(name = "headline")
    private String headline;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
