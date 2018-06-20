package com.frank.entity.mysql;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    @Column(name = "phone")
    private Long phone;

    @Column(name = "create_at")
    private Date createAt;


   @Column(name = "update_at")
    private Date updateAt;

   public static User generateUser(){
       User user = new User();
       user.setAge(RandomUtils.nextInt(15,80));
       user.setCreateAt(new Date());
       user.setUpdateAt(new Date());
       user.setId(RandomUtils.nextLong());
       user.setUserType(RandomUtils.nextInt(1,3));
       return user;
   }


   /*  @PreUpdate
    public void updateTime(){
      //  this.updateAt = new Timestamp(System.currentTimeMillis());
        log.info("updateTime in");
        this.setUpdateAt(new Date());
        log.info("updateTime user={}",this);
    }
*/

}
