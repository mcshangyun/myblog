package com.mcsy.blog.service;

import com.mcsy.blog.beans.User;

/**
 * @author 15199
 */
public interface UserService {
    /**
     * 检查是否有user用户
     * @param username 用户名
     * @param password 密码
     * @return 返回user
     */
    User checkUser(String username,String password);
}
