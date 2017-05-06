package com.rephen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class LoginController {

    @RequestMapping("/toLogin")
    public ModelAndView showPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("welcome");
        return mv;

    }


    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        ModelAndView mv = new ModelAndView("welcome");

        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
            HttpSession session = request.getSession();
            if (username.equalsIgnoreCase("admin") && username.equalsIgnoreCase("admin")) {
                session.setAttribute("user_info", username);
                mv.setViewName("/job/list");
                return new ModelAndView("redirect:/job/list");
            } else {
                mv.addObject("loginInfo", "用户名或密码错误");
                return mv;
            }
        } else {
            mv.addObject("loginInfo", "用户名或密码错误");
            return mv;
        }
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("welcome");
        HttpSession session = request.getSession(false);
        // 以秒为单位
        session.removeAttribute("user_info");
        return mv;
    }

}
