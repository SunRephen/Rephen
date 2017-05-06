package com.rephen.dao;

import java.util.List;

import com.rephen.domain.UserInfo;

/**
 * 作业人员DAO
 * @author Rephen
 *
 */
public interface UserInfoDAO {
    
    Long insertUserInfo(UserInfo userInfo);
    
    UserInfo selectUserInfoById(Long id);
    
    void updateUserInfo(UserInfo userInfo);
    
    void deleteUserInfo(Long id);
    
    List<UserInfo> selectAllUserInfo();

}
