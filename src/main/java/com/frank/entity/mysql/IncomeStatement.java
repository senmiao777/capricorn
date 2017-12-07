package com.frank.entity.mysql;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 合并利润表
 * getFdmtIS
 */
@Data
@Entity
@Table(name = "income_statement")
@Getter
@Setter
public class IncomeStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 证券内部ID
     */
    @Column(name = "secID")
    private String secID;
    /**
     * 发布日期
     */
    @Column(name = "publishDate")
    private Integer publishDate;
    /**
     * 截止日期
     */
    @Column(name = "endDate")
    private Integer endDate;
    /**
     * 报表截止日期
     */
    @Column(name = "endDateRep")
    private Integer endDateRep;
    /**
     * 机构内部ID
     */
    @Column(name = "partyID")
    private Integer partyID;
    /**
     * 股票代码
     */
    @Column(name = "ticker")
    private String ticker;
    /**
     * 证券简称
     */
    @Column(name = "secShortName")
    private String secShortName;
    /**
     * 通联编制的证券市场编码。例如，XSHG-上海证券交易所；XSHE-深圳证券交易所等。对应getSysCode.codeTypeID=10002。	查看参数可选值
     */
    @Column(name = "exchangeCD")
    private String exchangeCD;
    /**
     * 实际披露时间
     */
    @Column(name = "actPubtime")
    private Date actPubtime;
    /**
     * 合并类型。1-合并,2-母公司。对应getSysCode.codeTypeID=70003。	查看参数可选值
     */
    @Column(name = "mergedFlag")
    private String mergedFlag;
    /**
     * 报告类型。Q1-第一季报，S1-半年报，Q3-第三季报，CQ3-三季报（累计1-9月），A-年报。对应getSysCode.codeTypeID=70001。	查看参数可选值
     */
    @Column(name = "reportType")
    private String reportType;
    /**
     * 会计期间
     */
    @Column(name = "fiscalPeriod")
    private String fiscalPeriod;
    /**
     * 会计准则
     */
    @Column(name = "accoutingStandards")
    private String accoutingStandards;
    /**
     * 货币代码。例如，USD-美元；CAD-加元等。对应getSysCode.codeTypeID=10004。	查看参数可选值
     */
    @Column(name = "currencyCD")
    private String currencyCD;
    /**
     * 营业总收入
     */
    @Column(name = "tRevenue")
    private BigDecimal tRevenue;
    /**
     * 营业收入
     */
    @Column(name = "revenue")
    private BigDecimal revenue;
    /**
     * 利息收入
     */
    @Column(name = "intIncome")
    private BigDecimal intIncome;
    /**
     * 利息支出
     */
    @Column(name = "intExp")
    private BigDecimal intExp;
    /**
     * 已赚保费
     */
    @Column(name = "premEarned")
    private BigDecimal premEarned;
    /**
     * 手续费及佣金收入
     */
    @Column(name = "commisIncome")
    private BigDecimal commisIncome;
    /**
     * 手续费及佣金支出
     */
    @Column(name = "commisExp")
    private BigDecimal commisExp;
    /**
     * 营业总成本
     */
    @Column(name = "TCogs")
    private BigDecimal TCogs;
    /**
     * 营业成本
     */
    @Column(name = "COGS")
    private BigDecimal COGS;
    /**
     * 退保金
     */
    @Column(name = "premRefund")
    private BigDecimal premRefund;
    /**
     * 赔付支出净额
     */
    @Column(name = "NCompensPayout")
    private BigDecimal NCompensPayout;
    /**
     * 提取保险合同准备金净额
     */
    @Column(name = "reserInsurContr")
    private BigDecimal reserInsurContr;
    /**
     * 保单红利支出
     */
    @Column(name = "policyDivPayt")
    private BigDecimal policyDivPayt;
    /**
     * 分保费用
     */
    @Column(name = "reinsurExp")
    private BigDecimal reinsurExp;
    /**
     * 营业税金及附加
     */
    @Column(name = "bizTaxSurchg")
    private BigDecimal bizTaxSurchg;
    /**
     * 销售费用
     */
    @Column(name = "sellExp")
    private BigDecimal sellExp;
    /**
     * 管理费用
     */
    @Column(name = "adminExp")
    private BigDecimal adminExp;
    /**
     * 财务费用
     */
    @Column(name = "finanExp")
    private BigDecimal finanExp;
    /**
     * 资产减值损失
     */
    @Column(name = "assetsImpairLoss")
    private BigDecimal assetsImpairLoss;
    /**
     * 公允价值变动收益
     */
    @Column(name = "fValueChgGain")
    private BigDecimal fValueChgGain;
    /**
     * 投资收益
     */
    @Column(name = "investIncome")
    private BigDecimal investIncome;
    /**
     * 其中:对联营企业和合营企业的投资收益
     */
    @Column(name = "AJInvestIncome")
    private BigDecimal AJInvestIncome;
    /**
     * 汇兑收益
     */
    @Column(name = "forexGain")
    private BigDecimal forexGain;
    /**
     * 营业利润
     */
    @Column(name = "operateProfit")
    private BigDecimal operateProfit;
    /**
     * 营业外收入
     */
    @Column(name = "NoperateIncome")
    private BigDecimal NoperateIncome;
    /**
     * 营业外支出
     */
    @Column(name = "NoperateExp")
    private BigDecimal NoperateExp;
    /**
     * 非流动资产处置损失
     */
    @Column(name = "NCADisploss")
    private BigDecimal NCADisploss;
    /**
     * 利润总额
     */
    @Column(name = "TProfit")
    private BigDecimal TProfit;
    /**
     * 所得税费用
     */
    @Column(name = "incomeTax")
    private BigDecimal incomeTax;
    /**
     * 净利润
     */
    @Column(name = "NIncome")
    private BigDecimal NIncome;
    /**
     * 归属于母公司所有者的净利润
     */
    @Column(name = "NIncomeAttrP")
    private BigDecimal NIncomeAttrP;
    /**
     * 少数股东损益
     */
    @Column(name = "minorityGain")
    private BigDecimal minorityGain;
    /**
     * 基本每股收益
     */
    @Column(name = "basicEPS")
    private BigDecimal basicEPS;
    /**
     * 稀释每股收益
     */
    @Column(name = "dilutedEPS")
    private BigDecimal dilutedEPS;
    /**
     * 其他综合收益
     */
    @Column(name = "othComprIncome")
    private BigDecimal othComprIncome;
    /**
     * 综合收益总额
     */
    @Column(name = "TComprIncome")
    private BigDecimal TComprIncome;
    /**
     * 归属于母公司所有者的综合收益总额
     */
    @Column(name = "comprIncAttrP")
    private BigDecimal comprIncAttrP;
    /**
     * 归属于少数股东的综合收益总额
     */
    @Column(name = "comprIncAttrMS")
    private BigDecimal comprIncAttrMS;




    public void setPublishDate(String publishDate) {
        String s = StringUtils.replaceAll(publishDate, "-", "");
        this.publishDate = Integer.valueOf(s);
    }

    public void setEndDate(String endDate) {
        String s = StringUtils.replaceAll(endDate, "-", "");
        this.endDate = Integer.valueOf(s);
    }

    public void setEndDateRep(String endDateRep) {
        String s = StringUtils.replaceAll(endDateRep, "-", "");
        this.endDateRep = Integer.valueOf(s);
    }

}
