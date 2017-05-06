package com.rephen.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rephen.dao.BasicQuartzJobDAO;
import com.rephen.domain.BasicQuartzJob;

@Controller
@RequestMapping("/job")
public class JobController {
    
    private final static Log log = LogFactory.getLog(JobController.class);
    
    @Resource
    private BasicQuartzJobDAO basicQuartzJobDAO;
    
    
    @RequestMapping("/list")
    public ModelAndView showPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("job_list");
        
        String userIdsStr = request.getParameter("userId");
        if(!StringUtils.isBlank(userIdsStr)&& NumberUtils.isNumber(userIdsStr)){
            List<BasicQuartzJob> list = basicQuartzJobDAO.selectJobByUserId(NumberUtils.toLong(userIdsStr));
            mv.addObject("list", list);
            mv.addObject("userId", userIdsStr);
        }else{
            log.error("人员id错误：" + userIdsStr);
        }
        return mv;
    }
    
    @RequestMapping("/addOrEdit")
    public ModelAndView addOrEditJob(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("error_page");
        
        String jobIdStr = request.getParameter("jobId");
        String userIdStr = request.getParameter("userId");
        
        if(StringUtils.isBlank(jobIdStr)){
            // 新增
            mv.addObject("userId", userIdStr);
            mv.setViewName("job_detail");
        }else{
            // 修改
            if(NumberUtils.isNumber(jobIdStr)){
                BasicQuartzJob job = basicQuartzJobDAO.selectJobById(NumberUtils.toLong(jobIdStr));
                mv.addObject("job", job);
                mv.addObject("userId", userIdStr);
                mv.setViewName("job_detail");
            }else{
                log.error("修改job时错误 id:" + jobIdStr);
            }
        }
        return mv;
    }
    
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        BasicQuartzJob basicQuartzJob = this.handleRequest(request);
        if (null == basicQuartzJob) {
            result.put("message", "参数错误");
            writeJson(response, result);
            return;
        }

        Long id = basicQuartzJob.getId();
        try {
            if(null == id){
                basicQuartzJobDAO.insertJob(basicQuartzJob);
            }else{
                basicQuartzJobDAO.updateJob(basicQuartzJob);
            }
            
        } catch (Exception e) {
            result.put("message", "保存失败, " + e.getMessage());
            writeJson(response, result);
            return;
        }

        result.put("success", "保存成功");
        result.put("id", id);
        result.put("message", "保存成功");
        writeJson(response, result);
    }
    
    @RequestMapping(value="/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        try{
            String jobIdStr = request.getParameter("id");
            Long id = Long.parseLong(jobIdStr);
            basicQuartzJobDAO.deleteJobById(id);
            result.put("message", "删除成功");
        }catch(Exception e){
            result.put("message", "删除失败");
            log.error("删除失败", e);
        }
        
        writeJson(response, result);
    }
    
    
    private BasicQuartzJob handleRequest(HttpServletRequest request){
        
        String jobClass = request.getParameter("jobClass");
        String jobMethod = request.getParameter("jobMethod");
        String jobArguments = request.getParameter("jobArguments");
        String jobName = request.getParameter("jobName");
        String cronExpression = request.getParameter("cronExpression");
        String description = request.getParameter("description");
        
        long userId = 0L;
        try{
            userId = Long.parseLong(request.getParameter("userId"));
        }catch(Exception e){
            log.error("保存时出错",e);
        }
        
        if (StringUtils.isEmpty(jobClass) || StringUtils.isEmpty(jobMethod)
                || StringUtils.isEmpty(jobName)
                || StringUtils.isEmpty(cronExpression)) {
            return null;
        }

        BasicQuartzJob basicQuartzJob = new BasicQuartzJob();
        
        basicQuartzJob.setJobClass(jobClass);
        basicQuartzJob.setJobMethod(jobMethod);
        basicQuartzJob.setJobArguments(jobArguments);
        basicQuartzJob.setJobName(jobName);
        basicQuartzJob.setCronExpression(cronExpression);
        basicQuartzJob.setDescription(description);
        
        
        String jobIdStr = request.getParameter("jobId");
        if(StringUtils.isBlank(jobIdStr)){
            // 新增
            basicQuartzJob.setIsHide(0);
            basicQuartzJob.setStatus(BasicQuartzJob.JOB_STATUS_OFF);
            basicQuartzJob.setUserId(userId);
        }else{
            // 修改
            if(NumberUtils.isNumber(jobIdStr)){
                basicQuartzJob.setId(NumberUtils.toLong(jobIdStr));
            }else{
                return null;
            }
        }

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
