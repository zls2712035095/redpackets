package com.zls.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zls.api.dto.RedRecordDto;
import com.zls.api.enums.StatusCode;
import com.zls.api.respones.BaseResponse;
import com.zls.api.service.IRedPacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class RedPacketController {
    //定义日志
    private static final Logger log = LoggerFactory.getLogger(RedPacketController.class);

    //定义请求路径的资源前缀
    private static final String prefix = "red/packet";

    @Reference(
            version = "1.0.0",
            interfaceClass = IRedPacketService.class,
            interfaceName = "com.zls.api.service.IRedPacketService",
            timeout = 120000
    )
    private IRedPacketService redPacketService;

    @RequestMapping(value = prefix + "/hand/out", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private BaseResponse handOut(@Validated @RequestBody RedRecordDto dto, BindingResult result){

        if(result.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);

        try{

           String redid = redPacketService.handOut(dto);

           response.setData(redid);


        }catch (Exception e){
            log.error("发红包异常:dto={}", dto);
            e.printStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());

        }
        return response;
    }

    @GetMapping(prefix + "/rob")
    public BaseResponse rob(@RequestParam Integer userId, @RequestParam String redId){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {

//            BigDecimal res = redPacketService.rob(userId, redId);
            BigDecimal res = redPacketService.robLock(userId, redId);
            if(res != null){
                response.setData(res);
            }else{
                log.error("红包已经抢完了！");
                response=new BaseResponse(StatusCode.Fail.getCode(),"红包已经抢完了！");
            }
        }catch (Exception e){
            log.error("抢红包发生异常：userId={} redId={}",userId,redId,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),"红包已经抢完了！");
        }
        return response;
    }
}
