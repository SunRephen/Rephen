package com.rephen.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.rephen.dao.UserInfoDAO;
import com.rephen.domain.UserInfo;
import com.rephen.service.MailService;
import com.rephen.util.GetExceptionDetailUtil;

public class MailServiceImpl implements MailService{

    private final static Log log = LogFactory.getLog(MailServiceImpl.class);

    @Resource
    private JavaMailSender javaMailSender;
    
    @Resource
    private UserInfoDAO userInfoDAO;

    private static final String SEND_FROM = "sunjob123@sina.com";

    private static final String MAIL_SUBJIECT = "job异常告警";
    
    @Override
    public void sendMail(Long userId, Throwable e, String className, String method) {
        if (null == e) {
            log.error("发送邮件失败：异常为空");
            return;
        }
        
        UserInfo userInfo = userInfoDAO.selectUserInfoById(userId);
        if(null == userInfo || StringUtils.isBlank(userInfo.getMailAddress())){
            log.error("发送邮件失败：未存在id:" + userId + " 或该用户邮箱为空");
            return;
        }
        String mailAddress = userInfo.getMailAddress();
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailAddress);
        mail.setFrom(SEND_FROM);
        mail.setSubject(MAIL_SUBJIECT);
        
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("job异常-----类:").append(className).append("  方法:").append(method)
                .append("\r\n原因:").append(GetExceptionDetailUtil.getExceptionAllinformation(e)).append("\r\n已自动停止该job");
        mail.setText(stringBuffer.toString());
        
        log.error("发送异常告警邮件:"+mailAddress);
        javaMailSender.send(mail);
    }

}
