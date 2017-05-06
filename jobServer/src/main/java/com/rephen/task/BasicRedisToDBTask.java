package com.rephen.task;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rephen.dao.JobExcuteResultDAO;
import com.rephen.domain.BasicQuartzJob;
import com.rephen.domain.JobExcuteResult;
import com.rephen.domain.UserInfo;
import com.rephen.util.JedisUtil;

/**
 * job执行情况从redis写入DB
 * @author Rephen
 *
 */
public class BasicRedisToDBTask {
    
    private Log log = LogFactory.getLog(BasicRedisToDBTask.class);
    
    @Resource
    private JedisUtil jedisUtil;
    
    @Resource
    private JobExcuteResultDAO jobExcuteResultDAO;
    
    private static final String JOB = "job";

    public void basicRedisToDBTask() {
        log.error("redis内job执行结果数据写入DB作业-----开始");
        // 获取redis内成功执行过的job数量
        Long count = jedisUtil.scount(JOB);
        
        if(null != count && count>0L){
            Set<String> jobIds = jedisUtil.sget(JOB);
            Long jobId = null;
            for(String idStr:jobIds){
                jobId = NumberUtils.toLong(idStr);
                // 获取该job每次执行耗时
                Set<String> useTimes = jedisUtil.zgetMember(JOB+"_"+jobId, 0, -1);
                // 日期double格式
                Double createTimeNum = null;
                // 日期
                Date createTime = null;
                
                JobExcuteResult result = null;
                for(String useTimeStr:useTimes){
                    if(NumberUtils.isNumber(useTimeStr.split("_")[0])){
                        // createTime
                        createTimeNum = jedisUtil.zgetScore(JOB+"_"+jobId, useTimeStr);
                        
                        createTime = new Date(createTimeNum.longValue());
                        
                        result = new JobExcuteResult();
                        result.setCreateTime(createTime);
                        result.setJobId(jobId);
                        result.setStatus(1);
                        result.setUseTime(NumberUtils.toLong(useTimeStr.split("_")[0]));
                        jobExcuteResultDAO.insertJobResultDAO(result);
                    }
                }
                jedisUtil.expireimmediately(JOB+"_"+jobId);
            }
            
            jedisUtil.expireimmediately(JOB);
            log.error(count+" 条成功执行结果写入DB");
        }else{
            log.error("未获取到job成功执行的结果");
        }
        
       
    }
    
    public static BasicQuartzJob getBasicRedisToDBTask() {
        return generateJob();
    }
    
    public static BasicQuartzJob generateJob() {
        BasicQuartzJob job = new BasicQuartzJob();
        job.setJobClass("basicRedisToDBTask");
        job.setJobMethod("basicRedisToDBTask");
        job.setJobArguments(null);
        job.setJobGroup("1");
        job.setJobName("basicRedisToDBTask");
        job.setUserId(UserInfo.SA_ID);
        job.setStatus(BasicQuartzJob.JOB_STATUS_ON);
        job.setLocal(true);
        // TODO 更改时间
        //job.setCronExpression("0 0/3 * * * ?");
        job.setCronExpression("0 50 23 * * ?");
        return job;
    }

}
