package com.cfh.studyjmx.client;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * 自定义的管理MBean的client
 */

public class CustomClient {
    public static void main(String[] args) throws Exception{
        String domainName = "MyMbean";
        int rmiPort = 1099;
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:"+rmiPort+"/"+domainName);
        // 可以类比HelloAgent.java中的那句：
        // JMXConnectorServer jmxConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        // 获取一个与MBeanServer的连接
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        //print domains
        System.out.println("Domains:------------------");
        String domains[] = mbsc.getDomains();
        for(int i=0;i<domains.length;i++){
            System.out.println("\tDomain["+i+"] = "+domains[i]);
        }
        //MBean count
        System.out.println("MBean count = "+mbsc.getMBeanCount());

        //设置被MBean管理的对象资源的属性
        ObjectName mBeanName = new ObjectName(domainName+":name=testJMX");
        mbsc.setAttribute(mBeanName, new Attribute("Name","zzh"));//注意这里是Name而不是name
        System.out.println("===============Attribute================");
        System.out.println("Name = "+mbsc.getAttribute(mBeanName, "Name"));

        //通过rmi执行被MBean管理的对象资源中的方法
        System.out.println("===============Invoke===============");
        mbsc.invoke(mBeanName, "printHello", null, null);
        mbsc.invoke(mBeanName, "printHello", new String[]{"jizhi gril"}, new String[]{String.class.getName()});

        //get mbean information
        System.out.println("================Bean Information=============s");
        MBeanInfo info = mbsc.getMBeanInfo(mBeanName);
        System.out.println("class name: "+info.getClassName());
        for(int i=0;i<info.getAttributes().length;i++){
            System.out.println("attribute" + i + ":" +info.getAttributes()[i].getName());
        }
        for(int i=0;i<info.getOperations().length;i++){
            System.out.println("operation" + i + ":" +info.getOperations()[i].getName());
        }

        //ObjectName of MBean
        System.out.println("all ObjectName:--------------");
        Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
        for(Iterator<ObjectInstance> it = set.iterator();it.hasNext();){
            ObjectInstance oi = it.next();
            System.out.println("\t"+oi.getObjectName());
        }

        jmxc.close();
    }
}