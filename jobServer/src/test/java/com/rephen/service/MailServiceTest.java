package com.rephen.service;

import org.junit.Before;
import org.junit.Test;

import com.rephen.basic.BeanFactory;

public class MailServiceTest {

    private MailService mailService;

    @Before
    public void setUp() {
        mailService = (MailService) BeanFactory.getBean("mailService");
    }
    
    @Test
    public void sendMail(){
        mailService.sendMail(0L, new Exception(), "test", "test");
    }
    
}
