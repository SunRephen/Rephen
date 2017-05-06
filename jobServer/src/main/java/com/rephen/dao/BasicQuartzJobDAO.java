package com.rephen.dao;

import java.util.List;

import com.rephen.domain.BasicQuartzJob;
import com.rephen.query.QueryParam;

public interface BasicQuartzJobDAO {
    
    /**
     * 新增
     * @param jobConfig
     * @return
     */
    Long insertJob(BasicQuartzJob jobConfig);
    
    /**
     * 根据主键ID查询
     * @param id
     * @return
     */
    BasicQuartzJob selectJobById(Long id);
    
    /**
     * 根据查询参数查询
     * @param param
     * @return
     */
    List<BasicQuartzJob> selectJobsByQueryParam(QueryParam param);
    
    /**
     * 更新
     * @param jobConfig
     */
    void updateJob(BasicQuartzJob jobConfig);
    
    /**
     * 查找未隐藏的全部job
     * @return
     */
    List<BasicQuartzJob> selectAllJob();
    
    

}
