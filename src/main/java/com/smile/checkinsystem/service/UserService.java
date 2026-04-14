package com.smile.checkinsystem.service;

import com.smile.checkinsystem.entity.User;
import com.smile.checkinsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public String login(String username, String password) {
        User user = userMapper.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }

        return "token_" + user.getId();
    }
}
