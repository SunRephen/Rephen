package com.rephen.component;

/**
 * job保存结果 enum
 * @author Rephen
 *
 */
public enum JobSaveResult {

    SUCCESS("成功"),
    FAIL_PROJECT("工程参数错误"),
    FAIL_ILLEGAL("参数不合法错误"),
    FAIL_CONFLICT("任务组和名称冲突"),
    FAIL_NOT_EXISTS("现有任务不存在"),
    FAIL_SCHEDULER("调度错误"),
    ;
    
    private String message;
    
    private JobSaveResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
