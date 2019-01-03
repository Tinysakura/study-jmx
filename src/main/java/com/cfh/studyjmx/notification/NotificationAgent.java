package com.cfh.studyjmx.notification;

import com.cfh.studyjmx.mbean.Test;
import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * Agent层
 */
public class NotificationAgent {
    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String domainName = "Notification";

        // 注册TestMBean
        ObjectName testObjectName = new ObjectName(domainName+ ":" + "name=testJMX");
        Test test = new Test();
        mBeanServer.registerMBean(test, testObjectName);

        // 注册html adaptor
        ObjectName adapterName = new ObjectName(domainName + ":" + "name=htmladapter,port=8082");
        HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();
        mBeanServer.registerMBean(htmlAdaptorServer,adapterName);

        // 注册notification broadcast
        ObjectName broadcastObjectName = new ObjectName(domainName + ":" + "name=notificationBroadcast");
        NotificationBroadcast notificationBroadcast = new NotificationBroadcast();
        mBeanServer.registerMBean(notificationBroadcast, broadcastObjectName);
        // 添加事件监听者
        notificationBroadcast.addNotificationListener(
                new NotificationListener(), // listener
                null, // filter
                test // handback
        );

        htmlAdaptorServer.start();
    }
}