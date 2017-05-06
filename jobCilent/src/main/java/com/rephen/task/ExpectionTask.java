package com.rephen.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 异常告警任务
 * @author Rephen
 *
 */
public class ExpectionTask {
    
    private Log log = LogFactory.getLog(ExpectionTask.class);
    
    @SuppressWarnings("null")
    public void sendMail() throws Exception{
        try{
            log.error("异常任务开启");
            String str = null;
//            try {
//                
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
            str.substring(1, 2);
        }catch(Exception e){
            throw new Exception(e);
        }
        
    }

}
