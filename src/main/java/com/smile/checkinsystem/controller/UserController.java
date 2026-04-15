package com.smile.checkinsystem.controller;

import com.smile.checkinsystem.common.ApiResponse;
import com.smile.checkinsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody Map<String, String> params) {

        String username = params.get("username");
        String password = params.get("password");

        Map<String, Object> result = userService.login(username, password);

        return ApiResponse.success("登录成功", result);
    }

    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(@RequestHeader("Authorization") String authorization) {
        Map<String, Object> result = userService.getCurrentUser(authorization);
        return ApiResponse.success("获取当前用户成功", result);
    }

    @PostMapping("/register")
    public ApiResponse<Map<String, Object>> register(@RequestBody Map<String, String> params) {

        String username = params.get("username");
        String password = params.get("password");

        Map<String, Object> result = userService.register(username, password);

        return ApiResponse.success("注册成功", result);
    }
}
