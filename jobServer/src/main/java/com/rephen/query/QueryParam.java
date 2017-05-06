package com.rephen.query;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 查询参数类
 * @author Rephen
 *
 */
public class QueryParam {
    
    private Integer isHide;
    
    private Integer status;
    
    private String jobClass;
    
    private String jobName;
    
    private String jobGroup;
    
    /**
     * 分页查询起点
     */
    private Integer pageNo;
    
    /**
     * 每次查询偏移量
     */
    private Integer pageSize;

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }
    
    
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    
    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 将查询参数封装
     * @return
     */
    public Map<String, Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>(4);
        if(!StringUtils.isBlank(jobClass)){
            map.put("jobClass", this.jobClass);
        }
        if(!StringUtils.isBlank(jobGroup)){
            map.put("jobGroup", this.jobGroup);
        }
        if(!StringUtils.isBlank(jobName)){
            map.put("jobName", this.jobName);
        }
        if(null != status){
            map.put("status", this.status);
        }
        if(null != isHide){
            map.put("isHide", this.isHide);
        }
        if(this.pageSize!=null&&this.pageNo!=null){
            map.put("start", (pageNo-1)*pageSize);
            map.put("size", pageSize);
        }
        return map;
    }

}
