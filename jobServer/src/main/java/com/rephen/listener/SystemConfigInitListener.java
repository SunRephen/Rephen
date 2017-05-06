package com.rephen.listener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 读取配置文件listener
 * @author Rephen
 *
 */
public class SystemConfigInitListener implements ServletContextListener{
    
    
    public void contextInitialized(ServletContextEvent sce) {
        String prefix = sce.getServletContext().getRealPath("/");
        String file = sce.getServletContext().getInitParameter("systemConfigLocation");
        Properties props = new Properties();
        InputStream is = null;
        try {
            if (file.startsWith("classpath:")) {
                is =
                        SystemConfigInitListener.class.getClassLoader().getResourceAsStream(
                                file.substring(10));
            } else {
                String filePath = prefix + file;
                is = new FileInputStream(filePath);
            }
            props.load(is);
            for (Enumeration<Object> it = props.keys(); it.hasMoreElements();) {
                String key = (String) it.nextElement();
                System.setProperty(key, props.getProperty(key));
                sce.getServletContext().setAttribute(key, props.getProperty(key));
            }
        } catch (Exception e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        
    }

}
