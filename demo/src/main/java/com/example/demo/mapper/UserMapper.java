package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public int usernumber();
    public List<User> Userlist (@Param("User_id") Integer User_id,@Param("User_name") String User_name ,@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    public User checkUser(@Param("User_id") Integer User_id);
    public int addUser(@Param("User_id") Integer User_id,
                       @Param("User_name") String  User_name,
                       @Param("User_sex") String  User_sex,
                       @Param("User_address") String  User_address,
                       @Param("User_phone") String  User_phone,
                       @Param("User_username") String  User_username,
                       @Param("User_password") String  User_password,
                       @Param("User_borrow_max") Integer  User_borrow_max,
                       @Param("User_borrow_number") Integer  User_borrow_number
                       );
    public int updateUser(@Param("User_id") Integer User_id,
                          @Param("User_name") String  User_name,
                          @Param("User_sex") String  User_sex,
                          @Param("User_address") String  User_address,
                          @Param("User_phone") String  User_phone,
                          @Param("User_username") String  User_username,
                          @Param("User_password") String  User_password);
    public int deleteUser(@Param("User_id") Integer User_id);
    public int isCount(@Param("User_id") Integer User_id);
    public User getUser(@Param("User_username") String  User_username);
    public int updateCount(@Param("User_id") Integer User_id,@Param("User_borrow_number") Integer User_borrow_number);
}
