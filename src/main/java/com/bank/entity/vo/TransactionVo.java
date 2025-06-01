package com.bank.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

// 交易请求实体
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionVo implements Serializable {
    private Long id;

    //交易类型不能为空
    private String type;

    //金额
    private BigDecimal amount;

    //交易时间
    private Date createTime;

    //描述
    private String desc;
}
