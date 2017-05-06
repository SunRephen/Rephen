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
    public void selectTest(){
        System.out.println(userInfoDAO.selectUserInfoById(1L).getMailAddress());
        
    }
    
    @Test
    public void updateTest(){
        UserInfo userInfo = userInfoDAO.selectUserInfoById(1L);
        userInfo.setMailAddress("1312321312");
        userInfoDAO.updateUserInfo(userInfo);
        
    }
    
}
