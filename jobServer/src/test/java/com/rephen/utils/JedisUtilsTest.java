package com.rephen.utils;

import org.junit.Before;
import org.junit.Test;

import com.rephen.basic.BeanFactory;
import com.rephen.util.JedisUtil;

public class JedisUtilsTest {
    private JedisUtil jedisUtil;

    @Before
    public void setUp() {
        jedisUtil = (JedisUtil) BeanFactory.getBean("jedisUtil");
    }

    @Test
    public void set() throws Exception {
        jedisUtil.add("1", "2");
        System.out.println(jedisUtil.get("1"));
    }
}
