package com.rephen.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rephen.component.JobSaveResult;
import com.rephen.dao.BasicQuartzJobDAO;
import com.rephen.dao.JobExcuteResultDAO;
import com.rephen.dao.UserInfoDAO;
import com.rephen.domain.BasicQuartzJob;
import com.rephen.domain.JobExcuteResult;
import com.rephen.task.BasicScheduler;
import com.rephen.util.JedisUtil;
import com.rephen.util.JobExcuteResultResolverUtil;



@Controller
@RequestMapping("/job")
public class JobConfigController {

    private final static Log log = LogFactory.getLog(JobConfigController.class);

    @Resource
    private BasicScheduler basicScheduler;

    @Resource
    private JedisUtil jedisUtil;

    @Resource
    private JobExcuteResultDAO jobExcuteResultDAO;

    @Resource
    private BasicQuartzJobDAO basicQuartzJobDAO;
    
    @Resource
    private UserInfoDAO userInfoDAO;


    private String getJobExcuteResult() {
        return "job_excute_result";
    }

    private String getJobExcuteDetail() {
        return "job_excute_detail";
    }

    private String getErrorPage() {
        return "error_page";
    }

    private static final String JOB = "job";


    @RequestMapping("/list")
    public ModelAndView listJob(HttpServletRequest request, HttpServletResponse response) {
        ModelMap model = new ModelMap();

        try {
            List<BasicQuartzJob> list = basicScheduler.getAllJobList();
            for(BasicQuartzJob job:list){
                job.setUsername(userInfoDAO.selectUserInfoById(job.getUserId()).getUsername());
            }
            model.put("list", list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            model.put("message", e.getMessage());
            return new ModelAndView("job_list", model);
        }

        return new ModelAndView("job_list", model);
    }

    @RequestMapping("start")
    public void startJob(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        Long id = 0L;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            result.put("message", "参数错误");
            writeJson(response, result);
            return;
        }

        try {
            basicScheduler.start(id);
        } catch (Exception e) {
            result.put("message", "启动任务错误:" + e.getMessage());
            writeJson(response, result);
            return;
        }

        result.put("message", "启动成功");
        writeJson(response, result);
    }

    @RequestMapping("stop")
    public void stopJob(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        Long id = 0L;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            result.put("message", "参数错误");
            writeJson(response, result);
            return;
        }

        try {
            basicScheduler.stop(id);
        } catch (Exception e) {
            result.put("message", "停止任务错误:" + e.getMessage());
            writeJson(response, result);
            return;
        }

