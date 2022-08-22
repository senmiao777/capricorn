package com.frank.entity.mysql;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户基本信息
 */
@Entity
@Table(name = "user_info")
@Data
@Getter
@Setter
@ToString
@Slf4j
@AllArgsConstructor

public class User implements Serializable {

    private static final long serialVersionUID = -3622354500982417427L;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "create_at")
    private Date createAt;


    @Column(name = "update_at")
    private Date updateAt;

    public static User generateUser() {
        User user = new User();
        user.setAge(RandomUtils.nextInt(15, 80));
        user.setCreateAt(new Date());
        user.setUpdateAt(new Date());
        //user.setTest("123");
        //user.setId(RandomUtils.nextLong());
        user.setUserName("ceshi".concat(String.valueOf(RandomUtils.nextInt())));
        user.setPhone(RandomUtils.nextLong(13240411000L, 18766192345L));
        user.setUserType(RandomUtils.nextInt(1, 4));
        return user;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(Builder builder) {
        this.userName = builder.userName;
        this.userType = builder.userType;
        this.age = builder.age;
        this.phone = builder.phone;
        this.createAt = builder.createAt;
        this.updateAt = builder.updateAt;
    }


    public static class Builder {

        private Long id;

        private String userName;

        private Integer userType;

        private Integer age;

        private Long phone;

        private Date createAt;

        private Date updateAt;

        public User bulid() {
            /**
             * 参数检查
             */
            return new User(this);
        }


        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder userType(Integer userType) {
            this.userType = userType;
            return this;
        }

        public Builder phone(Long phone) {
            this.phone = phone;
            return this;
        }

        public Builder createAt(Date createAt) {
            this.createAt = createAt;
            return this;
        }

        public Builder updateAt(Date updateAt) {
            this.updateAt = updateAt;
            return this;
        }

        public Long getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public Integer getUserType() {
            return userType;
        }

        public Integer getAge() {
            return age;
        }

        public Long getPhone() {
            return phone;
        }

        public Date getCreateAt() {
            return createAt;
        }

        public Date getUpdateAt() {
            return updateAt;
        }
    }


}
