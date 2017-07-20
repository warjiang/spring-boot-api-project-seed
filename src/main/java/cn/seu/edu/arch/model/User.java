package cn.seu.edu.arch.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    /**
     * 用户ID 主键
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 手机号码 主键 用户填入手机号码之后可以访问更多资源
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像 url地址
     */
    private String avatar;

    /**
     * 加密密码
     */
    private String password;

    /**
     * 用户角色，目前包括 T(eacher)、S(tudent)、A(dmin)、V(isitor).角色用逗号分隔
     */
    private String role;

    /**
     * 本科院校
     */
    @Column(name = "old_school")
    private String oldSchool;

    /**
     * 目标院校
     */
    @Column(name = "target_school")
    private String targetSchool;

    /**
     * 获取用户ID 主键
     *
     * @return user_id - 用户ID 主键
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID 主键
     *
     * @param userId 用户ID 主键
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取手机号码 主键 用户填入手机号码之后可以访问更多资源
     *
     * @return phone_number - 手机号码 主键 用户填入手机号码之后可以访问更多资源
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置手机号码 主键 用户填入手机号码之后可以访问更多资源
     *
     * @param phoneNumber 手机号码 主键 用户填入手机号码之后可以访问更多资源
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户头像 url地址
     *
     * @return avatar - 用户头像 url地址
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置用户头像 url地址
     *
     * @param avatar 用户头像 url地址
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取加密密码
     *
     * @return password - 加密密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置加密密码
     *
     * @param password 加密密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户角色，目前包括 T(eacher)、S(tudent)、A(dmin)、V(isitor).角色用逗号分隔
     *
     * @return role - 用户角色，目前包括 T(eacher)、S(tudent)、A(dmin)、V(isitor).角色用逗号分隔
     */
    public String getRole(){
        return role;
    }

    public List<String> getRoleList() {
        ArrayList<String> roles = new ArrayList<>();
        if (role == null || role.length() == 0){
            return roles;
        }
        String[] roleArray = role.split(",");
        roles.addAll(Arrays.asList(roleArray));
        return roles;
    }

    /**
     * 设置用户角色，目前包括 T(eacher)、S(tudent)、A(dmin)、V(isitor).角色用逗号分隔
     *
     * @param role 用户角色，目前包括 T(eacher)、S(tudent)、A(dmin)、V(isitor).角色用逗号分隔
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取本科院校
     *
     * @return old_school - 本科院校
     */
    public String getOldSchool() {
        return oldSchool;
    }

    /**
     * 设置本科院校
     *
     * @param oldSchool 本科院校
     */
    public void setOldSchool(String oldSchool) {
        this.oldSchool = oldSchool;
    }

    /**
     * 获取目标院校
     *
     * @return target_school - 目标院校
     */
    public String getTargetSchool() {
        return targetSchool;
    }

    /**
     * 设置目标院校
     *
     * @param targetSchool 目标院校
     */
    public void setTargetSchool(String targetSchool) {
        this.targetSchool = targetSchool;
    }
}