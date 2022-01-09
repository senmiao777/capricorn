package com.frank.entity.mysql;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import javax.persistence.*;
import java.util.Date;

@Slf4j
@Data
@Entity
@ToString
@Table(name = "benefit")
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 证券内部ID
     */
    @Column(name = "sec_id")
    @JSONField(name = "secID")
    private String secId;

    /**
     * 发布日期,格式
     */
    @Column(name = "publish_date")
    private Date publishDate;

    /**
     * 截止日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 报表截止日期
     */
    @Column(name = "end_date_rep")
    private Date endDateRep;

    /**
     * 机构内部ID
     */
    @Column(name = "party_id")
    @JSONField(name = "partyID")
    private String partyId;


    public static Benefit generateBenefit() {
        Benefit benefit = new Benefit();
        benefit.setSecId("secid:".concat(String.valueOf(RandomUtils.nextInt())));
        benefit.setEndDate(new Date());
        benefit.setPartyId("partid:".concat(String.valueOf(RandomUtils.nextInt())));
        return benefit;
    }

}
