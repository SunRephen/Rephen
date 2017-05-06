package com.rephen.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rephen.dao.UserInfoDAO;
import com.rephen.domain.UserInfo;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Log log = LogFactory.getLog(UserController.class);

    @Resource
    UserInfoDAO userInfoDAO;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("user_info_list");
        List<UserInfo> userInfos = userInfoDAO.selectAllUserInfo();
        for (int i = 0;i < userInfos.size();i++) {

            if (userInfos.get(i).getId().equals(UserInfo.SA_ID))
                userInfos.remove(i);
//            if (CollectionUtils.isEmpty(userInfos)) {
//                break;
//            }

        }
        mv.addObject("list", userInfos);
        return mv;
    }

    @RequestMapping("/addOrEdit")
    public ModelAndView addOrEditUser(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("user_info_add_edit");

        String userIdStr = request.getParameter("userId");
        if (!StringUtils.isBlank(userIdStr)) {
            try {
                Long userId = Long.parseLong(userIdStr);
                UserInfo userInfo = userInfoDAO.selectUserInfoById(userId);
                mv.addObject("user", userInfo);
            } catch (Exception e) {
                log.error("添加/修改人员失败", e);
                mv.setViewName("error_page");
                return mv;
            }
        }
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("error_page");
        String idStr = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String mailAddress = request.getParameter("mailAddress");


        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
                || StringUtils.isBlank(mailAddress)) {
            log.error("保存用户信息错误：信息不完整。username:" + username + " password:" + password
                    + " mailAddress:" + mailAddress);
            return mv;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setMailAddress(mailAddress);

        try {
            if (StringUtils.isBlank(idStr)) {
                // add
                userInfoDAO.insertUserInfo(userInfo);
                mv.setViewName("redirect:/user/list");
                return mv;
            } else {
                // edit
                if (NumberUtils.isNumber(idStr)) {
                    userInfo.setId(NumberUtils.toLong(idStr));
                    userInfoDAO.updateUserInfo(userInfo);
                    mv.setViewName("redirect:/user/list");
                    return mv;
                } else {
                    log.error("更新用户信息错误：id错误。id:" + idStr);
                    return mv;
                }
            }
        } catch (Exception e) {
            log.error("保存人员失败", e);
            return mv;
        }

    }

    @RequestMapping("/delete")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String idStr = request.getParameter("id");
            if (NumberUtils.isNumber(idStr)) {
                userInfoDAO.deleteUserInfo(NumberUtils.toLong(idStr));
                result.put("message", "删除成功");
            } else {
                log.error("删除失败：id非法，id：" + idStr);
                result.put("message", "删除失败：id非法，id：" + idStr);
                writeJson(response, result);
                return;
            }
        } catch (Exception e) {
            result.put("message", "删除失败:" + e.getMessage());
            writeJson(response, result);
            return;
        }
        writeJson(response, result);
    }

    @RequestMapping("/resetPassword")
    public void resetPassword(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            String idStr = request.getParameter("id");
            if (NumberUtils.isNumber(idStr)) {
                UserInfo userInfo = userInfoDAO.selectUserInfoById(NumberUtils.toLong(idStr));
                userInfo.setPassword("123456");
                userInfoDAO.updateUserInfo(userInfo);
                result.put("message", "重置成功");
            } else {
                log.error("重置密码错误：id非法，id：" + idStr);
                result.put("message", "重置密码错误：id非法，id：" + idStr);
                writeJson(response, result);
                return;
            }
        } catch (Exception e) {
            result.put("message", "重置失败:" + e.getMessage());
            writeJson(response, result);
            return;
        }
        writeJson(response, result);
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
