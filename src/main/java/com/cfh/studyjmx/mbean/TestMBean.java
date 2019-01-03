package com.cfh.studyjmx.mbean;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * 遵循Standard MBean规范定义的MBean接口，命名规范为被jmx管理的对象资源类名+MBean后缀
 */
public interface TestMBean {
    public String getName();
    public void setName(String name);
    public void printHello();
    public void printHello(String whoName);
}