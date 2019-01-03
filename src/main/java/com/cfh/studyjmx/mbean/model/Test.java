package com.cfh.studyjmx.mbean.model;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * 被ModelMBean管理的类，不需要实现任何接口
 */

public class Test {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printHello(){
        System.out.println("Hello world, "+name);
    }

    public void printHello(String whoName){
        System.out.println("Hello, "+whoName);
    }
}