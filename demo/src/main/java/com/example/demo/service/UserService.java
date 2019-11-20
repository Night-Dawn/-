package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public List<User>  Userlist(Integer User_id,String User_name,Integer page,Integer pageSize )
    {
        List<User> userList = userMapper.Userlist(User_id, User_name, page, pageSize);
        return  userList;
    }
    public int usernumber(){
        return userMapper.usernumber();
    }
    public User checkUser(Integer User_id)
    {
        return userMapper.checkUser(User_id);
    }
    public int addUser(Integer User_id, String  User_name, String  User_sex,
                        String  User_address,
                        String  User_phone,
                        String  User_username,
                       String  User_password,
                       Integer  User_borrow_max,
                        Integer  User_borrow_number){
        return userMapper.addUser(User_id, User_name, User_sex, User_address, User_phone, User_username, User_password, User_borrow_max, User_borrow_number);
    }
    public int updateUser(Integer User_id, String  User_name, String  User_sex,
                          String  User_address,
                          String  User_phone,
                          String  User_username,
                          String  User_password){
        return userMapper.updateUser(User_id, User_name, User_sex, User_address, User_phone, User_username, User_password);
    }
    public int deleteUser(Integer User_id)
    {
        return userMapper.deleteUser(User_id);
    }
    public int isCount(Integer User_id)
    {
        return userMapper.isCount(User_id);
    }
    public User getUser(String  User_username)
    {
        return userMapper.getUser(User_username);
    }
    public int updateCount(Integer User_id, Integer User_borrow_number){
        return userMapper.updateCount(User_id, User_borrow_number);
    }
}
