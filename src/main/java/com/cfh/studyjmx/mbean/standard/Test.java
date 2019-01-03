package com.cfh.studyjmx.mbean.standard;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * 实现Standard MBean接口，被jmx管理的对象资源，注意要与对应的MBean接口放在同一个包下
 */

public class Test implements TestMBean {
    private String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printHello() {
        System.out.println("Test world, "+ name);
    }

    @Override
    public void printHello(String whoName) {
        System.out.println("Test, "+whoName);
    }
}