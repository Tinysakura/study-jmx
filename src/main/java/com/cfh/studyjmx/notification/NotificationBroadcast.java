package com.cfh.studyjmx.notification;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * @Author: chenfeihao@corp.netease.com
 * @Date: 2019/1/3
 * 消息发送者，需要继承NotificationBroadcasterSupport并被MBean管理
 */

public class NotificationBroadcast extends NotificationBroadcasterSupport implements NotificationBroadcastMBean {
    private int seq = 0;

    @Override
    public void sendNotification() {
        // 创建一个消息包
        Notification notification = new Notification(
                "notification name",// notification 名称
                this, // 消息源
                ++seq, // 消息序列
                System.currentTimeMillis(), // 消息发出的时间
                "notification body" // 消息体
        );

        // 调用父类的方法将消息发送出去
        sendNotification(notification);
    }
}