package com.zls.provider.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zls.api.dto.RedRecordDto;
import com.zls.api.model.RedDetail;
import com.zls.api.model.RedRecord;
import com.zls.api.model.RedRobRecord;
import com.zls.api.service.IRedPacketService;
import com.zls.api.service.IRedService;
import com.zls.provider.mapper.RedDetailMapper;
import com.zls.provider.mapper.RedRecordMapper;
import com.zls.provider.mapper.RedRobRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service(version = "1.0.0",
        interfaceClass = IRedService.class,
        interfaceName = "com.zls.api.service.IRedService")
public class RedServiceImpl implements IRedService {

    //定义日志
    private static final Logger log = LoggerFactory.getLogger(RedPacketServiceImpl.class);


    @Autowired
    private RedRecordMapper redRecordMapper;
    @Autowired
    private RedDetailMapper redDetailMapper;

    @Autowired
    private RedRobRecordMapper redRobRecordMapper;
    @Transactional(rollbackFor = Exception.class)//事务
    @Async//异步
    @Override
    public void recordRedPacket(RedRecordDto dto, String redId, List<Integer> list) {
        RedRecord redRecord = new RedRecord();

        redRecord.setRedPacket(redId);
        redRecord.setUserId(dto.getUserId());
        redRecord.setAmount(dto.getAmountMoney());
        redRecord.setTotal(dto.getTotalPeople());
        redRecord.setCreateTime(new Date());
        //将信息对象加入到数据库
        redRecordMapper.insertSelective(redRecord);
        //定义每笔红包的实体类
        RedDetail redDetail;
        for(Integer i: list){
            redDetail = new RedDetail();
            redDetail.setRecordId(redRecord.getId());
            redDetail.setAmount(BigDecimal.valueOf(i));
            redDetail.setCreateTime(new Date());
            redDetailMapper.insertSelective(redDetail);
        }
    }

    @Override
    @Async
    public void recordRedPacket(Integer userId, String redId, Double amount){
        RedRobRecord redRobRecord = new RedRobRecord();
        redRobRecord.setUserId(userId);
        redRobRecord.setRedPacket(redId);
        redRobRecord.setAmount(new BigDecimal(amount));
        redRobRecord.setRobTime(new Date());

        redRobRecordMapper.insertSelective(redRobRecord);
    }
}
