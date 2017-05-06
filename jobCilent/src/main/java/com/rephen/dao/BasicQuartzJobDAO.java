package com.rephen.dao;

import java.util.List;

import com.rephen.domain.BasicQuartzJob;

public interface BasicQuartzJobDAO {
    
    /**
     * 新增
     * @param jobConfig
     * @return
     */
    Long insertJob(BasicQuartzJob jobConfig);
    
    /**
     * 更新
     * @param jobConfig
     */
    void updateJob(BasicQuartzJob jobConfig);
    
    /**
     * 根据用户id查询job
     * @param userId
     * @return
     */
    List<BasicQuartzJob> selectJobByUserId(Long userId);
    
    /**
     * 根据主键ID查询
     * @param id
     * @return
     */
    BasicQuartzJob selectJobById(Long id);
    
    /**
     * 删除
     * @param id
     */
    void deleteJobById(Long id);
    

}
