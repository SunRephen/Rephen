package com.rephen.task;

import org.junit.Before;
import org.junit.Test;

import com.rephen.basic.BeanFactory;
import com.rephen.general.service.GeneralJobService;

public class RemoteTaskTest {
    
    private GeneralJobService generalJobService;
    
    @Before
    public void setUp() {
        generalJobService = (GeneralJobService) BeanFactory.getBean("generalJobService");
    }
    
    
    @Test
    public void main(String[] args) {
        
        try {
            generalJobService.excute("TestTaskOne", "run", "");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
