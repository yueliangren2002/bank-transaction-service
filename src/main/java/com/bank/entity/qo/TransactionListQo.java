package com.bank.entity.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 交易请求实体
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionListQo {
    //当前页页码,从1开始
    private int pageNum;

    //分页大小
    private int pageSize;

    //主键列表
    private List<Long> ids;
}
