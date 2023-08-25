package com.zls.provider.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedPacketUtil {
    public static List<Integer> divideRedPacket(Integer totalAmount, Integer totalPeopleNum){
        //用于存储每次生成的小红包随机金额列表，金额单位为分
        List<Integer> amountList = new ArrayList<>();

        if(totalAmount > 0 && totalPeopleNum > 0){
            Integer restAmount = totalAmount;
            Integer restPeopleNum = totalPeopleNum;

            Random random = new Random();

            for(int i = 0; i < totalPeopleNum - 1; i ++){
//                int tmp = restAmount/restPeopleNum * 2;
//                int amount = 1;
//                if(tmp > 0){
//                    amount += random.nextInt(tmp);
//                }
                int amount=random.nextInt(restAmount/restPeopleNum*2)+1;
                restAmount -= amount;
                restPeopleNum --;
                amountList.add(amount);
                //System.out.println(amount);
            }
            amountList.add(restAmount);
            //System.out.println(restAmount);
        }


        return amountList;
    }
}
