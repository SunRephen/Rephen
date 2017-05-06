package com.rephen.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ApplicationObjectSupport;

import com.rephen.component.JobSaveResult;
import com.rephen.dao.BasicQuartzJobDAO;
import com.rephen.dao.JobExcuteResultDAO;
import com.rephen.domain.BasicQuartzJob;
import com.rephen.domain.JobExcuteResult;
import com.rephen.domain.QuartzJobBean;
import com.rephen.general.service.GeneralJobService;
import com.rephen.query.QueryParam;
import com.rephen.service.MailService;
import com.rephen.util.JedisUtil;



public class BasicScheduler extends ApplicationObjectSupport
        implements ApplicationListener<ApplicationEvent> {

    private final static Log log = LogFactory.getLog(BasicScheduler.class);

    static SchedulerFactory sf = new StdSchedulerFactory();

    @Resource
    private BasicQuartzJobDAO basicQuartzJobDAO;
    
    @Resource
    private JobExcuteResultDAO jobExcuteResultDAO;
    
    @Resource
    private MailService mailService;
    
    @Resource
    private GeneralJobService generalJobService;
    
    @Resource
    private JedisUtil jedisUtil;
    
    public JedisUtil getJedisUtil(){
        return this.jedisUtil;
    }

    private static Scheduler scheduler;
    {
        try {
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ContextRefreshedEvent cre = (ContextRefreshedEvent) event;
            log.error("BasicScheduler Start. " + cre.getApplicationContext().getDisplayName());
            if (cre.getApplicationContext().getParent() == null && cre.getApplicationContext()
                    .getDisplayName().equalsIgnoreCase("Root WebApplicationContext")) {
                // 避免出现重复加载 即只处理根容器的事件
                this.startAll();
            }
        }
    }

    /*
     * 启动全部任务
     */
    public void startAll() {
        try {
            if (log.isInfoEnabled()) {
                log.info("[BasicScheduler]start when init begin.");
            }

            ApplicationContext ac = getApplicationContext();
            QuartzJobBean.setEnv(ac,mailService,this,generalJobService);
            

            if (log.isInfoEnabled()) {
                log.info("[BasicScheduler]get application context.");
            }

            scheduler.start();
            if (log.isInfoEnabled()) {
                log.info("[BasicScheduler]scheduler start.");
            }

            List<BasicQuartzJob> list = basicQuartzJobDAO.selectAllJob();

            if (list != null && !list.isEmpty()) {
                if (log.isInfoEnabled()) {
                    log.info("[BasicScheduler]the size of list for init is " + list.size());
                }
                // 远程作业从数据库中查询，local为false
                for (BasicQuartzJob job : list) {
                    if (BasicQuartzJob.JOB_STATUS_ON == job.getStatus()) {
                        try {
                            job.setLocal(false);
                            this.enable(job);
                        } catch (Exception e) {
                            log.error("[BasicScheduler]error when add job. " + e.getMessage(), e);
                        }
                    }
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("[BasicScheduler]no list for init.");
                }
            }

            // 加入心跳监控
            BasicQuartzJob monitorJob = BasicTaskMonitor.generateJobForDebug();
            
            // 加入redis数据刷入DB task
            BasicQuartzJob redisToDBTask = BasicRedisToDBTask.getBasicRedisToDBTask();
            try {
                this.enable(monitorJob);
                this.enable(redisToDBTask);
            } catch (Exception e) {
                log.error("[BasicScheduler]error when add monitor or redisToDBTask. " + e.getMessage(), e);
            }

            if (log.isInfoEnabled()) {
                log.info("[BasicScheduler]start when init end.");
            }
        } catch (Exception e) {
            log.error("[BasicScheduler]error when init. " + e.getMessage(), e);
        }
    }

    /**
     * 启动指定任务
     * 
     * @param jobConfig
     * @throws Exception
     */
    public void enable(BasicQuartzJob basicQuartzJob) throws Exception {
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(basicQuartzJob.getTriggerName(),
                basicQuartzJob.getJobGroup());
        if (null == trigger) {
            JobDetail jobDetail = new JobDetail(basicQuartzJob.getJobName(),
                    basicQuartzJob.getJobGroup(), QuartzJobBean.class);
            jobDetail.getJobDataMap().put(QuartzJobBean.TARGET_CLASS, basicQuartzJob.getJobClass());
            jobDetail.getJobDataMap().put(QuartzJobBean.TARGET_METHOD,
                    basicQuartzJob.getJobMethod());
            jobDetail.getJobDataMap().put(QuartzJobBean.TARGET_ARGUMENTS,
                    basicQuartzJob.getJobArguments());
            jobDetail.getJobDataMap().put(QuartzJobBean.TARGET_USER_ID,
                    basicQuartzJob.getUserId());
            jobDetail.getJobDataMap().put(QuartzJobBean.TARGET_JOB_ID,
                    basicQuartzJob.getId());
            jobDetail.getJobDataMap().put(QuartzJobBean.TARGET_JOB_IS_LOCAL,
                    basicQuartzJob.isLocal());

            trigger = new CronTrigger(basicQuartzJob.getTriggerName(), basicQuartzJob.getJobGroup(),
                    basicQuartzJob.getCronExpression());
            scheduler.scheduleJob(jobDetail, trigger);
            /**
             * trigger 有name jobName group jobGroup 如果没有设置jobName，则在调度时会用jobDetail的name和group赋值
             * trigger的jobName和jobGroup，必须和jobDetail的保持一致
             */
            if (log.isInfoEnabled()) {
                log.info(basicQuartzJob.toString() + ", add new.");
            }
        } else {
            trigger.setCronExpression(basicQuartzJob.getCronExpression());
            scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
            if (log.isInfoEnabled()) {
                log.info(basicQuartzJob.toString() + ", modify exist.");
            }
        }
    }

    /**
     * Quartz停止任务
     * 
     * @param basicQuartzJob
     * @throws Exception
     */
    private void disable(BasicQuartzJob basicQuartzJob) throws Exception {
        if (log.isInfoEnabled()) {
            log.info(basicQuartzJob.toString());
        }

        Trigger trigger =
                scheduler.getTrigger(basicQuartzJob.getTriggerName(), basicQuartzJob.getJobGroup());
        if (null != trigger) {
            scheduler.pauseTrigger(trigger.getName(), trigger.getGroup());
            scheduler.unscheduleJob(trigger.getName(), trigger.getGroup());
            scheduler.deleteJob(basicQuartzJob.getJobName(), basicQuartzJob.getJobGroup());
        }
    }

    /**
     * 按照ID启动指定的任务
     * 
     * @param id
     * @throws Exception
     */
    public void start(Long id) throws Exception {

        BasicQuartzJob basicQuartzJob = basicQuartzJobDAO.selectJobById(id);
        
        basicQuartzJob.setLocal(false);

        this.start(basicQuartzJob);
    }

    /**
     * 启动指定的任务
     * 
     * @param basicQuartzJob
     * @throws Exception
     */
    public void start(BasicQuartzJob basicQuartzJob) throws Exception {
        if (null == basicQuartzJob) {
            return;
        }

        this.enable(basicQuartzJob);
        basicQuartzJob.setStatus(BasicQuartzJob.JOB_STATUS_ON);
        basicQuartzJobDAO.updateJob(basicQuartzJob);
    }

    /**
     * 按照ID停止指定的任务
     * 
     * @param id
     * @throws Exception
     */
    public void stop(Long id) throws Exception {
        BasicQuartzJob basicQuartzJob = basicQuartzJobDAO.selectJobById(id);
        if (null == basicQuartzJob) {
            return;
        }

        this.disable(basicQuartzJob);
        basicQuartzJob.setStatus(BasicQuartzJob.JOB_STATUS_OFF);
        basicQuartzJobDAO.updateJob(basicQuartzJob);
        log.error("暂停任务id:" + id);
    }

    /**
     * 查询所有的任务列表
     * 
     * @return
     */
    public List<BasicQuartzJob> getAllJobList() {

        return basicQuartzJobDAO.selectAllJob();
    }

    /**
     * 查询指定的任务
     * 
     * @param id
     * @return
     */
    public BasicQuartzJob getJobDetail(Long id) {

        return basicQuartzJobDAO.selectJobById(id);
    }

    /**
     * 新增JOB
     * 
     * @param basicQuartzJob
     * @return
     */
    public JobSaveResult add(BasicQuartzJob basicQuartzJob) {

        if (null == basicQuartzJob) {
            return JobSaveResult.FAIL_ILLEGAL;
        }

        if (!checkForDomain(basicQuartzJob)) {
            return JobSaveResult.FAIL_ILLEGAL;
        }
        QueryParam param = new QueryParam();
        param.setJobGroup(basicQuartzJob.getJobGroup());
        param.setJobName(basicQuartzJob.getJobName());
        List<BasicQuartzJob> list = basicQuartzJobDAO.selectJobsByQueryParam(param);
        if (CollectionUtils.isNotEmpty(list)) {
            return JobSaveResult.FAIL_CONFLICT;
        }

        // 新增任务默认为关闭状态 需要手工开启
        basicQuartzJob.setStatus(BasicQuartzJob.JOB_STATUS_OFF);

        basicQuartzJobDAO.insertJob(basicQuartzJob);

        return JobSaveResult.SUCCESS;
    }

    /**
     * 修改任务
     * 
     * @param basicQuartzJob
     * @return
     */
    public JobSaveResult modify(BasicQuartzJob basicQuartzJob) {
        if (null == basicQuartzJob) {
            return JobSaveResult.FAIL_ILLEGAL;
        }

        if (!checkForDomain(basicQuartzJob)) {
            return JobSaveResult.FAIL_ILLEGAL;
        }

        Long id = basicQuartzJob.getId();
        if (id <= 0) {
            return JobSaveResult.FAIL_ILLEGAL;
        }

        BasicQuartzJob oldJob = basicQuartzJobDAO.selectJobById(id);
        if (null == oldJob) {
            return JobSaveResult.FAIL_NOT_EXISTS;
        }

        // 如果存在ID不同，任务组和任务名称相同的，存在问题，不能修改

        QueryParam param = new QueryParam();
        param.setJobGroup(basicQuartzJob.getJobGroup());
        param.setJobName(basicQuartzJob.getJobName());
        List<BasicQuartzJob> list = basicQuartzJobDAO.selectJobsByQueryParam(param);
        for (BasicQuartzJob job : list) {
            if (!job.getId().equals(id) ) {
                return JobSaveResult.FAIL_CONFLICT;
            }
        }

        // 先终止掉目前调度器内的任务
        try {
            this.disable(oldJob);
        } catch (Exception e) {
            log.error("[BasicScheduler][Modify]error when disale. " + oldJob + " " + e.getMessage(),
                    e);
            return JobSaveResult.FAIL_SCHEDULER;
        }

        basicQuartzJobDAO.updateJob(basicQuartzJob);

        // 如果是正在运行的任务 则启动
        if (basicQuartzJob.isRunning()) {
            try {
                this.enable(basicQuartzJob);
            } catch (Exception e) {
                log.error("[BasicScheduler][Modify]error when enable. " + basicQuartzJob + " "
                        + e.getMessage(), e);
                return JobSaveResult.FAIL_SCHEDULER;
            }
        }

        return JobSaveResult.SUCCESS;
    }

    /**
     * 通过所有的Trigger查询所有在调度执行的Job
     * 
     * @return
     */
    public List<BasicQuartzJob> getAllJobStatus() {
        List<BasicQuartzJob> list = new ArrayList<BasicQuartzJob>();

        try {
            String[] groups = scheduler.getTriggerGroupNames();
            for (String group : groups) {
                String[] names = scheduler.getTriggerNames(group);
                for (String name : names) {
                    Trigger trigger = scheduler.getTrigger(name, group);
                    String jobName = trigger.getJobName();
                    String jobGroup = trigger.getJobGroup();

                    BasicQuartzJob job = new BasicQuartzJob();
                    job.setJobName(jobName);
                    job.setJobGroup(jobGroup);
                    list.add(job);
                }
            }
        } catch (SchedulerException e) {
        }

        return list;
    }

    /**
     * 检查参数是否合法
     * 
     * @param basicQuartzJob
     * @return
     */
    boolean checkForDomain(BasicQuartzJob basicQuartzJob) {
        if (StringUtils.isEmpty(basicQuartzJob.getCronExpression())
                || StringUtils.isEmpty(basicQuartzJob.getJobClass())
                || StringUtils.isEmpty(basicQuartzJob.getJobMethod())
                || StringUtils.isEmpty(basicQuartzJob.getJobGroup())
                || StringUtils.isEmpty(basicQuartzJob.getJobName())) {
            return false;
        }

        return true;
    }
    
    /**
     * 新增job执行失败记录
     * @param jobId
     */
    public void addJobFilureResult(long jobId){
        JobExcuteResult result = new JobExcuteResult();
        result.setJobId(jobId);
        result.setStatus(0);
        result.setUseTime(0);
        
        jobExcuteResultDAO.insertJobResultDAO(result);
    }
}
