package com.rephen.domain;

import java.util.Date;

/***
 * jobConfig 实体类
 * 
 * @author Rephen
 *
 */
public class BasicQuartzJob {

    /**
     * 任务状态为运行
     */
    public static final int JOB_STATUS_ON = 1;

    /**
     * 任务状态为停止
     */
    public static final int JOB_STATUS_OFF = 0;

    private Long id;

    /**
     * job 类名
     */
    private String jobClass;

    /**
     * 所要运行的方法名
     */
    private String jobMethod;

    /**
     * 运行方法所需的参数
     */
    private String jobArguments;

    /**
     * job分组
     */
    private String jobGroup;

    /**
     * job名称
     */
    private String jobName;

    /**
     * 是否运行 1:运行 0未运行
     */
    private Integer status;

    /**
     * 1:隐藏 0：未隐藏
     */
    private Integer isHide;

    /**
     * corn表达式
     */
    private String cronExpression;

    /**
     * Job描述
     */
    private String description;

    private Date createTime;

    private Date updateTime;

    private Long userId;
    
    private String username;
    
    /**
     * 是否是本地任务
     */
    private Boolean local;
    
    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod;
    }

    public String getJobArguments() {
        return jobArguments;
    }

    public void setJobArguments(String jobArguments) {
        this.jobArguments = jobArguments;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }


    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    public String getTriggerName() {
        return "BasicQuartzJob" + ":" + this.getJobName() + ":Trigger";
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 是否在运行
     * 
     * @return
     */
    public boolean isRunning() {
        return status == JOB_STATUS_ON;
    }

}
