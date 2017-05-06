package com.rephen.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean工厂（供test使用）
 * @author Rephen
 *
 */
public class BeanFactory {
	
	private static ApplicationContext context =null;

    public static void init() {
        if (context == null) {
            context =
                    new ClassPathXmlApplicationContext("classpath*:spring/spring-*.xml");
        }
    }
	
    public static Object getBeanFromFacotry(String beanName) {
        init();
        return context.getBean(beanName);
    }
	
    public static <T> T getBeanFromFacotry(Class<T> requiredType) {
        init();
        return context.getBean(requiredType);
    }
	
	public static <T> T getBeanFromFacotry(String beanName, Class<T> requiredType) {
		init();
		return context.getBean(beanName, requiredType);
	}
	
	public static Object getBean(String beanName) {
		return getBeanFromFacotry(beanName);
	}
	
	public static <T> T getBean(Class<T> requiredType) {
		return getBeanFromFacotry(requiredType);
	}
	
	public static <T> T getBean(String beanName, Class<T> requiredType) {
		return getBeanFromFacotry(beanName, requiredType);
	}
}
