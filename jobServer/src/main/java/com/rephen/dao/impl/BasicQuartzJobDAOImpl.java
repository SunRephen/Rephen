package com.rephen.dao.impl;

import java.util.List;

import com.rephen.dao.BasicQuartzJobDAO;
import com.rephen.dao.GenericDAO;
import com.rephen.domain.BasicQuartzJob;
import com.rephen.query.QueryParam;

public class BasicQuartzJobDAOImpl extends GenericDAO<BasicQuartzJob> implements BasicQuartzJobDAO {

    @Override
    public Long insertJob(BasicQuartzJob jobConfig) {
        return (Long) insert("insertJob", jobConfig);
    }

    @Override
    public BasicQuartzJob selectJobById(Long id) {
       return selectOne("selectJobById", id);
    }

    @Override
    public List<BasicQuartzJob> selectJobsByQueryParam(QueryParam param) {
        return selectList("selectJobsByQueryParam", param.toMap());
    }

    @Override
    public void updateJob(BasicQuartzJob jobConfig) {
        update("updateJob", jobConfig);
    }

    @Override
    public List<BasicQuartzJob> selectAllJob() {
        return selectList("selectAllJob");
    }

}
