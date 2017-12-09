package com.frank.entity.mysql;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 用户基本信息
 */
@Entity
@Table(name = "user_info")
@Data
@Getter
@Setter
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = -3622354500982417427L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "age")
    private Integer age;

    @CreatedDate
    @Column(name = "create_at")
    private Timestamp createAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private Timestamp updateAt;


}
