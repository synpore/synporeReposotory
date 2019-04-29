package com.synpore.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributedLockDemon {



    @DistributeLock(pre = "order-lock",key="#order.id")
    public void order(Order order){
        //todo
    }
    @DistributeLock(pre = "handleBusiness",key="#businessId")
    public void handleBusiness(String businessId,String memo){
        //todo
    }
}


@AllArgsConstructor
@Data
class Order{
    private Long id;
    private String userId;
    private String address;
}