package com.bow.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

import com.bow.shiro.authenticate.OnlyOneAuthenticatorStrategy;

/**
 * login之后在SimpleRealm中做认证
 * 
 * @see com.bow.shiro.realm.SimpleRealm 支持用户vv和cypress
 * @see com.bow.shiro.realm.SimpleRealm1 支持用户cypress
 * @see OnlyOneAuthenticatorStrategy 多个realm认证通过会抛错
 * @author vv
 * @since 20171216
 */
public class AuthenticatorTest {

    @After
    public void tearDown() throws Exception {
        // 退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }

    @Test
    public void login() {
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
                "classpath:authenticate.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("vv", "123");
        subject.login(token);
    }

    /**
     * 多个realm都可以认证cypress用户，则报错。
     * 
     * @see com.bow.shiro.realm.SimpleRealm
     * @see com.bow.shiro.realm.SimpleRealm1
     */
    @Test(expected = AuthenticationException.class)
    public void multiRealm() {
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
                "classpath:authenticate.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("cypress", "123");
        subject.login(token);
    }

}
