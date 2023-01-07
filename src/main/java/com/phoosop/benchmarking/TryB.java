package com.phoosop.benchmarking;

import org.springframework.stereotype.Component;

@Component
public class TryB {

    public void init() throws InterruptedException {
        Thread.sleep(500);
    }

}
