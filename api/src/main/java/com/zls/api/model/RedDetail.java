package com.zls.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedDetail implements Serializable {
    private Integer id;

    private Integer recordId;

    private BigDecimal amount;

    private Byte isActive;

    private Date createTime;


}