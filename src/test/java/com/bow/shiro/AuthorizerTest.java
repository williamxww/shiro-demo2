package com.bow.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class AuthorizerTest {

    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }


    @Test
    public void testIsPermitted() {
        login("classpath:shiro-authorizer.ini", "zhang", "123");
        Subject subject = SecurityUtils.getSubject();
        // 判断拥有权限：user:create
        Assert.assertTrue(subject.isPermitted("user1:update"));
        Assert.assertTrue(subject.isPermitted("user2:update"));
        // 通过二进制位的方式表示权限
        Assert.assertTrue(subject.isPermitted("+user1+2"));// 新增权限
        Assert.assertTrue(subject.isPermitted("+user1+8"));// 查看权限
        Assert.assertTrue(subject.isPermitted("+user2+10"));// 新增及查看
        Assert.assertFalse(subject.isPermitted("+user1+4"));// 没有删除权限
        Assert.assertTrue(subject.isPermitted("menu:view"));// 通过MyRolePermissionResolver解析得到的权限
    }

    @Test
    public void testIsPermitted2() {
        login("classpath:shiro-jdbc-authorizer.ini", "zhang", "123");
        Subject subject = SecurityUtils.getSubject();
        // 判断拥有权限：user:create
        Assert.assertTrue(subject.isPermitted("user1:update"));
        Assert.assertTrue(subject.isPermitted("user2:update"));
        // 通过二进制位的方式表示权限
        Assert.assertTrue(subject.isPermitted("+user1+2"));// 新增权限
        Assert.assertTrue(subject.isPermitted("+user1+8"));// 查看权限
        Assert.assertTrue(subject.isPermitted("+user2+10"));// 新增及查看
        Assert.assertFalse(subject.isPermitted("+user1+4"));// 没有删除权限
        Assert.assertTrue(subject.isPermitted("menu:view"));// 通过MyRolePermissionResolver解析得到的权限
    }

    protected void login(String configFile, String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }


}
