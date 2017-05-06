package com.rephen.dao.impl;

import java.util.List;

import com.rephen.dao.BasicQuartzJobDAO;
import com.rephen.dao.GenericDAO;
import com.rephen.domain.BasicQuartzJob;

public class BasicQuartzJobDAOImpl extends GenericDAO<BasicQuartzJob> implements BasicQuartzJobDAO {

    @Override
    public Long insertJob(BasicQuartzJob jobConfig) {
        return (Long) insert("insertJob", jobConfig);
    }

    @Override
    public void updateJob(BasicQuartzJob jobConfig) {
        update("updateJob", jobConfig);
    }

    @Override
    public List<BasicQuartzJob> selectJobByUserId(Long userId) {
        return selectList("selectJobByUserId",userId);
    }

    @Override
    public BasicQuartzJob selectJobById(Long id) {
       return selectOne("selectJobById", id);
    }

    @Override
    public void deleteJobById(Long id) {
        delete("deleteJobById", id);
        
    }


}
