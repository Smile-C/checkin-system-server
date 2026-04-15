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

    @Autowired
    private JwtService jwtService;

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }

        String accessToken = jwtService.generateToken(user);

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("tokenType", "Bearer");
        result.put("expiresIn", jwtService.getExpirationSeconds());

        return result;
    }

    public Map<String, Object> getCurrentUser(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new RuntimeException("未提供认证令牌");
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("认证令牌格式错误");
        }

        String token = authorizationHeader.substring(7).trim();
        if (token.isEmpty()) {
            throw new RuntimeException("认证令牌不能为空");
        }

        try {
            Long userId = jwtService.getUserIdFromToken(token);
            User user = userMapper.findById(userId);

            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            Map<String, Object> result = new HashMap<>();
            result.put("id", user.getId());
            result.put("username", user.getUsername());
            return result;
        } catch (Exception exception) {
            throw new RuntimeException("认证令牌无效或已过期");
        }
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
