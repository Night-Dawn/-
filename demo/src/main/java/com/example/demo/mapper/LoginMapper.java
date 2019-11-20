package com.example.demo.mapper;

import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface LoginMapper {
    User getUser(String User_username);
    Admin getAdmin(String username);
}
