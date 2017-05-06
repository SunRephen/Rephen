package com.rephen.service;

public interface MailService {

    void sendMail(Long userId, Throwable e, String className, String method);
}
