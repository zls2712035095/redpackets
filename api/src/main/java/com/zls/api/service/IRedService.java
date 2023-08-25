package com.zls.api.service;

import com.zls.api.dto.RedRecordDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IRedService {
    void recordRedPacket(RedRecordDto dto, String redId, List<Integer> list);
    void recordRedPacket(Integer userId, String redId, Double amount);
}
