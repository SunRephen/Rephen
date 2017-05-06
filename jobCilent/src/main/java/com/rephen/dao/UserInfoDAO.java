package com.rephen.dao;

import com.rephen.domain.UserInfo;

/**
 * 作业人员DAO
 * @author Rephen
 *
 */
public interface UserInfoDAO {
    
    UserInfo selectUserInfoById(Long id);
    
    void updateUserInfo(UserInfo userInfo);
    
    UserInfo selectUserInfoByUsername(String username);
    

}
