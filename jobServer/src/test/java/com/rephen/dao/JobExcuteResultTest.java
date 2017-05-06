package com.rephen.dao;

import org.junit.Before;
import org.junit.Test;

import com.rephen.basic.BeanFactory;
import com.rephen.domain.JobExcuteResult;

public class JobExcuteResultTest {
    private JobExcuteResultDAO jobExcuteResultDAO;

    @Before
    public void setUp() {
        jobExcuteResultDAO = (JobExcuteResultDAO) BeanFactory.getBean("jobExcuteResultDAO");
    }
    
    @Test
    public void insertTest(){
        JobExcuteResult result = new JobExcuteResult();
        result.setJobId(4L);
        result.setStatus(0);
        jobExcuteResultDAO.insertJobResultDAO(result);
    }
    
    
    @Test
    public void selectJobExcuteResultTest(){
        System.out.println(jobExcuteResultDAO.selectJobExcuteResultsByJobId(4L).get(0));
    }
}
