package com.rephen.general.service.impl;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rephen.general.service.GeneralJobService;

public class GeneralJobServiceImpl implements GeneralJobService{


    private final static Log log = LogFactory.getLog(GeneralJobServiceImpl.class);
    
    protected GeneralJobServiceImpl() throws RemoteException {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void excute(String className, String methodName, String methodArgs)
            throws Exception {
        
        log.error("监控调度系统开始远程调用:"+className+"-"+methodName);
        
        Object[] args = null;
        if (!StringUtils.isEmpty(methodArgs)) {
            methodArgs = methodArgs + " ";
            String[] argString = methodArgs.split("#&");
            args = new Object[argString.length];
            for (int i = 0; i < argString.length; i++) {
                args[i] = argString[i].trim();
            }
        }
        //获得Clss对象
        Class target = Class.forName("com.rephen.task." + className);
        if (null != target) {
            Class[] parameterType = null;
            if (args != null) {
                parameterType = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterType[i] = String.class;
                }
            }
            Method method = target.getDeclaredMethod(methodName, parameterType);
            if (null != method) {
                method.invoke(target.newInstance(), args);
            }
        }
        
        log.error("监控调度系统结束远程调用:"+className+"-"+methodName);
        
    }

}
