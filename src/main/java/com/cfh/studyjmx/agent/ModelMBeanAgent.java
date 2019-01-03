package com.cfh.studyjmx.agent;

import com.cfh.studyjmx.mbean.model.ModelMBeanUtils;
import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.RequiredModelMBean;
import java.lang.management.ManagementFactory;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 */
public class ModelMBeanAgent {
    public static void main(String[] args) throws Exception{
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        String domainName = "ModelMBean";

        ObjectName testObjectName = new ObjectName(domainName + ":" + "name=testJMX");
        RequiredModelMBean test = ModelMBeanUtils.createModelMBean();
        // 注册Model类型的MBean
        server.registerMBean(test, testObjectName);

        ObjectName adapterName = new ObjectName(domainName + ":" + "name=htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        server.registerMBean(adapter, adapterName);
        adapter.start();
    }
}