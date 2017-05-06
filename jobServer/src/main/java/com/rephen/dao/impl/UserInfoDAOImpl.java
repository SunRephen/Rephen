package com.rephen.dao.impl;

import java.util.List;

import com.rephen.dao.GenericDAO;
import com.rephen.dao.UserInfoDAO;
import com.rephen.domain.UserInfo;

public class UserInfoDAOImpl extends GenericDAO<UserInfo> implements UserInfoDAO{

    @Override
    public Long insertUserInfo(UserInfo userInfo) {
        return (Long) insert("insertUserInfo", userInfo);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        update("updateUserInfo", userInfo);
        
    }

    @Override
    public void deleteUserInfo(Long id) {
        delete("deleteUserInfo", id);
        
    }

    @Override
    public UserInfo selectUserInfoById(Long id) {
        return selectOne("selectUserInfoById", id);
    }

    @Override
    public List<UserInfo> selectAllUserInfo() {
        return selectList("selectAllUserInfo");
    }

}
