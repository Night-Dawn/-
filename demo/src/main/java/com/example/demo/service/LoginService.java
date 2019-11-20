package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginMapper loginMapper;
    public User getUser(String admin_name)
    {
        return loginMapper.getUser(admin_name);
    }
    public Admin getAdmin(String username)
    {
        return loginMapper.getAdmin(username);
    }
}
