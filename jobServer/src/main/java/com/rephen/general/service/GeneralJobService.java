package com.rephen.general.service;

import java.rmi.RemoteException;

/**
 * 远程调用作业通用接口类
 * @author Rephen
 *
 */
public interface GeneralJobService{
    
    /**
     * 通用执行作业方法
     * @param className
     * @param methodName
     * @param args
     * @return
     * @throws Exception 
     * @throws RemoteException
     */
    public void excute(String className,String methodName,String args) throws Exception;
}
