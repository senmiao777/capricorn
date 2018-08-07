package com.frank.entity.es;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 *股票基本信息
 */
@Data
//@Document
public class Stock {
    /**
     * NAME
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * NAME
     */
    @Column(name = "code")
    private String code;

    /**
     * NAME
     */
    @Column(name = "name")
    private String name;

    /**
     * NAME
     */
    @Column(name = "industry")
    private String industry;

    /**
     * NAME
     */
    @Column(name = "area")
    private String area;

    /**
     * NAME
     */
    @Column(name = "pe")
    private BigDecimal pe;

    /**
     * NAME
     */
    @Column(name = "outstanding")
    private BigDecimal outstanding;

    /**
     * NAME
     */
    @Column(name = "totals")
    private BigDecimal totals;

    /**
     * NAME
     */
    @Column(name = "total_assets")
    private BigDecimal totalAssets;

    /**
     * NAME
     */
    @Column(name = "liquid_assets")
    private BigDecimal liquidAssets;

    /**
     * NAME
     */
    @Column(name = "fixed_assets")
    private BigDecimal fixed_assets;

    /**
     * NAME
     */
    @Column(name = "reserved")
    private BigDecimal reserved;

    /**
     * NAME
     */
    @Column(name = "reserved_per_share")
    private BigDecimal reservedPerShare;

    /**
     * NAME
     */
    @Column(name = "esp")
    private BigDecimal esp;

    /**
     * NAME
     */
    @Column(name = "bvps")
    private BigDecimal bvps;

    /**
     * NAME
     */
    @Column(name = "pb")
    private BigDecimal pb;

    /**
     * NAME
     */
    @Column(name = "time_to_market")
    private Integer timeToMarket;

    /**
     * NAME
     */
    @Column(name = "undp")
    private BigDecimal undp;

    /**
     * NAME
     */
    @Column(name = "perundp")
    private BigDecimal perundp;

    /**
     * NAME
     */
    @Column(name = "rev")
    private BigDecimal rev;

    /**
     * NAME
     */
    @Column(name = "profit")
    private BigDecimal profit;

    /**
     * NAME
     */
    @Column(name = "gpr")
    private BigDecimal gpr;

    /**
     * NAME
     */
    @Column(name = "npr")
    private BigDecimal npr;

    /**
     * NAME
     */
    @Column(name = "holders")
    private Integer holders;


}
