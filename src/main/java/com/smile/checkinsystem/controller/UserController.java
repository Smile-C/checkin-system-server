package com.smile.checkinsystem.controller;

import com.smile.checkinsystem.common.ApiResponse;
import com.smile.checkinsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

        String token = userService.login(username, password);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);

        return ApiResponse.success("登录成功", result);
    }
}
