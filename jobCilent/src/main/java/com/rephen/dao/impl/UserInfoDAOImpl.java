package com.rephen.dao.impl;

import com.rephen.dao.GenericDAO;
import com.rephen.dao.UserInfoDAO;
import com.rephen.domain.UserInfo;

public class UserInfoDAOImpl extends GenericDAO<UserInfo> implements UserInfoDAO{

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        update("updateUserInfo", userInfo);
        
    }


    @Override
    public UserInfo selectUserInfoById(Long id) {
        return selectOne("selectUserInfoById", id);
    }


    @Override
    public UserInfo selectUserInfoByUsername(String username) {
        return selectOne("selectUserInfoByUsername", username);
    }


}
