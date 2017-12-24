package com.bow.entity;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;


/**
 * @ClassName: User
 * @Description: 系统的登陆用户实体类
 * @author ViVi
 * @date 2015年6月29日 下午9:48:59
 */

public class User {

    private Long id;

    private Long organizationId; // 所属公司

    // 默认的为employeeCode;
    private String username;

    private String password;

    private String salt;

    private String nickname;

    private List<Long> roleIds; // 拥有的角色列表

    private Boolean locked = Boolean.FALSE;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     *            the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     *            the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getRoleIdsStr() {
        if (CollectionUtils.isEmpty(roleIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (Long roleId : roleIds) {
            s.append(roleId);
            s.append(",");
        }
        return s.toString();
    }
}
