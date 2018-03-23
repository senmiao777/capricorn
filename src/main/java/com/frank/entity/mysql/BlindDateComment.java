package com.frank.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/18 0018 下午 4:31
 */

/**
 * 相亲题目下用户评论
 * 知乎URL：https://www.zhihu.com/question/67228907/answer/251023939
 */
@Data
@Entity
@Table(name = "blind_date_comment")
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
     * 学历
     */
    @Column(name = "user_education")
    private String education;

    /**
     * 职业
     */
    @Column(name = "user_age")
    private String age;

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
    @Column(name = "url_token")
    private String urlToken;

    /**
     * 头像链接
     */
    @Column(name = "head_image")
    private String headImage;

    /**
     * 本次评论所填写性别
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 注册时填写性别
     */
    @Column(name = "register_gender")
    private String registerGender;

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
    @Column(name = "create_at")
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    @Column(name = "update_at")
    private Timestamp updatedAt;
}
