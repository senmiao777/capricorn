package com.frank.entity.mysql;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@Entity
@ToString
@Table(name = "namespace_error_total")
public class NamespaceErrorTotal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 项目名称
     */
    @Column(name = "namespace")
    private String namespace;

    /**
     * 异常类型
     */
    @Column(name = "error_type")
    private String error_type;


    /**
     * 该异常发生次数
     */
    @Column(name = "error_count")
    private String error_count;

    /**
     * 日期，格式yyyyMMdd
     */
    @Column(name = "dt")
    private Integer dt;
}
