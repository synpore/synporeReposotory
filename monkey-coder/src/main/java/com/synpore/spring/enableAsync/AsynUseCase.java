package com.synpore.spring.enableAsync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsynUseCase {

    @Async
    public void startMyTreadTask() {
        System.out.println("this is my async task");
    }
}
