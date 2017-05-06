package com.rephen.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rephen.basic.BeanFactory;
import com.rephen.domain.BasicQuartzJob;


public class BasicQuartzJobDAOTest {
    
    private BasicQuartzJobDAO basicQuartzJobDAO;
    
    @Before
    public void setUp() {
        basicQuartzJobDAO = (BasicQuartzJobDAO)BeanFactory.getBean("basicQuartzJobDAO");
    }
    
    @Test
    public void insertJob(){
        BasicQuartzJob job = new BasicQuartzJob();
        job.setStatus(0);
        job.setIsHide(1);
        job.setDescription("测试");
        job.setJobClass("class");
        job.setJobArguments("arguments");
        job.setJobMethod("method");
        job.setJobName("name");
        job.setCronExpression("123");
        job.setUserId(1L);
        basicQuartzJobDAO.insertJob(job);
    }
    
    
    @Test
    public void updateJobConfig(){
        List<BasicQuartzJob> jobConfigs = basicQuartzJobDAO.selectJobByUserId(1L);
        BasicQuartzJob job = jobConfigs.get(0);
        job.setDescription("ssssssss");
        basicQuartzJobDAO.updateJob(job);
    }
    

}
