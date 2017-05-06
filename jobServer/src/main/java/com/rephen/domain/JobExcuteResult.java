package com.rephen.domain;

import java.util.Date;

/**
 * job执行实体类
 * @author Rephen
 *
 */
public class JobExcuteResult {
    
    public static final int SUCCESS=1;
    
    public static final int FAIL=0;
    
    
    /**
     * 主键
     */
    private long id;
    
    /**
     * job id
     */
    private long jobId;
    
    /**
     * 执行状态 0:失败 1:成功
     */
    private int status;
    
    /**
     * 耗时
     */
    private long useTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    public JobExcuteResult(){
        
    }
    
    public JobExcuteResult(long useTime,Date createTime){
        this.createTime = createTime;
        this.useTime = useTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
