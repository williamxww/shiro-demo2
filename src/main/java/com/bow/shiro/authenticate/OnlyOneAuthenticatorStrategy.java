package com.bow.shiro.authenticate;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;

/**
 * 如果发现多个Realm都认证通过此用户，则抛出错误。
 * @since 20171216
 */
public class OnlyOneAuthenticatorStrategy extends AbstractAuthenticationStrategy {


    @Override
    public AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token)
            throws AuthenticationException {
        return new SimpleAuthenticationInfo();// 返回一个权限的认证信息
    }

    @Override
    public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate)
            throws AuthenticationException {
        return aggregate;// 返回之前合并的
    }

    /**
     * 将singleRealmInfo 合并到aggregateInfo中，然后判断是否有多个认证信息。
     * @param realm
     * @param token
     * @param singleRealmInfo
     * @param aggregateInfo
     * @param t
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo,
            AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        AuthenticationInfo info;
        if (singleRealmInfo == null) {
            info = aggregateInfo;
        } else {
            if (aggregateInfo == null) {
                info = singleRealmInfo;
            } else {
                info = merge(singleRealmInfo, aggregateInfo);
                if (info.getPrincipals().getRealmNames().size() > 1) {
                    throw new AuthenticationException("Please ensure only one realm can "
                            + "authenticate these tokens. Realms' name "+info.getPrincipals().getRealmNames());
                }
            }
        }

        return info;
    }

    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate)
            throws AuthenticationException {
        return aggregate;
    }
}
