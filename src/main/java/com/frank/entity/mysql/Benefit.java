package com.frank.entity.mysql;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

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

   /* ticker	String	股票代码
    secShortName	String	证券简称
    exchangeCD	String	通联编制的证券市场编码。例如，XSHG-上海证券交易所；XSHE-深圳证券交易所等。对应getSysCode.codeTypeID=10002。	查看参数可选值
    actPubtime	String	实际披露时间
    mergedFlag	String	合并类型。1-合并,2-母公司。对应getSysCode.codeTypeID=70003。	查看参数可选值
    reportType	String	报告类型。Q1-第一季报，S1-半年报，Q3-第三季报，CQ3-三季报（累计1-9月），A-年报。对应getSysCode.codeTypeID=70001。	查看参数可选值
    fiscalPeriod	String	会计期间
    accoutingStandards	String	会计准则
    currencyCD	String	货币代码。例如，USD-美元；CAD-加元等。对应getSysCode.codeTypeID=10004。	查看参数可选值
    tRevenue	Double	营业总收入
    revenue	Double	营业收入
    intIncome	Double	利息收入
    intExp	Double	利息支出
    premEarned	Double	已赚保费
    commisIncome	Double	手续费及佣金收入
    commisExp	Double	手续费及佣金支出
    TCogs	Double	营业总成本
    COGS	Double	营业成本
    premRefund	Double	退保金
    NCompensPayout	Double	赔付支出净额
    reserInsurContr	Double	提取保险合同准备金净额
    policyDivPayt	Double	保单红利支出
    reinsurExp	Double	分保费用
    bizTaxSurchg	Double	营业税金及附加
    sellExp	Double	销售费用
    adminExp	Double	管理费用
    finanExp	Double	财务费用
    assetsImpairLoss	Double	资产减值损失
    fValueChgGain	Double	公允价值变动收益
    investIncome	Double	投资收益
    AJInvestIncome	Double	其中:对联营企业和合营企业的投资收益
    forexGain	Double	汇兑收益
    operateProfit	Double	营业利润
    NoperateIncome	Double	营业外收入
    NoperateExp	Double	营业外支出
    NCADisploss	Double	非流动资产处置损失
    TProfit	Double	利润总额
    incomeTax	Double	所得税费用
    NIncome	Double	净利润
    NIncomeAttrP	Double	归属于母公司所有者的净利润
    minorityGain	Double	少数股东损益
    basicEPS	Double	基本每股收益
    dilutedEPS	Double	稀释每股收益
    othComprIncome	Double	其他综合收益
    TComprIncome	Double	综合收益总额
    comprIncAttrP	Double	归属于母公司所有者的综合收益总额
    comprIncAttrMS	Double	归属于少数股东的综合收益总额*/
}
