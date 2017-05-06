package com.rephen.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rephen.dao.UserInfoDAO;
import com.rephen.domain.UserInfo;

@Controller
@RequestMapping("/user")
public class UserController {
    
    private final static Log log = LogFactory.getLog(UserController.class);
    
    @Resource
    private UserInfoDAO userInfoDAO;

    
    @RequestMapping("/edit")
    public ModelAndView editUser(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("error_page");
        
        String userIdStr = request.getParameter("userId");
        if(!StringUtils.isBlank(userIdStr) && NumberUtils.isNumber(userIdStr)){
            UserInfo userInfo = userInfoDAO.selectUserInfoById(NumberUtils.toLong(userIdStr));
            if(null == userInfo){
                log.error("获取用户信息失败：不存在该用户  id:" + userIdStr);
            }else{
                mv.addObject("user", userInfo);
                mv.setViewName("user_info_add_edit");
            }
            
        }else{
            log.error("获取用户信息失败：id为空或非数字");
        }
        
        return mv;
    }
    
    
    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws IOException{
        ModelAndView mv = new ModelAndView("error_page");
        
        String userIdStr = request.getParameter("userId");
        String password = request.getParameter("password");
        String mailAddress = request.getParameter("mailAddress");

        if(!StringUtils.isBlank(password) && !StringUtils.isBlank(mailAddress)){
           
            try{
                UserInfo user = userInfoDAO.selectUserInfoById(NumberUtils.toLong(userIdStr));
                user.setMailAddress(mailAddress);
                user.setPassword(password);
                userInfoDAO.updateUserInfo(user);
                mv.setViewName("redirect:/job/list?userId=" + userIdStr);
            }catch(Exception e){
                log.error("保存失败", e);
            }
            
        }else{
            log.error("保存失败：密码或邮箱地址为空");
        }
        return mv;
    }
    
}
