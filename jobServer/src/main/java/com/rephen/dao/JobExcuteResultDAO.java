package com.rephen.dao;

import java.util.List;

import com.rephen.domain.JobExcuteResult;

public interface JobExcuteResultDAO {
    
    /**
     * 新增一条Job执行结果
     */
    void insertJobResultDAO(JobExcuteResult excuteResult);
    
    /**
     * 根据job id查询执行结果
     * @param jobId
     * @return
     */
    List<JobExcuteResult> selectJobExcuteResultsByJobId(long jobId);

}
