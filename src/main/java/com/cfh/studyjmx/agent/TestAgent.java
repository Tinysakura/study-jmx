/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 */

package com.cfh.studyjmx.agent;

import com.cfh.studyjmx.mbean.Test;
import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * Agent层，通过Standard MBean对对象资源进行管理
 */

public class TestAgent {
    public static void main(String[] args) throws Exception{
        // 创建一个MBeanServer用于注册MBean
        // MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // 为MBean创建ObjectName实例
        String domainName="MyMbean";
        ObjectName testName = new ObjectName(domainName+":name=testJMX");
        // 将Hello这个对象资源注册到MBeanServer上
        mBeanServer.registerMBean(new Test(), testName);

        // HtmlAdaptor，负责将http请求转换成jmx agent请求
        ObjectName adapterName = new ObjectName(domainName + ":name=htmladapter,port=8082");
        HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();
        htmlAdaptorServer.start();
        // 将HtmlAdaptor注册到MBeanServer上
        mBeanServer.registerMBean(htmlAdaptorServer, adapterName);

        // connector,打开本地的rmi端口
        int rmiPort = 1099;
        Registry registry = LocateRegistry.createRegistry(rmiPort);

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:"+rmiPort+"/"+domainName);
        JMXConnectorServer jmxConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);
        jmxConnector.start();
    }
}