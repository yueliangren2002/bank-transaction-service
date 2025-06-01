package com.bank.entity.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

// 交易请求实体，不带参数校验
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionQo {
    private Long id;

    private String type;

    private BigDecimal amount;

    private Date createTime;

    private String desc;
}
