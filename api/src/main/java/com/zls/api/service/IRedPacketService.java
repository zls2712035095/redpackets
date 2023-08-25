package com.zls.api.service;

import com.zls.api.dto.RedRecordDto;

import java.math.BigDecimal;

public interface IRedPacketService {
    public String handOut(RedRecordDto dto) throws Exception;

    BigDecimal robLock(Integer userId, String redId) throws Exception;

    BigDecimal rob(Integer userId, String redId) throws Exception;
}
