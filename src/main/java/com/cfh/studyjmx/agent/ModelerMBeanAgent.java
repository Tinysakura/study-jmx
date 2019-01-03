package com.cfh.studyjmx.agent;


import com.cfh.studyjmx.mbean.modeler.Test;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import org.apache.commons.modeler.ManagedBean;
import org.apache.commons.modeler.Registry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import java.io.InputStream;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 */

public class ModelerMBeanAgent {
    public static void main(String[] args) throws Exception{
        // 将xml信息读入registry
        Registry registry = Registry.getRegistry(null, null);
        InputStream inputStream = ModelMBeanAgent.class.getClassLoader().getResourceAsStream("modeler/mbeans-descriptors.xml");
        registry.loadMetadata(inputStream);

        // 通过registry获取注册MBean的MBeanServer
        MBeanServer mBeanServer = registry.getMBeanServer();

        // 获取xml文件中配置的MBean消息
        ManagedBean managedBean = registry.findManagedBean("Test");
        String domainName = managedBean.getDomain();
        System.out.println("domainName:" + domainName);
        ObjectName objectName = new ObjectName(domainName + ":" + "name=Test");
        // 使用ManagedBean创建一个ModelMBean
        ModelMBean test = managedBean.createMBean(new Test());
        // 注册
        mBeanServer.registerMBean(test, objectName);

        // html adaptor
        ObjectName adapterName = new ObjectName(domainName+ ":" + "name = htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        mBeanServer.registerMBean(adapter,adapterName);
        adapter.start();
    }
}