        result.put("message", "停止成功");
        writeJson(response, result);
    }

    @RequestMapping("add")
    public ModelAndView addJob(HttpServletRequest request, HttpServletResponse response) {
        ModelMap model = new ModelMap();
        model.put("type", "add");
        return new ModelAndView("job_detail", model);
    }

    @RequestMapping("update")
    public ModelAndView updateJob(HttpServletRequest request, HttpServletResponse response) {
        ModelMap model = new ModelMap();

        Long id = 0L;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            model.put("message", "参数错误");
            return new ModelAndView("job_detail", model);
        }

        try {
            BasicQuartzJob basicQuartzJob = basicScheduler.getJobDetail(id);
            if (null == basicQuartzJob) {
                model.put("message", "无法查询到对应的任务");
            } else {
                basicQuartzJob.setUsername(userInfoDAO.selectUserInfoById(basicQuartzJob.getUserId()).getUsername());
                model.put("job", basicQuartzJob);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            model.put("message", e.getMessage());
            return new ModelAndView("job_detail", model);
        }

        model.put("type", "update");
        return new ModelAndView("job_detail", model);
    }

    @RequestMapping("save")
    public void saveJob(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        BasicQuartzJob basicQuartzJob = this.handleRequest(request);
        if (null == basicQuartzJob) {
            result.put("message", "参数错误");
            writeJson(response, result);
            return;
        }

        Long id = basicQuartzJob.getId();
        JobSaveResult jsr = null;
        try {
            if (id <= 0) {
                // 新增
                jsr = basicScheduler.add(basicQuartzJob);
            } else {
                jsr = basicScheduler.modify(basicQuartzJob);
            }
        } catch (Exception e) {
            result.put("message", "保存失败, " + e.getMessage());
            writeJson(response, result);
            return;
        }

        result.put("success", jsr == JobSaveResult.SUCCESS);
        result.put("id", id);
        result.put("message", jsr.getMessage());
        writeJson(response, result);
    }

    @RequestMapping("hide")
    public void hideJob(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();

        Long id = 0L;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            result.put("message", "参数错误");
            writeJson(response, result);
            return;
        }

        JobSaveResult jsr = null;
        try {
            // 停止任务
            basicScheduler.stop(id);
            BasicQuartzJob basicQuartzJob = basicScheduler.getJobDetail(id);
            basicQuartzJob.setIsHide(1);

            jsr = basicScheduler.modify(basicQuartzJob);

        } catch (Exception e) {
            result.put("message", "删除任务错误:" + e.getMessage());
            writeJson(response, result);
            return;
        }

        result.put("message", jsr.getMessage());
        writeJson(response, result);
    }

    /**
     * 展示任务总运行情况
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("showDetail")
    public ModelAndView showDetail(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ModelAndView mv = new ModelAndView(getErrorPage());

        Long id = 0L;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            log.error("展示任务总运行情况错误", e);
            return null;
        }

        if (null == basicQuartzJobDAO.selectJobById(id)) {
            log.error("展示任务总运行情况错误，不存在该任务");
            return null;
        }

        String jobId = request.getParameter("id").trim();


        // 获取当前redis内job成功执行耗时时间
        Set<String> useTimes = jedisUtil.zgetMember(JOB + "_" + jobId, 0, -1);

        List<JobExcuteResult> historyResults = jobExcuteResultDAO.selectJobExcuteResultsByJobId(id);

        // 存在运行记录
        if (!(CollectionUtils.isEmpty(useTimes) && CollectionUtils.isEmpty(historyResults))) {
            mv.setViewName(getJobExcuteResult());
            mv.addObject("hasData", true);

            // 获取历史成功执行，失败执行次数
            int successCount = useTimes.size();
            int failureCount = 0;
            for (JobExcuteResult result : historyResults) {
                if (result.getStatus() == JobExcuteResult.SUCCESS) {
                    successCount++;
                } else {
                    failureCount++;
                }
            }

            // 存放结果
            String excuteCountResult =
                    JobExcuteResultResolverUtil.getJobExcuteCount(successCount, failureCount);
            mv.addObject("excuteCountResult", excuteCountResult);
            mv.addObject("jobId", id);

        }
        return mv;
    }


    /**
     * 展示作业最近一天成功运行情况
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("showRecentDetail")
    public ModelAndView showRecentDetail(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ModelAndView mv = new ModelAndView(getErrorPage());

        String jobId = request.getParameter("id").trim();

        if (!NumberUtils.isNumber(jobId)) {
            log.error("展示作业最近一天成功运行情况出错，id非法:" + jobId);
            return mv;
        }

        String key = JOB + "_" + jobId;


        // 获取当前redis内job成功执行耗时时间
        Set<String> useTimes = jedisUtil.zgetMember(key, 0, -1);

        if (!useTimes.isEmpty()) {
            List<JobExcuteResult> results = new ArrayList<JobExcuteResult>(useTimes.size());
            Date date = null;
            for (String useTime : useTimes) {
                // 分数（创建日期）
                Double score = jedisUtil.zgetScore(key, useTime);
                if (null != score) {
                    date = new Date(Math.round(score));
                    results.add(
                            new JobExcuteResult(NumberUtils.toLong(useTime.split("_")[0]), date));
                }
            }
            String data[] = JobExcuteResultResolverUtil.getJobExcuteDetail(results);
            if (!(StringUtils.isBlank(data[0]) || StringUtils.isBlank(data[1]))) {
                mv.setViewName(getJobExcuteDetail());
                mv.addObject("xAxis", data[0]);
                mv.addObject("value", data[1]);
                mv.addObject("showAll", 0);
                mv.addObject("jobId", jobId);
            }

        } else if (null != basicQuartzJobDAO.selectJobById(NumberUtils.toLong(jobId))) {
            mv.addObject("jobId", jobId);
            mv.addObject("hasRecentDetail", "yes");
        } else {
            log.error("不存在id：" + jobId + "的作业");
        }

        return mv;
    }

    /**
     * 展示任务历史成功运行情况
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("showHistoryDetail")
    public ModelAndView showHistoryDetail(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ModelAndView mv = new ModelAndView(getErrorPage());

        String jobId = request.getParameter("id").trim();

        if (!NumberUtils.isNumber(jobId)) {
            log.error("展示作业历史成功运行情况出错，id非法:" + jobId);
            return mv;
        }

        // 全部记录
        List<JobExcuteResult> allResults =
                jobExcuteResultDAO.selectJobExcuteResultsByJobId(NumberUtils.toLong(jobId));
        // 成功记录
        List<JobExcuteResult> successResults = new ArrayList<JobExcuteResult>(allResults.size());
        if (!CollectionUtils.isEmpty(allResults)) {
            for (JobExcuteResult result : allResults) {
                if (result.getStatus() == JobExcuteResult.SUCCESS) {
                    successResults.add(result);
                }
            }
            if (!CollectionUtils.isEmpty(successResults)) {
                String data[] = JobExcuteResultResolverUtil.getJobExcuteDetail(successResults);
                if (!(StringUtils.isBlank(data[0]) || StringUtils.isBlank(data[1]))) {
                    mv.setViewName(getJobExcuteDetail());
                    mv.addObject("xAxis", data[0]);
                    mv.addObject("value", data[1]);
                    mv.addObject("showAll", 1);
                }
            }
        }else{
            log.error("不存在id：" + jobId + "的作业");
        }

        return mv;
    }

    /**
     * 从请求中构建任务对象
     * 
     * @param request
     * @param basicQuartzJob
     * @return
     */
    private BasicQuartzJob handleRequest(HttpServletRequest request) {
        String type = request.getParameter("type");
        boolean isAdd = false;
        if ("add".equalsIgnoreCase(type)) {
            isAdd = true;
        }

        Long id = 0L;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            if (!isAdd) {
                return null;
            }
        }
        int jobStatus = 0;
        try {
            jobStatus = Integer.parseInt(request.getParameter("jobStatus"));
        } catch (Exception e) {
            if (!isAdd) {
                return null;
            }
        }

        String jobClass = request.getParameter("jobClass");
        String jobMethod = request.getParameter("jobMethod");
        String jobArguments = request.getParameter("jobArguments");
        String jobGroup = request.getParameter("jobGroup");
        String jobName = request.getParameter("jobName");
        String cronExpression = request.getParameter("cronExpression");
        String description = request.getParameter("description");
        
        long userId = 0L;
        try{
            userId = Integer.parseInt(request.getParameter("userId"));
        }catch(Exception e){
            log.error("保存时出错",e);
        }

        if (StringUtils.isEmpty(jobClass) || StringUtils.isEmpty(jobMethod)
                || StringUtils.isEmpty(jobGroup) || StringUtils.isEmpty(jobName)
                || StringUtils.isEmpty(cronExpression)) {
            return null;
        }

        BasicQuartzJob basicQuartzJob = new BasicQuartzJob();
        basicQuartzJob.setId(id);
        basicQuartzJob.setJobClass(jobClass);
        basicQuartzJob.setJobMethod(jobMethod);
        basicQuartzJob.setJobArguments(jobArguments);
        basicQuartzJob.setJobGroup(jobGroup);
        basicQuartzJob.setJobName(jobName);
        basicQuartzJob.setStatus(jobStatus == BasicQuartzJob.JOB_STATUS_ON
                ? BasicQuartzJob.JOB_STATUS_ON : BasicQuartzJob.JOB_STATUS_OFF);
        basicQuartzJob.setCronExpression(cronExpression);
        basicQuartzJob.setDescription(description);
        basicQuartzJob.setUserId(userId);

        return basicQuartzJob;
    }


    /**
     * 输出Gson到页面
     * 
     * @param response
     * @param obj
     * @throws IOException
     */
    private void writeJson(HttpServletResponse response, Object obj) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        response.setContentType("application/html; charset=UTF-8");
        response.getWriter().print(json);
    }



}
