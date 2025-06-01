package com.bank.entity.qo;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

// 增加交易请求实体
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionAddQo {
    private Long id;

    @NotEmpty(message = "交易类型不能为空")
    private String type;

    @Positive(message = "金额必须为正数")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Size(max = 100, message = "描述不能超过100字符")
    private String desc;
}
