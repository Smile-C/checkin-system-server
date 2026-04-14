package com.smile.checkinsystem.service;

import com.smile.checkinsystem.entity.User;
import com.smile.checkinsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }

        User existingUser = userMapper.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password);

        userMapper.insertUser(user);

        Map<String, Object> result = new HashMap<>();
        result.put("id", user.getId());
        result.put("username", user.getUsername());

        return result;
    }
}
