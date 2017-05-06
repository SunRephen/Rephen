
package com.rephen.domain;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.rephen.general.service.GeneralJobService;
import com.rephen.service.MailService;
import com.rephen.task.BasicScheduler;


/**
 * job运行类
 * 
 * @author Rephen
 *
 */
public class QuartzJobBean implements StatefulJob {


    private Log log = LogFactory.getLog(QuartzJobBean.class);

    public static final String TARGET_CLASS = "class";
    public static final String TARGET_METHOD = "method";
    public static final String TARGET_ARGUMENTS = "arguments";
    public static final String TARGET_USER_ID = "userId";
    public static final String TARGET_JOB_ID = "id";
    public static final String TARGET_JOB_IS_LOCAL = "isLocal";

    private static final String JOB = "job";

    private static ApplicationContext ac;

    private static MailService mailService;
    
    private static GeneralJobService generalJobService;

    private static BasicScheduler scheduler;

    private static final int ONE_DAY = 60 * 60 * 24;


    public static void setEnv(ApplicationContext ac, MailService mailService,
            BasicScheduler scheduler,GeneralJobService generalJobService) {
        QuartzJobBean.ac = ac;
        QuartzJobBean.mailService = mailService;
        QuartzJobBean.generalJobService = generalJobService;
        QuartzJobBean.scheduler = scheduler;
    }

    /**
     * 通过类名和方法名去获取目标对象，再通过反射执行 类名和方法名保存在jobDetail中
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String targetClass = (String) context.getMergedJobDataMap().get(TARGET_CLASS);
        String targetMethod = (String) context.getMergedJobDataMap().get(TARGET_METHOD);
        String methodArgs = (String) context.getMergedJobDataMap().get(TARGET_ARGUMENTS);
        Long userId = (Long) context.getMergedJobDataMap().get(TARGET_USER_ID);
        Long id = (Long) context.getMergedJobDataMap().get(TARGET_JOB_ID);
        Boolean jobIsLocal = (Boolean) context.getMergedJobDataMap().get(TARGET_JOB_IS_LOCAL);

        if (StringUtils.isEmpty(targetClass) || StringUtils.isEmpty(targetMethod))
            return;

        Object[] args = null;
        if (!StringUtils.isEmpty(methodArgs)) {
            methodArgs = methodArgs + " ";
            String[] argString = methodArgs.split("#&");
            args = new Object[argString.length];
            for (int i = 0; i < argString.length; i++) {
                args[i] = argString[i].trim();
            }
        }

        long startTime = System.currentTimeMillis();

        try {
            if(jobIsLocal){
                // 本地任务
                Object target = ac.getBean(targetClass);
                if (null != target) {
                    Class tc = target.getClass();
                    Class[] parameterType = null;
                    if (args != null) {
                        parameterType = new Class[args.length];
                        for (int i = 0; i < args.length; i++) {
                            parameterType[i] = String.class;
                        }
                    }
                    Method method = tc.getDeclaredMethod(targetMethod, parameterType);
                    if (null != method) {
                        method.invoke(target, args);
                    }
                }
            }else{
                // 远程调度任务
                generalJobService.excute(targetClass, targetMethod, methodArgs);
                // 心跳检测及redis数据刷入DB不做统计
                if (!("basicTaskMonitor".equalsIgnoreCase(targetClass) || "basicRedisToDBTask".equalsIgnoreCase(targetClass))) {
                    // 成功job id统计
                    scheduler.getJedisUtil().sadd(JOB, String.valueOf(id));
                    // 统计job成功结果 写入redis zset 结构 score：timeStamp 为防止member重复导致更新此处member也加时间戳
                    scheduler.getJedisUtil().zadd(JOB + "_" + id,
                            String.valueOf(System.currentTimeMillis() - startTime) +"_"+ System.currentTimeMillis(),
                            System.currentTimeMillis());
                    // 过期时间为1天
                    scheduler.getJedisUtil().expire(JOB + "_" + id, ONE_DAY);
                    scheduler.getJedisUtil().expire(JOB, ONE_DAY);
                }
            }
            
        } catch (Exception  e) {
            log.error("作业调度异常:" + e.getMessage(), e.getCause());
            if (!("basicTaskMonitor".equalsIgnoreCase(targetClass) || "basicRedisToDBTask".equalsIgnoreCase(targetClass))) {
                // job失败 写入DB
                scheduler.addJobFilureResult(id);
            }
            // 处理异常链为空的问题 优先发送异常链
            if(null != e.getCause()){
                mailService.sendMail(userId, e.getCause(), targetClass, targetMethod);
            }else{
                mailService.sendMail(userId, e, targetClass, targetMethod);
            }
            try {
                scheduler.stop(id);
            } catch (Exception e1) {
                log.error("作业停止失败:" + e1.getMessage(), e1);
            }
            throw new JobExecutionException(e);
        }

        log.error("[QuartzJobBean]" + targetClass + ", " + targetMethod + ", " + methodArgs
                + ", 耗時 " + (System.currentTimeMillis() - startTime));
    }

}
