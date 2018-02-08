package com.frank.entity.mysql;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;



@Data
@Slf4j
public class TestUser implements Serializable {

    private String userName;

    private Integer age;

    private Long phone;

}
