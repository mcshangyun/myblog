package com.mcsy.blog.service.impl;

import com.mcsy.blog.beans.User;
import com.mcsy.blog.dao.UserRepository;
import com.mcsy.blog.service.UserService;
import com.mcsy.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 15199
 */
@Service
public class UserServiceImpl implements UserService {
    /*自动注入*/
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
