package com.zls.provider.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zls.api.dto.RedRecordDto;
import com.zls.api.service.IRedPacketService;
import com.zls.api.service.IRedService;
import com.zls.provider.mapper.RedRobRecordMapper;
import com.zls.provider.util.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service(version = "1.0.0",
        interfaceClass = IRedPacketService.class,
        interfaceName = "com.zls.api.service.IRedPacketService")
public class RedPacketServiceImpl implements IRedPacketService {
    //定义日志
    private static final Logger log = LoggerFactory.getLogger(RedPacketServiceImpl.class);

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private IRedService redService;

    private static final String keyPrefix = "redis:red:packet";
    @Override
    public String handOut(RedRecordDto dto) throws Exception {
        if(dto.getTotalPeople() > 0 && dto.getAmountMoney().doubleValue() > 0){
            List<Integer> list = RedPacketUtil.divideRedPacket(dto.getAmountMoney().intValue(), dto.getTotalPeople());

            //红包标识
            String timeStamp = String.valueOf(System.nanoTime());
            //根据缓存key的前缀与其他信息拼接成一个用于存储随机金额的列表唯一key

            String redId = new StringBuffer(keyPrefix).append(dto.getUserId()).append(":").append(timeStamp).toString().trim();

            //存入redis
            redisTemplate.opsForList().leftPushAll(redId, list.toArray());

            //根据缓存的key的前缀与其他信息拼接成一个存储红包总数的key
            String redTotalKey = redId + ":total";

            redisTemplate.opsForValue().set(redTotalKey, dto.getTotalPeople());

            //把红包个数随机列表存到数据库中
            redService.recordRedPacket(dto, redId, list);

            return redId;
        }else {
            log.error("系统异常-分发红包-参数不合法！");
            throw new Exception("系统异常-分发红包-参数不合法！");
        }
    }

    @Override
    public BigDecimal robLock(Integer userId, String redId) throws Exception {
        //获取redis的操作对象
        ValueOperations valueOperations = redisTemplate.opsForValue();

        //判断是否抢过
        Object obj = valueOperations.get(redId + userId + ":rob");
        if(obj != null){
            return new BigDecimal(obj.toString());
        }

        boolean flag = click(redId);
        try {
            if(flag){
                //锁标记
                final String lockKey = redId + userId + "-lock";
                boolean lock = valueOperations.setIfAbsent(lockKey, redId, 24, TimeUnit.HOURS);

                if(lock){
                    Object value = redisTemplate.opsForList().rightPop(redId);
                    if(value != null){
                        String redTotalKey = redId + ":total";
                        Integer currTotal = valueOperations.get(redTotalKey) != null ? (Integer) valueOperations.get(redTotalKey) : 0;
                        //valueOperations.set(redTotalKey,currTotal-1);//不是原子操作，会出现线程竞技问题

                        valueOperations.decrement(redTotalKey);//原子操作，不会出现竞技问题

                        //红包
                        Double amount = new Double(value.toString());
                        redService.recordRedPacket(userId, redId, amount);

                        valueOperations.set(redId + userId + ":rob", amount, 24, TimeUnit.HOURS);

                        //打印日志，抢到的红包记录
                        log.info("当前用户抢到红包了:userId={} key={} 金额={}", userId, redId, amount);
                        return new BigDecimal(amount);
                    }
                }
            }
        }catch(Exception e){
            log.error("系统异常-抢红包-加分布式锁失败！",e.fillInStackTrace());
            throw new Exception("系统异常-抢红包-加分布式锁失败");
        }

        return new BigDecimal(0);


    }

    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        //获取redis的操作对象
        ValueOperations valueOperations = redisTemplate.opsForValue();

        //判断是否抢过
        Object obj = valueOperations.get(redId + userId + ":rob");
        if(obj != null){
            return new BigDecimal(obj.toString());
        }

        boolean flag = click(redId);
        try {
            if(flag){
                Object value = redisTemplate.opsForList().rightPop(redId);
                if(value != null){
                    String redTotalKey = redId + ":total";
                    Integer currTotal = valueOperations.get(redTotalKey) != null ? (Integer) valueOperations.get(redTotalKey) : 0;
                    //valueOperations.set(redTotalKey,currTotal-1);//不是原子操作，会出现线程竞技问题

                    valueOperations.decrement(redTotalKey);//原子操作，不会出现竞技问题

                    //红包
                    Double amount = new Double(value.toString());
                    redService.recordRedPacket(userId, redId, amount);

                    valueOperations.set(redId + userId + ":rob", amount, 24, TimeUnit.HOURS);

                    //打印日志，抢到的红包记录
                    log.info("当前用户抢到红包了:userId={} key={} 金额={}", userId, redId, amount);
                    return new BigDecimal(amount);
                }

            }
        }catch(Exception e){
            log.error("系统异常-抢红包-加分布式锁失败！",e.fillInStackTrace());
            throw new Exception("系统异常-抢红包-加分布式锁失败");
        }

        return new BigDecimal(0);
    }

    public boolean click(String redId){
        ValueOperations valueOperations = redisTemplate.opsForValue();

        String redTotalKey = redId + ":total";
        Object tol = valueOperations.get(redTotalKey);
        if(tol != null && Integer.valueOf(tol.toString()) > 0){
            return true;
        }
        return false;
    }

}
