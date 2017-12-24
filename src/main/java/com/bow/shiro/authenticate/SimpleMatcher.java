package com.bow.shiro.authenticate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 对用户提交的token和从数据库里查出的AuthenticationInfo 进行匹配认证。<br/>
 * 此处为了代码简单，将cache直接用map代替。超过2次直接抛出错误
 * 
 * @author vv
 * @since 20171216
 */
public class SimpleMatcher extends SimpleCredentialsMatcher {

    private Map<String, AtomicInteger> cache = new ConcurrentHashMap<>();

    public SimpleMatcher() {
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        // retry count + 1
        AtomicInteger retryCount = cache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            cache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 2) {
            // if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // clear retry count
            cache.remove(username);
        }
        return matches;
    }
}
