package com.cfh.studyjmx.notification;

import com.cfh.studyjmx.mbean.Test;

import javax.management.Notification;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * 消息监听者，需要实现NotificationListener接口
 */

public class NotificationListener implements javax.management.NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        // 打印消息体
        System.out.println("----------HelloListener-Begin------------");
        System.out.println("\ttype = "+ notification.getType());
        System.out.println("\tsource = "+notification.getSource());
        System.out.println("\tseq = "+notification.getSequenceNumber());
        System.out.println("\tsend time = "+notification.getTimeStamp());
        System.out.println("\tmessage="+notification.getMessage());
        System.out.println("----------HelloListener-End------------");

        // 根据消息内容处理handback中的逻辑
        if (handback != null) {
            if (handback instanceof Test) {
                Test test = (Test) handback;
                test.printHello(notification.getMessage());
            }
        }
    }
}