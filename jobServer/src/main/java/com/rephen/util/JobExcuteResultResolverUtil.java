package com.rephen.util;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.rephen.domain.JobExcuteResult;

public class JobExcuteResultResolverUtil {

    private static SimpleDateFormat yyyyMMddHHmmSS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * json拼接job执行成功详情数据
     * 
     * @param results
     * @return
     */
    public static String[] getJobExcuteDetail(List<JobExcuteResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return null;
        } else {
            String[] content = {"", ""};
            String xAxis = "[";
            String data = "[";
            for (int i = 0; i < results.size(); i++) {
                xAxis += "\'";
                xAxis += yyyyMMddHHmmSS.format(results.get(i).getCreateTime());
                xAxis += "\'";
                data += results.get(i).getUseTime();
                if (i < results.size() - 1) {
                    xAxis += ",";
                    data += ",";
                }


            }
            xAxis += "]";
            data += "]";
            content[0] = xAxis;
            content[1] = data;
            return content;
        }
    }
    
    /**
     * 饼状图json
     * 
     * @param successCount
     * @param failureCount
     * @return
     */
    public static String getJobExcuteCount(int successCount, int failureCount) {
        String successCountStr = String.valueOf(successCount);
        String failureCountStr = String.valueOf(failureCount);
        return "[{value:" + successCountStr + ", name:\'成功次数\'},{value:" + failureCountStr
                + ", name:\'失败次数\'}]";
    }

}
