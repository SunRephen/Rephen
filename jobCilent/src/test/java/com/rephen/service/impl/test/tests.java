package com.rephen.service.impl.test;

import java.rmi.Naming;

import com.rephen.service.test.RemoteTask;


public class tests {
    
    public static void main(String[] args) throws Exception {
        String url = "rmi://localhost:1099/job.task1";
        RemoteTask helloService = (RemoteTask) Naming.lookup(url);
        String result = helloService.test();
        System.out.println(result);
    }

}
