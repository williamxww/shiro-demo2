package com.bow.shiro.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import com.bow.shiro.util.SerializableUtils;

/**
 * sessionManager会判断相应的sessionDAO是否实现了CacheManagerAware，如果实现了会把CacheManager设置给它
 */
public class MySessionDAO extends CachingSessionDAO {

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String sql = "insert into sessions(id, session) values(?,?)";
        // jdbcTemplate.update(sql, sessionId,
        // SerializableUtils.serialize(session));
        return session.getId();
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return; // 如果会话过期/停止 没必要再更新了
        }
        String sql = "update sessions set session=? where id=?";
        // jdbcTemplate.update(sql, SerializableUtils.serialize(session),
        // session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sql = "delete from sessions where id=?";
        // jdbcTemplate.update(sql, session.getId());
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String sql = "select session from sessions where id=?";
        List<String> sessionStrList = new ArrayList();
        // jdbcTemplate.queryForList(sql, String.class, sessionId);
        if (sessionStrList.size() == 0)
            return null;
        return SerializableUtils.deserialize(sessionStrList.get(0));
    }
}
