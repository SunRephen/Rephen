package com.rephen.dao.impl;

import java.util.List;

import com.rephen.dao.GenericDAO;
import com.rephen.dao.JobExcuteResultDAO;
import com.rephen.domain.JobExcuteResult;

public class JobExcuteResultDAOImpl extends GenericDAO<JobExcuteResult> implements JobExcuteResultDAO {

    @Override
    public void insertJobResultDAO(JobExcuteResult excuteResult) {
        super.insert("insertJobResultDAO", excuteResult);

    }

    @Override
    public List<JobExcuteResult> selectJobExcuteResultsByJobId(long jobId) {
        return super.selectList("selectJobExcuteResultsByJobId", jobId);
    }

}
