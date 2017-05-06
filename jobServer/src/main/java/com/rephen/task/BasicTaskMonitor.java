package com.rephen.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rephen.domain.BasicQuartzJob;
import com.rephen.domain.UserInfo;

/**
 * 心跳检测
 * @author Rephen
 */
public class BasicTaskMonitor {
    
    private Log log = LogFactory.getLog(BasicTaskMonitor.class);


    private static final String DEFAULT_PROJECT = "DEFAULT";

    public void basicTaskMonitor(String project) {
        final String monitorStr = "%s TASK IS VIABLE";
        log.error(String.format(monitorStr, project));
    }
    
    public static BasicQuartzJob generateJobForDebug() {
        return generateJobForProject(DEFAULT_PROJECT);
    }
    
    public static BasicQuartzJob generateJobForProject(String project) {
        BasicQuartzJob job = new BasicQuartzJob();
        job.setJobClass("basicTaskMonitor");
        job.setJobMethod("basicTaskMonitor");
        job.setJobArguments(project);
        job.setJobGroup("1");
        job.setJobName("basicTaskMonitor");
        job.setStatus(BasicQuartzJob.JOB_STATUS_ON);
        job.setUserId(UserInfo.SA_ID);
        job.setLocal(true);
        job.setCronExpression("0 0/1 * * * ?");
        return job;
    }
    
}
