package com.bow.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class MySessionListener1 implements SessionListener {

    @Override
    public void onStart(Session session) {
        System.out.println("会话创建 " + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("会话过期 " + session.getId());
    }

    @Override
    public void onStop(Session session) {// 退出/会话过期时触发
        System.out.println("会话停止 " + session.getId());
    }

}
