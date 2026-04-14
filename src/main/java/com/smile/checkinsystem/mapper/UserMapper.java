package com.smile.checkinsystem.mapper;

import com.smile.checkinsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findByUsername(@Param("username") String username);

    int insertUser(User user);
}
