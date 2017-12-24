package com.bow.shiro;

import com.bow.shiro.realm.DemoRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 错误2次就抛出错误
 * @see DemoRealm 支持用户cypress
 * @author vv
 * @since 20171216
 */
public class CredentialMatchTest {

    @After
    public void tearDown() throws Exception {
        // 退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void login() throws Exception{
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
                "classpath:authenticate-credential.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        //登陆错误超过2次就抛出
        UsernamePasswordToken token = new UsernamePasswordToken("cypress", "1234");
        while(true){
            try{
                subject.login(token);
            }catch (IncorrectCredentialsException e){
                TimeUnit.SECONDS.sleep(1);
            }
        }

    }


}
