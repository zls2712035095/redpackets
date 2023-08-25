package com.zls.api.dto;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RedRecordDto implements Serializable {
    //用户id
    private Integer userId;
    //指定多少人抢
    @NotNull
    private Integer totalPeople;
    //指定总金额 单位为分
    @NotNull
    private BigDecimal amountMoney;
}
