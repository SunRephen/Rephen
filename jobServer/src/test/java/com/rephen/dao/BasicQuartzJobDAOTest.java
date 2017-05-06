package com.rephen.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rephen.basic.BeanFactory;
import com.rephen.domain.BasicQuartzJob;
import com.rephen.query.QueryParam;


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
        job.setJobGroup("group");
        job.setJobMethod("method");
        job.setJobName("name");
        job.setCronExpression("123");
        
        basicQuartzJobDAO.insertJob(job);
    }
    
    @Test
    public void selectJobById(){
        System.out.println(basicQuartzJobDAO.selectJobById(1L).getDescription());
    }
    
    @Test
    public void selectJobsByQueryParam(){
        QueryParam param = new QueryParam();
        param.setIsHide(0);
        System.out.println(basicQuartzJobDAO.selectJobsByQueryParam(param).size());
    }
    
    @Test
    public void updateJobConfig(){
        BasicQuartzJob jobConfig = basicQuartzJobDAO.selectJobById(1L);
        jobConfig.setDescription("ssssssss");
        basicQuartzJobDAO.updateJob(jobConfig);
    }
    
    @Test
    public void selectAllJob(){
        List<BasicQuartzJob> list = basicQuartzJobDAO.selectAllJob();
        
        System.out.println(list.size());
    }
    

}
