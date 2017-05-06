package com.rephen.util;

public class GetExceptionDetailUtil {

    public static String getExceptionAllinformation(Throwable ex) {
        String sOut = "cause:"+ex.getMessage()+"\r\n";
        StackTraceElement[] trace = ex.getCause().getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
    }
    

}
