package com.frank.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.frank.entity.mysql.IncomeStatement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Result {
    /**     *   "retCode": 1,

     "retMsg": "some requested data is out of your trial date range",
     "data": [
     */
    private int retCode;
    private String retMsg;
    @JSONField(name = "data")
    private List<IncomeStatement> incomeStatementList;
}
