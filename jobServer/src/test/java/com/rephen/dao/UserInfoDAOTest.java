package com.rephen.dao;

import org.junit.Before;
import org.junit.Test;

import com.rephen.basic.BeanFactory;
import com.rephen.domain.UserInfo;

public class UserInfoDAOTest {
    private UserInfoDAO userInfoDAO;

    @Before
    public void setUp() {
        userInfoDAO = (UserInfoDAO) BeanFactory.getBean("userInfoDAO");
    }
    
    @Test
    public void insertTest(){
        UserInfo userInfo = new UserInfo();
        userInfo.setMailAddress("test");
        userInfo.setPassword("test");
        userInfo.setUsername("test");
        userInfoDAO.insertUserInfo(userInfo);
    }
    
    @Test
    public void selectTest(){
        System.out.println(userInfoDAO.selectUserInfoById(3L).getMailAddress());
        
    }
    
    @Test
    public void updateTest(){
        UserInfo userInfo = userInfoDAO.selectUserInfoById(1L);
        userInfo.setMailAddress("1312321312");
        userInfoDAO.updateUserInfo(userInfo);
        
    }
    
    @Test
    public void deleteTest(){
        userInfoDAO.deleteUserInfo(1L);
    }

}
