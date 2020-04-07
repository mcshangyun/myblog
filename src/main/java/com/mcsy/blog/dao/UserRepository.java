package com.mcsy.blog.dao;

import com.mcsy.blog.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 15199
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户名和密码进行查询
     * @param username 用户名
     * @param password 密码
     * @return 返回user对象
     */
    User findByUsernameAndPassword(String username,String password);
}
