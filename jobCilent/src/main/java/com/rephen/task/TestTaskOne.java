package com.rephen.task;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestTaskOne {
    
    private Log log = LogFactory.getLog(TestTaskOne.class);
    
    @SuppressWarnings("static-access")
    public String run(){
        Random random = new Random(System.currentTimeMillis());
        try {
            Thread thread = Thread.currentThread();
            thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.error("测试redis刷入DB开启");
        return "12321321";
        
    }

}
