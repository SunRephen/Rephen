package com.rephen.util;

public class GetExceptionDetailUtil {

    public static String getExceptionAllInformation(Throwable ex) {
        String sOut = "cause:"+ex.getMessage()+"\r\n";
        
        StackTraceElement[] trace;
        if(null != ex.getCause()){
            trace= ex.getCause().getStackTrace();
        }else{
            trace= ex.getStackTrace();
        }
        
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
    }
    

}
