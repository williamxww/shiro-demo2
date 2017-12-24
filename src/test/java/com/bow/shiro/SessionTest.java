package com.bow.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @see org.apache.shiro.session.mgt.OnlineSessionFactory
 * @see org.apache.shiro.session.mgt.OnlineSession
 */
public class SessionTest {

    @Test
    public void testGetSession() throws Exception{
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:session.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("vv", "123");
        subject.login(token);

        Session session = subject.getSession();

        System.out.println("会话ID "+session.getId());
        System.out.println("登录用户主机地址 "+session.getHost());
        System.out.println("超时时间 "+session.getTimeout());
        System.out.println("会话创建时间 "+session.getStartTimestamp());
        System.out.println("最后访问时间 "+session.getLastAccessTime());
        Thread.sleep(1000L);
        session.touch();
        System.out.println("最后访问时间 "+session.getLastAccessTime());


        //会话属性操作
        session.setAttribute("key", "123");
        Assert.assertEquals("123", session.getAttribute("key"));
        session.removeAttribute("key");
    }


}
